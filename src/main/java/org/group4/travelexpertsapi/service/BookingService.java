//package org.group4.travelexpertsapi.service;
//
//import org.group4.travelexpertsapi.dto.BookingDTO;
//import org.group4.travelexpertsapi.entity.*;
//import org.group4.travelexpertsapi.entity.Package;
//import org.group4.travelexpertsapi.repository.*;
//import org.springframework.stereotype.Service;
//
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class BookingService {
//
//    private final BookingRepo bookingRepo;
//    private final CustomerRepo customerRepo;
//    private final PackageRepo packageRepo;
//    private final TriptypeRepo triptypeRepo;
//    private final BookingdetailRepo bookingdetailRepo;
//
//    public BookingService(BookingRepo bookingRepo, CustomerRepo customerRepo, PackageRepo packageRepo, TriptypeRepo triptypeRepo, BookingdetailRepo bookingdetailRepo) {
//        this.bookingRepo = bookingRepo;
//        this.customerRepo = customerRepo;
//        this.packageRepo = packageRepo;
//        this.triptypeRepo = triptypeRepo;
//        this.bookingdetailRepo = bookingdetailRepo;
//    }
//
//    public Optional<BookingDTO> getBookingByBookingNo(String bookingNo) {
//        Booking booking = bookingRepo.findByBookingno(bookingNo);
//        if (booking != null) {
//            BookingDTO converted = convertToBookingDTO(booking);
//            return Optional.of(converted);
//        }
//        return Optional.empty();
//    }
//
//    public Optional<List<BookingDTO>> getBookingsByCustomerId(Integer id) {
//        Customer customer = customerRepo.findById(id).orElse(null);
//        if (customer != null) {
//            List<Booking> bookings = bookingRepo.findByCustomerid(customer);
//            List<BookingDTO> converted = new ArrayList<>();
//            for (Booking booking : bookings) {
//                converted.add(convertToBookingDTO(booking));
//            }
//            return Optional.of(converted);
//        }
//        return Optional.empty();
//    }
//
//    public Optional<BookingDTO> addBooking(Integer customerId, Integer packageId, String tripTypeId, BookingDTO dto) {
//        Customer customer = customerRepo.findById(customerId).orElse(null);
//        Package bookingPackage = packageRepo.findById(packageId).orElse(null);
//        Triptype triptype = triptypeRepo.findByTriptypeid(tripTypeId);
//        if (customer != null && bookingPackage != null && triptype != null) {
//            dto.setBookingNo(generateBookingNumber());
//            Booking booking = convertToBooking(dto, customer, bookingPackage, triptype);
//            bookingRepo.save(booking);
//            Bookingdetail details = convertToBookingdetail(dto);
//            bookingdetailRepo.save(details);
//            return Optional.of(dto);
//        }
//        return Optional.empty();
//    }
//
//    public Optional<BookingDTO> updateBooking(String bookingNo, BookingDTO dto) {
//        Booking existingBooking = bookingRepo.findByBookingno(bookingNo);
//        Bookingdetail details = bookingdetailRepo.findByBookingid(existingBooking);
//        if (existingBooking != null && details != null) {
//            existingBooking.setSavedAt(ZonedDateTime.now(ZoneId.of("America/Edmonton")).toInstant());
//            existingBooking.setTravelers(dto.getTravelers());
//            bookingRepo.save(existingBooking);
//            details.setTripstart(dto.getTripStart());
//            details.setTripend(dto.getTripEnd());
//            bookingdetailRepo.save(details);
//            BookingDTO converted = convertToBookingDTO(existingBooking);
//            return Optional.of(converted);
//        }
//        return Optional.empty();
//    }
//
//    public Optional<BookingDTO> setBookingDate(String bookingNo) {
//        Booking booking = bookingRepo.findByBookingno(bookingNo);
//        if (booking != null) {
//            booking.setBookingdate(ZonedDateTime.now(ZoneId.of("America/Edmonton")).toInstant());
//            booking.setSavedAt(ZonedDateTime.now(ZoneId.of("America/Edmonton")).toInstant());
//            Booking saved = bookingRepo.save(booking);
//            BookingDTO transfer = convertToBookingDTO(saved);
//            return Optional.of(transfer);
//        }
//        return Optional.empty();
//    }
//
//    public boolean deleteBooking(String bookingNo) {
//        Booking existingBooking = bookingRepo.findByBookingno(bookingNo);
//        Bookingdetail details = bookingdetailRepo.findByBookingid(existingBooking);
//        if (existingBooking != null && details != null) {
//            bookingdetailRepo.delete(details);
//            bookingRepo.delete(existingBooking);
//            return true;
//        }
//        return false;
//    }
//
//    public BookingDTO convertToBookingDTO(Booking booking) {
//        Bookingdetail details = bookingdetailRepo.findByBookingid(booking);
//        BookingDTO dto = new BookingDTO();
//        dto.setBookingNo(booking.getBookingno());
//        dto.setBookingDate(booking.getBookingdate());
//        dto.setName(booking.getPackageid().getPkgname());
//        dto.setDestination(booking.getPackageid().getDestination());
//        dto.setTripStart(details.getTripstart());
//        dto.setTripEnd(details.getTripend());
//        dto.setTravelerCount(booking.getTravelercount());
//        dto.setTripTypeId(booking.getTriptypeid().getTriptypeid());
//        dto.setBasePrice(booking.getPackageid().getPkgbaseprice());
//        dto.setAgencyCommission(booking.getPackageid().getPkgagencycommission());
//        dto.setSavedAt(booking.getSavedAt());
//        dto.setTravelers(booking.getTravelers());
//        return dto;
//    }
//
//    public Booking convertToBooking(BookingDTO dto, Customer customer, Package bookingPackage, Triptype triptype) {
//        Booking booking = new Booking();
//        booking.setBookingno(dto.getBookingNo());
//        booking.setBookingdate(dto.getBookingDate());
//        booking.setTravelercount(dto.getTravelerCount());
//        booking.setCustomerid(customer);
//        booking.setTriptypeid(triptype);
//        booking.setPackageid(bookingPackage);
//        booking.setSavedAt(dto.getSavedAt());
//        booking.setTravelers(dto.getTravelers());
//        return booking;
//    }
//
//    public Bookingdetail convertToBookingdetail(BookingDTO dto) {
//        Booking booking = bookingRepo.findByBookingno(dto.getBookingNo());
//        Bookingdetail details = new Bookingdetail();
//        details.setTripstart(dto.getTripStart());
//        details.setTripend(dto.getTripEnd());
//        details.setDescription("");
//        details.setDestination(dto.getDestination());
//        details.setBaseprice(dto.getBasePrice());
//        details.setAgencycommission(dto.getAgencyCommission());
//        details.setBookingid(booking);
//        return details;
//    }
//
//    public String generateBookingNumber() {
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < 4; i++) {
//            char c = (char) ((int) (Math.random() * 26) + 'A');
//            sb.append(c);
//        }
//        for (int i = 0 ; i < 4; i++) {
//            sb.append((int) (Math.random() * 10));
//        }
//        return sb.toString();
//    }
//}
package org.group4.travelexpertsapi.service;

import org.group4.travelexpertsapi.dto.BookingDTO;
import org.group4.travelexpertsapi.entity.*;
import org.group4.travelexpertsapi.entity.Package;
import org.group4.travelexpertsapi.repository.*;
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

    public BookingService(BookingRepo bookingRepo, CustomerRepo customerRepo, PackageRepo packageRepo, TriptypeRepo triptypeRepo, BookingdetailRepo bookingdetailRepo) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.packageRepo = packageRepo;
        this.triptypeRepo = triptypeRepo;
        this.bookingdetailRepo = bookingdetailRepo;
    }

    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
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


            // ✅ Set savedAt here if not already set
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
        List<Bookingdetail> detailsList = bookingdetailRepo.findByBookingid(existingBooking);
        Bookingdetail details = detailsList.isEmpty() ? null : detailsList.get(0);

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

    public Optional<BookingDTO> setBookingDate(String bookingNo) {
        Booking booking = bookingRepo.findByBookingno(bookingNo);
        if (booking != null) {
            booking.setBookingdate(ZonedDateTime.now(ZoneId.of("America/Edmonton")).toInstant());
            booking.setSavedAt(ZonedDateTime.now(ZoneId.of("America/Edmonton")).toInstant());
            Booking saved = bookingRepo.save(booking);
            BookingDTO transfer = convertToBookingDTO(saved);
            return Optional.of(transfer);
        }
        return Optional.empty();
    }

    public boolean deleteBooking(String bookingNo) {
        Booking existingBooking = bookingRepo.findByBookingno(bookingNo);
        List<Bookingdetail> detailsList = bookingdetailRepo.findByBookingid(existingBooking);
        if (existingBooking != null && !detailsList.isEmpty()) {
            for (Bookingdetail d : detailsList) {
                bookingdetailRepo.delete(d);
            }
            bookingRepo.delete(existingBooking);
            return true;
        }
        return false;
    }

    public BookingDTO convertToBookingDTO(Booking booking) {
        List<Bookingdetail> detailsList = bookingdetailRepo.findByBookingid(booking);
        Bookingdetail details = detailsList.isEmpty() ? null : detailsList.get(0);

        BookingDTO dto = new BookingDTO();
        dto.setBookingNo(booking.getBookingno());
        dto.setBookingDate(booking.getBookingdate());
        dto.setName(booking.getPackageid().getPkgname());
        dto.setDestination(booking.getPackageid().getDestination());

        if (details != null) {
            dto.setTripStart(details.getTripstart());
            dto.setTripEnd(details.getTripend());
            dto.setDestination(details.getDestination());
            dto.setBasePrice(details.getBaseprice());
            dto.setAgencyCommission(details.getAgencycommission());
        }

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
        String bookingNumber = "";

        do {
            bookingNumber = generateBookingNumber();
        } while (bookingRepo.existsByBookingno(bookingNumber));

        return bookingNumber;
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
}

