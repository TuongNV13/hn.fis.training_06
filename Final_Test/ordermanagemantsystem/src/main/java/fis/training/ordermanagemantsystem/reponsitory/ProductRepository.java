package fis.training.ordermanagemantsystem.reponsitory;

import fis.training.ordermanagemantsystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByAvaiable(Long avaiable);
}
