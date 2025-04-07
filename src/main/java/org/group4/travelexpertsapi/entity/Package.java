package org.group4.travelexpertsapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "packages", schema = "public")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "packageid", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "pkgname", nullable = false, length = 50)
    private String pkgname;

    @Column(name = "pkgstartdate", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant pkgstartdate;

    @Column(name = "pkgenddate", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant pkgenddate;

    @Size(max = 250)
    @ColumnDefault("NULL")
    @Column(name = "pkgdesc", length = 250)
    private String pkgdesc;

    @NotNull
    @Column(name = "pkgbaseprice", nullable = false, precision = 19, scale = 4)
    private BigDecimal pkgbaseprice;

    @ColumnDefault("NULL")
    @Column(name = "pkgagencycommission", precision = 19, scale = 4)
    private BigDecimal pkgagencycommission;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    private String destination;

    @Column(precision = 8, scale = 4)
    private BigDecimal lat;

    @Column(precision = 8, scale = 4)
    private BigDecimal lng;

    @Column(precision = 2, scale = 1)
    private BigDecimal rating;

    @OneToMany(mappedBy = "pkg", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JsonManagedReference(value = "package-reviews")
    private List<PackageReview> reviews = new ArrayList<>();

    public Package() {
    }

    public Package(BigDecimal pkgagencycommission, BigDecimal pkgbaseprice, String pkgdesc, Instant pkgenddate, Instant pkgstartdate, String pkgname) {
        this.pkgagencycommission = pkgagencycommission;
        this.pkgbaseprice = pkgbaseprice;
        this.pkgdesc = pkgdesc;
        this.pkgenddate = pkgenddate;
        this.pkgstartdate = pkgstartdate;
        this.pkgname = pkgname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPkgname() {
        return pkgname;
    }

    public void setPkgname(String pkgname) {
        this.pkgname = pkgname;
    }

    public Instant getPkgstartdate() {
        return pkgstartdate;
    }

    public void setPkgstartdate(Instant pkgstartdate) {
        this.pkgstartdate = pkgstartdate;
    }

    public Instant getPkgenddate() {
        return pkgenddate;
    }

    public void setPkgenddate(Instant pkgenddate) {
        this.pkgenddate = pkgenddate;
    }

    public String getPkgdesc() {
        return pkgdesc;
    }

    public void setPkgdesc(String pkgdesc) {
        this.pkgdesc = pkgdesc;
    }

    public BigDecimal getPkgbaseprice() {
        return pkgbaseprice;
    }

    public void setPkgbaseprice(BigDecimal pkgbaseprice) {
        this.pkgbaseprice = pkgbaseprice;
    }

    public BigDecimal getPkgagencycommission() {
        return pkgagencycommission;
    }

    public void setPkgagencycommission(BigDecimal pkgagencycommission) {
        this.pkgagencycommission = pkgagencycommission;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public List<PackageReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<PackageReview> reviews) {
        this.reviews = reviews;
    }

}