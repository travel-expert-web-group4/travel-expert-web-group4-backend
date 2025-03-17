package org.group4.travelexpertsapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "bookings", schema = "public", indexes = {
        @Index(name = "bookings_customerid_idx", columnList = "customerid"),
        @Index(name = "customers_bookings_idx", columnList = "customerid"),
        @Index(name = "triptypes_bookings_idx", columnList = "triptypeid"),
        @Index(name = "packageid_idx", columnList = "packageid"),
        @Index(name = "packages_bookings_idx", columnList = "packageid")
})
public class Booking {
    @Id
    @ColumnDefault("nextval('bookings_bookingid_seq')")
    @Column(name = "bookingid", nullable = false)
    private Integer id;

    @Column(name = "bookingdate")
    private Instant bookingdate;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "bookingno", length = 50)
    private String bookingno;

    @Column(name = "travelercount")
    private Double travelercount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerid")
    private org.group4.travelexpertsapi.entity.Customer customerid;

    @ManyToOne(fetch = FetchType.LAZY)
    @ColumnDefault("NULL")
    @JoinColumn(name = "triptypeid")
    private org.group4.travelexpertsapi.entity.Triptype triptypeid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packageid")
    private org.group4.travelexpertsapi.entity.Package packageid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(Instant bookingdate) {
        this.bookingdate = bookingdate;
    }

    public String getBookingno() {
        return bookingno;
    }

    public void setBookingno(String bookingno) {
        this.bookingno = bookingno;
    }

    public Double getTravelercount() {
        return travelercount;
    }

    public void setTravelercount(Double travelercount) {
        this.travelercount = travelercount;
    }

    public org.group4.travelexpertsapi.entity.Customer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(org.group4.travelexpertsapi.entity.Customer customerid) {
        this.customerid = customerid;
    }

    public org.group4.travelexpertsapi.entity.Triptype getTriptypeid() {
        return triptypeid;
    }

    public void setTriptypeid(org.group4.travelexpertsapi.entity.Triptype triptypeid) {
        this.triptypeid = triptypeid;
    }

    public org.group4.travelexpertsapi.entity.Package getPackageid() {
        return packageid;
    }

    public void setPackageid(org.group4.travelexpertsapi.entity.Package packageid) {
        this.packageid = packageid;
    }

}