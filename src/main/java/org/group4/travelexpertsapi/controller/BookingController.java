package org.group4.travelexpertsapi.controller;

import org.group4.travelexpertsapi.dto.BookingDTO;
import org.group4.travelexpertsapi.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{booking_no}")
    public ResponseEntity<BookingDTO> getBookingByBookingNo(@PathVariable String booking_no) {
        Optional<BookingDTO> booking = bookingService.getBookingByBookingNo(booking_no);
        return booking.map(bookingDTO -> new ResponseEntity<>(bookingDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/customer/{customer_id}")
    public ResponseEntity<List<BookingDTO>> getBookingsByCustomerId(@PathVariable Integer customer_id) {
        Optional<List<BookingDTO>> bookings = bookingService.getBookingsByCustomerId(customer_id);
        return bookings.map(bookingDTOS -> new ResponseEntity<>(bookingDTOS, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/new/{trip_type}/{customer_id}/{package_id}")
    public ResponseEntity<BookingDTO> createBooking(@PathVariable String trip_type, @PathVariable Integer customer_id, @PathVariable Integer package_id, @RequestBody BookingDTO bookingDTO) {
        Optional<BookingDTO> newBooking = bookingService.addBooking(customer_id, package_id, trip_type, bookingDTO);
        return newBooking.map(dto -> new ResponseEntity<>(dto, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PutMapping("/{booking_no}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable String booking_no, @RequestBody BookingDTO bookingDTO) {
        Optional<BookingDTO> updatedBooking = bookingService.updateBooking(booking_no, bookingDTO);
        return updatedBooking.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{booking_no}")
    public ResponseEntity<Void> deleteBooking(@PathVariable String booking_no) {
        if (bookingService.deleteBooking(booking_no)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
