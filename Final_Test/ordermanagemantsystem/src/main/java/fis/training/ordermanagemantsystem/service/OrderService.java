package fis.training.ordermanagemantsystem.service;

import fis.training.ordermanagemantsystem.dto.CreateOrderDTO;
import fis.training.ordermanagemantsystem.dto.CreateOrderItemDTO;
import fis.training.ordermanagemantsystem.dto.DeleteOrderItem;
import fis.training.ordermanagemantsystem.dto.OrderDTO;
import fis.training.ordermanagemantsystem.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDTO createOrder(CreateOrderDTO createOrderDTO);
    void deleteOrderById(Long orderId);
    Page<OrderDTO> findAll(Pageable pageable);
    OrderDTO findById(Long orderId);
    OrderDTO addOrderItem(CreateOrderItemDTO createOrderItemDTO);
    OrderDTO updateStatus(Long id, OrderStatus status);
    OrderDTO removeOrderItem(DeleteOrderItem deleteOrderItem);

}
