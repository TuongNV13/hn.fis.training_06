package fis.training.ordermanagemantsystem.reponsitory;

import fis.training.ordermanagemantsystem.model.Order;
import fis.training.ordermanagemantsystem.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface OrderRepository extends JpaRepository<Order, Long> {
    @Modifying
    @Transactional
    @Query("update Order o set o.status=:status where o.id=:id")
    void updateStatusById(
            @Param("status") OrderStatus status,
            @Param("id") Long id
    );

}
