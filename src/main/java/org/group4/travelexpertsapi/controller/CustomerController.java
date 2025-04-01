package org.group4.travelexpertsapi.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.group4.travelexpertsapi.entity.Customer;

import org.group4.travelexpertsapi.entity.CustomerType;

import org.group4.travelexpertsapi.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {


    private CustomerService customerService;



    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


  
    @GetMapping
    public List<Customer> getCustomerList() {
        return customerService.getAllCustomers();
    }
    // Get customer by ID

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Integer id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Add new customer

    @PostMapping("/new/{agent_id}")
    public ResponseEntity<Customer> createCustomer(@PathVariable Integer agent_id, @RequestBody Customer customer) {
        Optional<Customer> newCustomer = customerService.addCustomer(agent_id, customer);
        return newCustomer.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    // Update customer

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @RequestBody Customer updatedCustomer) {
        Optional<Customer> existingCustomer = customerService.updateCustomer(id, updatedCustomer);
        return existingCustomer.map(customer -> new ResponseEntity<>(customer, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete customer

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        if (customerService.deleteCustomer(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    // Add Profile Picture

    @PostMapping(value = "/{customer_id}/upload-image", consumes = "multipart/form-data")
    public ResponseEntity<Customer> uploadImage(@PathVariable("customer_id") Integer customer_id, @RequestPart("image") MultipartFile image) {
        customerService.uploadPicture(customer_id, image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Get Profile Picture

//    @GetMapping("/{customer_id}/image")
//    public MultipartFile getImage(@PathVariable("customer_id") Integer customer_id) {
//        return customerService.getImageFile(customer_id);
//    }
}
