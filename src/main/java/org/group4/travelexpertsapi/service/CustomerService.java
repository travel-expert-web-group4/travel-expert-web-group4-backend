package org.group4.travelexpertsapi.service;


import org.group4.travelexpertsapi.entity.Customer;
import org.group4.travelexpertsapi.entity.Agent;
import org.group4.travelexpertsapi.entity.CustomerType;
import org.group4.travelexpertsapi.repository.CustomerRepo;
import org.group4.travelexpertsapi.repository.CustomerTypeRepo;
import org.group4.travelexpertsapi.repository.AgentRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepo customerRepo;
    private CustomerTypeRepo customerTypeRepo;
    private AgentRepository agentRepo;


//    @Value("${customer.image.upload-dir}")
//    private String imageUploadDir;

    // Constructor

    public CustomerService(CustomerRepo customerRepo, CustomerTypeRepo customerTypeRepo, AgentRepository agentRepo) {
        this.customerRepo = customerRepo;
        this.customerTypeRepo = customerTypeRepo;
        this.agentRepo = agentRepo;
    }


    // CRUD
  
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    // (R) Get customer by id

    public Optional<Customer> getCustomerById(Integer id) {
        Customer customer = customerRepo.findById(id).orElse(null);
        if (customer != null) {
            return Optional.of(customer);
        }
        return Optional.empty();
    }

    // (C) Create new customer

    public Optional<Customer> addCustomer(Integer agentId, Customer customer) {
        Agent agent = agentRepo.findById(agentId).orElse(null);
        if (agent != null) {
            customer.setAgentid(agent);
            Customer saved = customerRepo.save(customer);
            return Optional.of(saved);
        }
        return Optional.empty();
    }

    // (U) Update customer

    public Optional<Customer> updateCustomer(Integer id, Customer updatedCustomer) {
        Customer existingCustomer = customerRepo.findById(id).orElse(null);
        if (existingCustomer != null) {
            existingCustomer.setCustfirstname(updatedCustomer.getCustfirstname());
            existingCustomer.setCustlastname(updatedCustomer.getCustlastname());
            existingCustomer.setCustaddress(updatedCustomer.getCustaddress());
            existingCustomer.setCustcity(updatedCustomer.getCustcity());
            existingCustomer.setCustprov(updatedCustomer.getCustprov());
            existingCustomer.setCustpostal(updatedCustomer.getCustpostal());
            existingCustomer.setCustcountry(updatedCustomer.getCustcountry());
            existingCustomer.setCusthomephone(updatedCustomer.getCusthomephone());
            existingCustomer.setCustbusphone(updatedCustomer.getCustbusphone());
            existingCustomer.setCustemail(updatedCustomer.getCustemail());
            if (updatedCustomer.getAgentid() != null) {
                existingCustomer.setAgentid(updatedCustomer.getAgentid());
            }
            Customer saved = customerRepo.save(existingCustomer);
            return Optional.of(saved);
        }
        return Optional.empty();
    }


    // (D) Delete customer

    public boolean deleteCustomer(Integer id) {
        if (!customerRepo.existsById(id)) {
            return false;
        }
        customerRepo.deleteById(id);
        return true;
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


    // THESE METHODS HAVE BEEN UPDATED TO STORE DATA INTO WEB_USER TABLE
    // THUS THE METHODS HAVE BEEN MOVED TO WEB_USER_SERVICE
    /*
    // Profile Picture manager

    public String uploadPicture(Integer customerid, MultipartFile image) {
        Customer customer = customerRepo.findById(customerid).orElse(null);
        String filePath = customer.getCustfirstname() + "_" + customer.getCustlastname() + ".jpg";
        try {
            File fileDir = new File(imageUploadDir);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            //String fileName = image.getOriginalFilename();
            Path imagePath = Paths.get(imageUploadDir, filePath);
            Files.write(imagePath, image.getBytes());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String ImageUrl = "/images/customers/" + filePath;

        return ImageUrl;
    }

        // save Profile Picture

        public void savePicture(Integer customerid, MultipartFile image) {
            Customer customer = customerRepo.findById(customerid).orElse(null);
            String ImageUrl = uploadPicture(customerid, image);
            customer.setProfileImageUrl(ImageUrl);
            customerRepo.save(customer);
        }




    // Update Profile Picture of Customer
    public void updatePicture(Integer customerid, MultipartFile image) {
        Customer existingCustomer = customerRepo.findById(customerid).orElse(null);

        if (existingCustomer != null) {
            if (image != null && !image.isEmpty()) {
                String imagePath = uploadPicture(customerid, image);
                existingCustomer.setProfileImageUrl(imagePath);

            }
        }

        customerRepo.save(existingCustomer);
    }

    // Delete Profile Picture of Customer
    public void deletePicture(Integer customerid) {
        Customer customer = customerRepo.findById(customerid).orElse(null);
        if (customer != null) {
            String filePath = customer.getCustfirstname() + "_" + customer.getCustlastname() + ".jpg";
            Path imagePath = Paths.get(imageUploadDir, filePath);
            try {
                Files.delete(imagePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            customer.setProfileImageUrl(null);

        }

        customerRepo.save(customer);
    }

     */


    

}
