package fis.training.ordermanagemantsystem.service.impl;

import fis.training.ordermanagemantsystem.dto.CreateOrderDTO;
import fis.training.ordermanagemantsystem.dto.CreateOrderItemDTO;
import fis.training.ordermanagemantsystem.dto.DeleteOrderItem;
import fis.training.ordermanagemantsystem.dto.OrderDTO;
import fis.training.ordermanagemantsystem.model.*;
import fis.training.ordermanagemantsystem.reponsitory.OrderItemRepository;
import fis.training.ordermanagemantsystem.reponsitory.OrderRepository;
import fis.training.ordermanagemantsystem.reponsitory.ProductRepository;
import fis.training.ordermanagemantsystem.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public OrderDTO removeOrderItem(DeleteOrderItem deleteOrderItem) {
        Order order = orderRepository.findById(deleteOrderItem.getOrderId()).orElseThrow(
                () -> new IllegalArgumentException("Order Not Found. Order Id " + deleteOrderItem.getOrderId()));
        Product product = productRepository.findById(deleteOrderItem.getProductId()).orElseThrow(
                () -> new IllegalArgumentException("Product Not Found. Product Id " + deleteOrderItem.getProductId()));
        order.getOrderItems().forEach(i -> {
            if (i.getId().equals(deleteOrderItem.getOrderItemId())) {
                product.setAvaiable(product.getAvaiable() - deleteOrderItem.getQuantity());

                order.setTotalAmount(order.getTotalAmount() - i.getAmount());
                orderItemRepository.delete(i);
                return;
            }
        });

        return OrderDTO.Mapper.fromEntity(orderRepository.save(order));
    }

    @Override
    public OrderDTO createOrder(CreateOrderDTO createOrderDTO) {
        Order orderAdd = Order.builder()
                .customer(Customer.builder().id(createOrderDTO.getCustomerId()).build())
                .orderDateTime(LocalDateTime.now())
                .status(OrderStatus.CREATED)
                .totalAmount(0.0)
                .build();

        Order order = orderRepository.save(orderAdd);
        AtomicReference<Double> totalAmount = new AtomicReference<>(0.0);

        createOrderDTO.getOrderItems().forEach(i -> {
            Product product = productRepository.findById(createOrderDTO.getProductId()).orElseThrow(
                    () -> new IllegalArgumentException("Product Not Found. Product Id " + createOrderDTO.getProductId()));

            if (product.getAvaiable() < i.getQuantity()) {
                throw new IllegalArgumentException(String.format("The quantity in stock is not enough to add: %s", i.getProductId()));
            }
            OrderItem orderItemAdd = OrderItem.builder()
                    .quantity(i.getQuantity())
                    .product(Product.builder().id(i.getProductId()).build())
                    .order(order)
                    .amount(i.getQuantity() * product.getPrice())
                    .build();

            totalAmount.updateAndGet(v -> v + orderItemAdd.getAmount());
            orderItemRepository.save(orderItemAdd);
        });
        order.setTotalAmount(totalAmount.get());

        return OrderDTO.Mapper.fromEntity(orderRepository.save(orderAdd));
    }

    @Override
    @Transactional
    public void deleteOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("Can't not find Order by id:" +orderId);
                });
       if (order.getStatus().equals("CANCELLED") || order.getStatus().equals("CREATED")) {
            order.getOrderItems().forEach(i -> {
               orderItemRepository.delete(i);
           });
       }
       else
           throw new IllegalArgumentException("status is not CANCELLED or CREATED");
        orderRepository.delete(order);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable) {
        log.info("Query all Order. PageNumber: {}, PageSize: {}", pageable.getPageNumber(), pageable.getPageSize());
        return orderRepository.findAll(pageable).map(OrderDTO.Mapper::fromEntity);
    }

    @Override
    public OrderDTO findById(Long orderId) {
        return OrderDTO.Mapper.fromEntity(orderRepository.findById(orderId).orElseThrow(
                () -> {throw new IllegalArgumentException(String.format("Not found order with id %s",orderId));}));
    }

    @Override
    public OrderDTO addOrderItem(CreateOrderItemDTO createOrderItemDTO) {

        Order order = orderRepository.findById(createOrderItemDTO.getOrderId()).orElseThrow(
                () -> new IllegalArgumentException("Order Not Found. Order Id " + createOrderItemDTO.getOrderId()));
        Product product = productRepository.findById(createOrderItemDTO.getProductId()).orElseThrow(
                () -> new IllegalArgumentException("Product Not Found. Product Id " + createOrderItemDTO.getProductId()));

        product.setAvaiable(product.getAvaiable() + createOrderItemDTO.getQuantity());

        OrderItem orderItem = OrderItem.builder().order(order)
                .product(product)
                .quantity(createOrderItemDTO.getQuantity())
                .amount(product.getPrice() * createOrderItemDTO.getQuantity())
                .build();

        if (product.getAvaiable() < createOrderItemDTO.getQuantity()){
            {throw new IllegalArgumentException(String.format("Only %s Products left", product.getAvaiable()));}
        }
        order.getOrderItems().add(orderItem);
        Double totalAmount = order.getOrderItems().stream().mapToDouble(item -> item.getAmount())
                .reduce(0,(amountOne, amountTwo) -> amountOne + amountTwo);

        order.setTotalAmount(totalAmount);
        product.setAvaiable(product.getAvaiable() - createOrderItemDTO.getQuantity());

        return OrderDTO.Mapper.fromEntity(orderRepository.save(order));
    }

    @Override
    public OrderDTO updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Order Not Found. Order Id " ));
        if ((status.toString()).equals("CREATED")) {
            orderRepository.updateStatusById(status, id);
            order.setStatus(status);
            return OrderDTO.Mapper.fromEntity(order);
        }
        return null;
    }




}
