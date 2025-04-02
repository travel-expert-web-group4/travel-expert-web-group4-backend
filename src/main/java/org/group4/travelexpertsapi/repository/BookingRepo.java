package org.group4.travelexpertsapi.repository;

import org.group4.travelexpertsapi.entity.Booking;
import org.group4.travelexpertsapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer> {
    List<Booking> findByCustomerid(Customer customerid);

    Booking findByBookingno(String bookingno);
}
