package org.group4.travelexpertsapi.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.group4.travelexpertsapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
  
    @Query("SELECT sum(bd.baseprice + bd.agencycommission) FROM Booking b JOIN Bookingdetail bd on b.id = bd.bookingid.id WHERE b.customerid.id = :customer_id")
    Double getTotalPrice(@Param("customer_id") Integer customer_id);

    Customer findByCustemail(String email);

    List<Customer> findByAgentid_Id(Integer agentId);

    boolean existsByCustemail(@Size(max = 50) @NotNull String custemail);
}
