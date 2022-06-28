package fis.training.ordermanagemantsystem.service.impl;

import fis.training.ordermanagemantsystem.dto.CustomerDTO;
import fis.training.ordermanagemantsystem.model.Customer;
import fis.training.ordermanagemantsystem.reponsitory.CustomerRepository;
import fis.training.ordermanagemantsystem.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer deleteCustomerById(Long customerId) {
        Customer customer = customerRepository.getById(customerId);
        customerRepository.delete(customer);
        return customer;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDTO> findAll(Pageable pageable) {
        log.info("Query all Customer. PageNumber: {}, PageSize: {}", pageable.getPageNumber(), pageable.getPageSize());
        return customerRepository.findAll(pageable).map(CustomerDTO.Mapper::fromEntity);
    }

    @Override
    public CustomerDTO findById(Long customerId) {
         return CustomerDTO.Mapper.fromEntity(customerRepository.findById(customerId).orElseThrow(
                 () -> {throw new IllegalArgumentException(String.format("Not Found Customer with id %s ", customerId));}));
    }
}
