package org.group4.travelexpertsapi.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "bookingdetails", schema = "public", indexes = {
        @Index(name = "bookingid_idx", columnList = "bookingid"),
        @Index(name = "bookings_bookingdetails_idx", columnList = "bookingid"),
        @Index(name = "dest_id_idx", columnList = "regionid"),
        @Index(name = "destinations_bookingdetails_idx", columnList = "regionid"),
        @Index(name = "classes_bookingdetails_idx", columnList = "classid"),
        @Index(name = "agency_fee_code_idx", columnList = "feeid"),
        @Index(name = "fees_bookingdetails_idx", columnList = "feeid"),
        @Index(name = "products_suppliers_bookingdetails_idx", columnList = "productsupplierid"),
        @Index(name = "productsupplierid_idx", columnList = "productsupplierid")
})
public class Bookingdetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingdetailid", nullable = false)
    private Integer id;

    @Column(name = "itineraryno")
    private Double itineraryno;

    @Column(name = "tripstart", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant tripstart;

    @Column(name = "tripend", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant tripend;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "destination", length = Integer.MAX_VALUE)
    private String destination;

    @ColumnDefault("NULL")
    @Column(name = "baseprice", precision = 19, scale = 4)
    private BigDecimal baseprice;

    @ColumnDefault("NULL")
    @Column(name = "agencycommission", precision = 19, scale = 4)
    private BigDecimal agencycommission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookingid")
    private org.group4.travelexpertsapi.entity.Booking bookingid;

    @ManyToOne(fetch = FetchType.LAZY)
    @ColumnDefault("NULL")
    @JoinColumn(name = "regionid")
    private org.group4.travelexpertsapi.entity.Region regionid;

    @ManyToOne(fetch = FetchType.LAZY)
    @ColumnDefault("NULL")
    @JoinColumn(name = "classid")
    private org.group4.travelexpertsapi.entity.Class classid;

    @ManyToOne(fetch = FetchType.LAZY)
    @ColumnDefault("NULL")
    @JoinColumn(name = "feeid")
    private org.group4.travelexpertsapi.entity.Fee feeid;

    @Column(name = "productsupplierid")
    private Integer productsupplierid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getItineraryno() {
        return itineraryno;
    }

    public void setItineraryno(Double itineraryno) {
        this.itineraryno = itineraryno;
    }

    public Instant getTripstart() {
        return tripstart;
    }

    public void setTripstart(Instant tripstart) {
        this.tripstart = tripstart;
    }

    public Instant getTripend() {
        return tripend;
    }

    public void setTripend(Instant tripend) {
        this.tripend = tripend;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public BigDecimal getBaseprice() {
        return baseprice;
    }

    public void setBaseprice(BigDecimal baseprice) {
        this.baseprice = baseprice;
    }

    public BigDecimal getAgencycommission() {
        return agencycommission;
    }

    public void setAgencycommission(BigDecimal agencycommission) {
        this.agencycommission = agencycommission;
    }

    public org.group4.travelexpertsapi.entity.Booking getBookingid() {
        return bookingid;
    }

    public void setBookingid(org.group4.travelexpertsapi.entity.Booking bookingid) {
        this.bookingid = bookingid;
    }

    public org.group4.travelexpertsapi.entity.Region getRegionid() {
        return regionid;
    }

    public void setRegionid(org.group4.travelexpertsapi.entity.Region regionid) {
        this.regionid = regionid;
    }

    public org.group4.travelexpertsapi.entity.Class getClassid() {
        return classid;
    }

    public void setClassid(org.group4.travelexpertsapi.entity.Class classid) {
        this.classid = classid;
    }

    public org.group4.travelexpertsapi.entity.Fee getFeeid() {
        return feeid;
    }

    public void setFeeid(org.group4.travelexpertsapi.entity.Fee feeid) {
        this.feeid = feeid;
    }

    public Integer getProductsupplierid() {
        return productsupplierid;
    }

    public void setProductsupplierid(Integer productsupplierid) {
        this.productsupplierid = productsupplierid;
    }

}