package org.group4.travelexpertsapi.controller;

import org.group4.travelexpertsapi.entity.Customer;
import org.group4.travelexpertsapi.entity.CustomerType;
import org.group4.travelexpertsapi.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Get customer by ID

    @GetMapping("/{customer_id}")
    public Customer getCustomer(@PathVariable("customer_id") Integer customer_id) {
        return customerService.getCustomerByAgentid(customer_id);
    }

    // Add new customer

    @PostMapping("/create-customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customerService.createCustomer(customer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    // Update customer

    @PutMapping("/{customer_id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customer_id") Integer customer_id, @RequestBody Customer customer) {
        Customer customerToUpdate = customerService.updateCustomer(customer_id, customer);
        return new ResponseEntity<>(customerToUpdate, HttpStatus.OK);
    }

    // Delete customer

    @DeleteMapping("/{customer_id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("customer_id") Integer customer_id) {
        customerService.deleteCustomer(customer_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Get Points
    @GetMapping("/{customer_id}/points")
    public Double getPointsBalance(@PathVariable("customer_id") Integer customer_id) {
        return customerService.getPointsBalance(customer_id);
    }

    // Get CustomerType

    @GetMapping("/{customer_id}/customer-type")
    public CustomerType getCustomerType(@PathVariable("customer_id") Integer customer_id) {
        return customerService.getCustomerType(customer_id);
    }
}
