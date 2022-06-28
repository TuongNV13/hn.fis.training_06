package fis.training.ordermanagemantsystem.service;

import fis.training.ordermanagemantsystem.dto.CustomerDTO;
import fis.training.ordermanagemantsystem.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    Customer deleteCustomerById(Long customerId);
    Page<CustomerDTO> findAll(Pageable pageable);
    CustomerDTO findById(Long customerId);
}
