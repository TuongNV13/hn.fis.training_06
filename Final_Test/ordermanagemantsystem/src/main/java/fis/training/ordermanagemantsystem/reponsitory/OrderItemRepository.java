package fis.training.ordermanagemantsystem.reponsitory;

import fis.training.ordermanagemantsystem.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
