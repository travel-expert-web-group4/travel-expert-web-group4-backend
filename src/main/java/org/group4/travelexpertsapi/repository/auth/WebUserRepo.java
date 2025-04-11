package org.group4.travelexpertsapi.repository.auth;


import org.group4.travelexpertsapi.entity.Customer;
import org.group4.travelexpertsapi.entity.auth.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebUserRepo extends JpaRepository<WebUser,Integer> {
    WebUser findByCustomer(Customer customer);


    Optional<WebUser> findByEmail(String email);
}
