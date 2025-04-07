package org.group4.travelexpertsapi.repository;


import org.group4.travelexpertsapi.entity.Customer;
import org.group4.travelexpertsapi.entity.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebUserRepo extends JpaRepository<WebUser,Integer> {
    WebUser findByCustomer(Customer customer);
}
