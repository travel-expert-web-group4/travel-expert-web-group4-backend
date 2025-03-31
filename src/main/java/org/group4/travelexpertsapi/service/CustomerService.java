package org.group4.travelexpertsapi.service;

import org.group4.travelexpertsapi.entity.Customer;
import org.group4.travelexpertsapi.repository.CustomerRepo;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private CustomerRepo customerRepo;

    // Constructor
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    // CRUD

    // (R) Get customer by id

    public Customer getCustomerByAgentid(Integer customerid) {
        return customerRepo.findById(customerid).orElse(null);
    }

    // (C) Create new customer

    public void createCustomer(Customer customer) {
        customerRepo.save(customer);
    }

    // (U) Update customer

    public Customer updateCustomer(Integer customerid, Customer customer) {
        Customer customerToUpdate = customerRepo.findById(customerid).orElse(null);
        if (customerToUpdate != null) {
            customerToUpdate.setCustfirstname(customer.getCustfirstname());
            customerToUpdate.setCustlastname(customer.getCustlastname());
            customerToUpdate.setCustemail(customer.getCustemail());
            customerToUpdate.setCustaddress(customer.getCustaddress());
            customerToUpdate.setCustcity(customer.getCustcity());
            customerToUpdate.setCustprov(customer.getCustprov());
            customerToUpdate.setCustcountry(customer.getCustcountry());
            customerToUpdate.setCustpostal(customer.getCustpostal());
            customerToUpdate.setCusthomephone(customer.getCusthomephone());
            customerToUpdate.setCustbusphone(customer.getCustbusphone());

        }
        return customerRepo.save(customerToUpdate);
    }


    // (D) Delete customer

    public void deleteCustomer(Integer customerid) {
        Customer customerToDelete = customerRepo.findById(customerid).orElse(null);
        if (customerToDelete != null) {
            customerRepo.delete(customerToDelete);
        }
    }


}
