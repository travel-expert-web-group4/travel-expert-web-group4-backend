package org.group4.travelexpertsapi.repository;

import org.group4.travelexpertsapi.entity.Booking;
import org.group4.travelexpertsapi.entity.Bookingdetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


import java.util.Optional;

@Repository
public interface BookingdetailRepo extends JpaRepository<Bookingdetail, Integer> {
//    Bookingdetail findByBookingid(Booking bookingid);
    List<Bookingdetail> findByBookingid(Booking booking);
 }
