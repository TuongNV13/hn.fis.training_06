package fis.training.ordermanagemantsystem.controller;

import fis.training.ordermanagemantsystem.dto.CustomerDTO;
import fis.training.ordermanagemantsystem.model.Customer;
import fis.training.ordermanagemantsystem.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customer")
@Slf4j
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    public Page<CustomerDTO> findAll(@PathVariable(name = "pageNumber") Integer pageNumber, @PathVariable(name = "pageSize")
            Integer pageSize) {
        log.info("Request All Order. PageNumber: {}, PageSize: {}", pageNumber, pageSize);
        return customerService.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @GetMapping("/findById/{customerId}")
    public CustomerDTO findById(@PathVariable(name = "customerId") Long customerId) {
        return customerService.findById(customerId);
    }

    @PostMapping("/create")
    public Customer  addCustomer(@RequestBody Customer customer){

        log.info("Request All Order. customer: {}", customer);
        return customerService.createCustomer(customer);
    }

    @PostMapping("/{customerId}")
    public Customer updateCustomer(@PathVariable(name = "customerId") Customer customer) {

        log.info("Request All Order. customer: {}", customer);
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping("/{customerId}")
    public Customer deleteCustomerById(@PathVariable(name = "customerId") Long customerId){
        return customerService.deleteCustomerById(customerId);
    }

}




