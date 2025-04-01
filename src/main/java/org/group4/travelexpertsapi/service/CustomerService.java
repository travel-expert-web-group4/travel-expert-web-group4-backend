package org.group4.travelexpertsapi.service;

import org.group4.travelexpertsapi.entity.Customer;
import org.group4.travelexpertsapi.entity.CustomerType;
import org.group4.travelexpertsapi.repository.CustomerRepo;
import org.group4.travelexpertsapi.repository.CustomerTypeRepo;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private CustomerRepo customerRepo;
    private CustomerTypeRepo customerTypeRepo;

    // Constructor

    public CustomerService(CustomerRepo customerRepo, CustomerTypeRepo customerTypeRepo) {
        this.customerRepo = customerRepo;
        this.customerTypeRepo = customerTypeRepo;
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

    // Return customer type

    public CustomerType  getCustomerType(Integer customerid) {
        CustomerType customerType = null;
        Double balance = getPointsBalance(customerid);
        if (balance != null) {
            if (balance >= 5000 &&  balance < 20000) {
                customerType = customerTypeRepo.findByNameContainsIgnoreCase("bronze");
            } else if  (balance >= 20000) {
                customerType = customerTypeRepo.findByNameContainsIgnoreCase("platinum");
            } else {
                customerType = customerTypeRepo.findByNameContainsIgnoreCase("guest");
            }
        }


        return  customerType;
    }

    // View points

    public Double getPointsBalance(Integer customerid) {
        Double balance = customerRepo.getTotalPrice(customerid);
        return balance;
    }




}
