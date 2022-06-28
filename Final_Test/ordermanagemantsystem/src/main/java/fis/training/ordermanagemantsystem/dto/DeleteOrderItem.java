package fis.training.ordermanagemantsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteOrderItem {

    private Long orderId;
    private Long orderItemId;
    private Long productId;
    private Integer quantity;
}
