package fis.training.ordermanagemantsystem.reponsitory;

import fis.training.ordermanagemantsystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer getById(Long id);
}
