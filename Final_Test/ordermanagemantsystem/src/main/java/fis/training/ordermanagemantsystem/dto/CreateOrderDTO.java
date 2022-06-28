package fis.training.ordermanagemantsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDTO {
    private Long orderId;
    private Long productId;
    private Long customerId;
    private List<OrderItemOnCreateOrderDTO> orderItems;
}