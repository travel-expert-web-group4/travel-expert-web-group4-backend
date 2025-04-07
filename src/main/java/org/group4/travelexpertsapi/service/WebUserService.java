package org.group4.travelexpertsapi.service;

import org.group4.travelexpertsapi.entity.Agent;
import org.group4.travelexpertsapi.entity.Customer;
import org.group4.travelexpertsapi.entity.WebUser;
import org.group4.travelexpertsapi.repository.AgentRepository;
import org.group4.travelexpertsapi.repository.CustomerRepo;
import org.group4.travelexpertsapi.repository.CustomerTypeRepo;
import org.group4.travelexpertsapi.repository.WebUserRepo;
import org.springframework.stereotype.Service;

@Service
public class WebUserService {
    WebUserRepo webUserRepo;
    CustomerRepo customerRepo;
    CustomerTypeRepo customerTypeRepo;
    AgentRepository agentRepo;

    public WebUserService(WebUserRepo webUserRepo, CustomerRepo customerRepo, CustomerTypeRepo customerTypeRepo, AgentRepository agentRepo) {
        this.webUserRepo = webUserRepo;
        this.customerRepo = customerRepo;
        this.customerTypeRepo = customerTypeRepo;
        this.agentRepo = agentRepo;
    }

    // create new web user
    public void createNewUser(String email, String password, String agentEmail){
        WebUser webUser=new WebUser();
        Customer customer = customerRepo.findByCustemail(email);
        Integer points =  getPointsBalance(customer.getId()).intValue();

        customerTypeSetter(webUser, points);

        webUser.setCustomer(customer);
        webUser.setPoints(points);

        // check if agent

        webUser.setAgent(false);
        if (agentEmail != null){
            Agent agent = agentRepo.findByAgtemail(agentEmail);
            if (agent != null /* && agent password check??*/){
                webUser.setAgent(true);
            }
        }

        // CHANGE TO HASHED PASSWORD WHEN SECURITY LAYER ADDED
        webUser.setPassword(password);

        webUserRepo.save(webUser);
    }

    public void customerTypeSetter(WebUser webUser, int points) {

            if (points >= 5000 &&  points < 20000) {
                webUser.setCustomerType(customerTypeRepo.findByNameContainsIgnoreCase("bronze"));
            } else if  (points >= 20000) {
                webUser.setCustomerType(customerTypeRepo.findByNameContainsIgnoreCase("platinum"));

            } else {
                webUser.setCustomerType(customerTypeRepo.findByNameContainsIgnoreCase("guest"));

            }

    }

    public Double getPointsBalance(Integer customerid) {
        Double balance = customerRepo.getTotalPrice(customerid);
        return balance;
    }

    // find web-user by email
    public WebUser getWebUserByEmail(String email) {
        Customer customer = customerRepo.findByCustemail(email);
        WebUser webUser = webUserRepo.findByCustomer(customer);
        return webUser;
    }

    // find web-user by id
    public WebUser getWebUserById(Integer id) {
        WebUser webUser =  webUserRepo.findById(id).orElse(null);
        return webUser;
    }
}
