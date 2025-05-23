package org.group4.travelexpertsapi.controller;

import org.group4.travelexpertsapi.entity.Customer;

import org.group4.travelexpertsapi.entity.CustomerType;

import org.group4.travelexpertsapi.repository.CustomerRepo;
import org.group4.travelexpertsapi.service.CustomerService;
import org.group4.travelexpertsapi.service.auth.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/customer")
public class CustomerController {


    private CustomerService customerService;
    private WebUserService webUserService;



    public CustomerController(CustomerService customerService, WebUserService webUserService) {
        this.customerService = customerService;
        this.webUserService = webUserService;
    }



    @GetMapping
    public List<Customer> getCustomerList() {
        return customerService.getAllCustomers();
    }
    // Get customer by ID

    @GetMapping("/by-agent/{id}")
    public List<Customer> getCustomersByAgent(@PathVariable Integer id) {
        return customerService.getCustomersAssignedToAgent(id);
    }

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
        return webUserService.getPointsBalance(customer_id);
    }

    // Get CustomerType

    @GetMapping("/{customer_id}/customer-type")
    public CustomerType getCustomerType(@PathVariable("customer_id") Integer customer_id) {
        return webUserService.getCustomerType(customer_id);


    }

    // Add Profile Picture

//    @PostMapping(value = "/{customer_id}/profile-picture", consumes = "multipart/form-data")
//    public ResponseEntity<Customer> uploadImage(@PathVariable("customer_id") Integer customer_id, @RequestPart("image") MultipartFile image) {
//        webUserService.savePicture(customer_id, image);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PostMapping(value = "/{customer_id}/profile-picture", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, String>> uploadImage(
            @PathVariable("customer_id") Integer customer_id,
            @RequestPart("image") MultipartFile image) {
        try {
            String savedPath = webUserService.savePicture(customer_id, image); // <- this method should return the saved image path or URL

            // ✅ Return JSON with the image path
            return ResponseEntity.ok(Map.of("profileImage", savedPath));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to upload image"));
        }
    }



    // Update Profile Picture


    @PutMapping(value = "/{customer_id}/profile-picture", consumes = "multipart/form-data")
    public ResponseEntity<Customer> updateImage(@PathVariable("customer_id") Integer customer_id, @RequestPart("image") MultipartFile image) {
        webUserService.updatePicture(customer_id, image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Delete Profile Picture
    @DeleteMapping("/{customer_id}/profile-picture")
    public ResponseEntity<Customer> deleteImage(@PathVariable("customer_id") Integer customer_id) {
        webUserService.deletePicture(customer_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Autowired
    private CustomerRepo customerRepo;


    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        Customer customer = customerRepo.findByCustemail(email);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
