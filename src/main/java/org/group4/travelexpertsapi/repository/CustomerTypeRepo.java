package org.group4.travelexpertsapi.repository;

import org.group4.travelexpertsapi.entity.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface CustomerTypeRepo extends JpaRepository<CustomerType, Integer> {


    CustomerType findByName(String name);

    CustomerType findByNameContainsIgnoreCase(String name);
}
