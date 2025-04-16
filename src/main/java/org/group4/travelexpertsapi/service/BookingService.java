package org.group4.travelexpertsapi.service;

import org.group4.travelexpertsapi.dto.BookingDTO;
import org.group4.travelexpertsapi.entity.*;
import org.group4.travelexpertsapi.entity.Package;
import org.group4.travelexpertsapi.entity.auth.WebUser;
import org.group4.travelexpertsapi.repository.*;
import org.group4.travelexpertsapi.repository.auth.WebUserRepo;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepo bookingRepo;
    private final CustomerRepo customerRepo;
    private final PackageRepo packageRepo;
    private final TriptypeRepo triptypeRepo;
    private final BookingdetailRepo bookingdetailRepo;
    private final WebUserRepo webUserRepo;
    private final CustomerTypeRepo customerTypeRepo;

    public BookingService(BookingRepo bookingRepo, CustomerRepo customerRepo, PackageRepo packageRepo, TriptypeRepo triptypeRepo, BookingdetailRepo bookingdetailRepo, WebUserRepo webUserRepo, CustomerTypeRepo customerTypeRepo) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.packageRepo = packageRepo;
        this.triptypeRepo = triptypeRepo;
        this.bookingdetailRepo = bookingdetailRepo;
        this.webUserRepo = webUserRepo;
        this.customerTypeRepo = customerTypeRepo;
    }

    public Optional<BookingDTO> getBookingByBookingNo(String bookingNo) {
        Booking booking = bookingRepo.findByBookingno(bookingNo);
        if (booking != null) {
            BookingDTO converted = convertToBookingDTO(booking);
            return Optional.of(converted);
        }
        return Optional.empty();
    }

    public Optional<List<BookingDTO>> getBookingsByCustomerId(Integer id) {
        Customer customer = customerRepo.findById(id).orElse(null);
        if (customer != null) {
            List<Booking> bookings = bookingRepo.findByCustomerid(customer);
            List<BookingDTO> converted = new ArrayList<>();
            for (Booking booking : bookings) {
                converted.add(convertToBookingDTO(booking));
            }
            return Optional.of(converted);
        }
        return Optional.empty();
    }

    public Optional<BookingDTO> addBooking(Integer customerId, Integer packageId, String tripTypeId, BookingDTO dto) {
        Customer customer = customerRepo.findById(customerId).orElse(null);
        Package bookingPackage = packageRepo.findById(packageId).orElse(null);
        Triptype triptype = triptypeRepo.findByTriptypeid(tripTypeId);
        if (customer != null && bookingPackage != null && triptype != null) {
            dto.setBookingNo(uniqueBookingNumber());
            if (dto.getSavedAt() == null) {
                dto.setSavedAt(ZonedDateTime.now(ZoneId.of("America/Edmonton")).toInstant());
            }
            Booking booking = convertToBooking(dto, customer, bookingPackage, triptype);
            bookingRepo.save(booking);
            Bookingdetail details = convertToBookingdetail(dto);
            bookingdetailRepo.save(details);
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public Optional<BookingDTO> updateBooking(String bookingNo, BookingDTO dto) {
        Booking existingBooking = bookingRepo.findByBookingno(bookingNo);
        Bookingdetail details = bookingdetailRepo.findByBookingid(existingBooking);
        if (existingBooking != null && details != null) {
            existingBooking.setSavedAt(ZonedDateTime.now(ZoneId.of("America/Edmonton")).toInstant());
            existingBooking.setTravelers(dto.getTravelers());
            bookingRepo.save(existingBooking);

            details.setTripstart(dto.getTripStart());
            details.setTripend(dto.getTripEnd());
            bookingdetailRepo.save(details);

            BookingDTO converted = convertToBookingDTO(existingBooking);
            return Optional.of(converted);
        }
        return Optional.empty();
    }

    public void setBookingDate(String bookingNo) {
        Booking booking = bookingRepo.findByBookingno(bookingNo);
        if (booking != null) {
            booking.setBookingdate(ZonedDateTime.now(ZoneId.of("America/Edmonton")).toInstant());
            booking.setSavedAt(ZonedDateTime.now(ZoneId.of("America/Edmonton")).toInstant());
            Booking saved = bookingRepo.save(booking);
            updateWebUserPointsAndType(saved);
        }
    }

    public boolean deleteBooking(String bookingNo) {
        Booking existingBooking = bookingRepo.findByBookingno(bookingNo);
        Bookingdetail details = bookingdetailRepo.findByBookingid(existingBooking);
        if (existingBooking != null && details != null) {
            bookingdetailRepo.delete(details);
            bookingRepo.delete(existingBooking);
            return true;
        }
        return false;
    }

    public BookingDTO convertToBookingDTO(Booking booking) {
        Bookingdetail details = bookingdetailRepo.findByBookingid(booking);
        BookingDTO dto = new BookingDTO();
        dto.setBookingNo(booking.getBookingno());
        dto.setBookingDate(booking.getBookingdate());
        dto.setName(booking.getPackageid().getPkgname());
        dto.setDestination(booking.getPackageid().getDestination());
        dto.setTripStart(details.getTripstart());
        dto.setTripEnd(details.getTripend());
        dto.setDestination(details.getDestination());
        dto.setBasePrice(details.getBaseprice());
        dto.setAgencyCommission(details.getAgencycommission());
        dto.setTravelerCount(booking.getTravelercount());
        dto.setTripTypeId(booking.getTriptypeid().getTriptypeid());
        dto.setSavedAt(booking.getSavedAt());
        dto.setTravelers(booking.getTravelers());
        return dto;
    }

    public Booking convertToBooking(BookingDTO dto, Customer customer, Package bookingPackage, Triptype triptype) {
        Booking booking = new Booking();
        booking.setBookingno(dto.getBookingNo());
        booking.setBookingdate(dto.getBookingDate());
        booking.setTravelercount(dto.getTravelerCount());
        booking.setCustomerid(customer);
        booking.setTriptypeid(triptype);
        booking.setPackageid(bookingPackage);
        booking.setSavedAt(dto.getSavedAt());
        booking.setTravelers(dto.getTravelers());
        return booking;
    }

    public Bookingdetail convertToBookingdetail(BookingDTO dto) {
        Booking booking = bookingRepo.findByBookingno(dto.getBookingNo());
        Bookingdetail details = new Bookingdetail();
        details.setTripstart(dto.getTripStart());
        details.setTripend(dto.getTripEnd());
        details.setDescription("");
        details.setDestination(dto.getDestination());
        details.setBaseprice(dto.getBasePrice());
        details.setAgencycommission(dto.getAgencyCommission());
        details.setBookingid(booking);
        return details;
    }

    public String uniqueBookingNumber() {
        // Load list to use only query database once
        List<Booking> bookings = bookingRepo.findAll();

        String bookingNumber;

        do {
            bookingNumber = generateBookingNumber();
        } while (bookingAlreadyExists(bookings, bookingNumber));

        return bookingNumber;
    }

    private boolean bookingAlreadyExists(List<Booking> bookings, String bookingNumber) {
        // Check list if the generated number already exists
        for (Booking booking : bookings) {
            if (booking.getBookingno().equals(bookingNumber)) {
                return true;
            }
        }
        return false;
    }

    public String generateBookingNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            char c = (char) ((int) (Math.random() * 26) + 'A');
            sb.append(c);
        }
        for (int i = 0 ; i < 4; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }

    public void updateWebUserPointsAndType(Booking booking) {
        Customer customer = booking.getCustomerid();
        if (customer == null) return;

        WebUser webUser = webUserRepo.findByCustomer(customer);
        if (webUser == null) return;

        double earned = (booking.getPackageid().getPkgbaseprice().doubleValue()
                + booking.getPackageid().getPkgagencycommission().doubleValue())
                * booking.getTravelercount();

        int updatedPoints = (webUser.getPoints() != null ? webUser.getPoints() : 0) + (int) earned;
        webUser.setPoints(updatedPoints);

        // Assign customer type based on new point balance
        if (updatedPoints >= 20000) {
            webUser.setCustomerType(customerTypeRepo.findByName("Platinum"));
        } else if (updatedPoints >= 5000) {
            webUser.setCustomerType(customerTypeRepo.findByName("Bronze"));
        } else {
            webUser.setCustomerType(customerTypeRepo.findByName("Guest"));
        }

        webUserRepo.save(webUser);
    }
}