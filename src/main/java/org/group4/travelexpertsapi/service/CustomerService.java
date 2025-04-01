package org.group4.travelexpertsapi.service;

import org.group4.travelexpertsapi.entity.Agent;
import org.group4.travelexpertsapi.entity.Customer;
import org.group4.travelexpertsapi.repository.AgentRepository;
import org.group4.travelexpertsapi.repository.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final AgentRepository agentRepo;

    public CustomerService(CustomerRepo customerRepo, AgentRepository agentRepo) {
        this.customerRepo = customerRepo;
        this.agentRepo = agentRepo;
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public Optional<Customer> getCustomerById(Integer id) {
        Customer customer = customerRepo.findById(id).orElse(null);
        if (customer != null) {
            return Optional.of(customer);
        }
        return Optional.empty();
    }

    public Optional<Customer> addCustomer(Integer agentId, Customer customer) {
        Agent agent = agentRepo.findById(agentId).orElse(null);
        if (agent != null) {
            customer.setAgentid(agent);
            Customer saved = customerRepo.save(customer);
            return Optional.of(saved);
        }
        return Optional.empty();
    }

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

    public boolean deleteCustomer(Integer id) {
        if (!customerRepo.existsById(id)) {
            return false;
        }
        customerRepo.deleteById(id);
        return true;
    }
}
