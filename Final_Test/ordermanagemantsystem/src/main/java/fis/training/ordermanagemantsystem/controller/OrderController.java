package fis.training.ordermanagemantsystem.controller;

import fis.training.ordermanagemantsystem.dto.*;
import fis.training.ordermanagemantsystem.model.OrderStatus;
import fis.training.ordermanagemantsystem.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    public Page<OrderDTO> findAll(@PathVariable(name="pageNumber") Integer pageNumber, @PathVariable(name="pageSize")
            Integer pageSize) {
        log.info("Request All Order. PageNumber: {}, PageSize: {}", pageNumber, pageSize);
        return orderService.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> findById(@PathVariable(name = "orderId") Long orderId) {
        return ResponseEntity.ok(orderService.findById(orderId));
    }

    @PutMapping("/")
    public ResponseEntity<OrderDTO> create(@RequestBody CreateOrderDTO createOrderDTO) {
        return ResponseEntity.ok(orderService.createOrder(createOrderDTO));
    }


    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderDTO> delete(@PathVariable(name = "orderId") Long orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/addOrderItem")
    public OrderDTO addOrderItem(@RequestBody CreateOrderItemDTO createOrderItemDTO) {
        return orderService.addOrderItem(createOrderItemDTO);
    }

    @PostMapping("/removeOrderItem")
    public ResponseEntity<OrderDTO> removeOrderItem(@RequestBody DeleteOrderItem deleteOrderItem) {
        return ResponseEntity.ok(orderService.removeOrderItem(deleteOrderItem));
    }

    @PostMapping("/paid/{orderId}")
    public ResponseEntity<OrderDTO> paid(@PathVariable(name = "orderId") Long orderId) {
        return ResponseEntity.ok(orderService.updateStatus(orderId, OrderStatus.PAID));
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<OrderDTO> cancel(@PathVariable(name = "orderId") Long orderId) {
        return ResponseEntity.ok(orderService.updateStatus(orderId, OrderStatus.CANCELLED));
    }
}
