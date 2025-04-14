package org.group4.travelexpertsapi.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.group4.travelexpertsapi.entity.Booking;
import org.group4.travelexpertsapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer> {
    List<Booking> findByCustomerid(Customer customerid);

    Booking findByBookingno(String bookingno);

    Booking findByCustomerid_CustemailAndPackageid_PkgnameOrderBySavedAtDesc(String customeridCustemail, String packageidPkgname);

    boolean existsByBookingno(String bookingno);
}
