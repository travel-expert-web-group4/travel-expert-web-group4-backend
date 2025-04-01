package org.group4.travelexpertsapi.repository;

import org.group4.travelexpertsapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    Customer findByCustemail(String email);
}
