package org.group4.travelexpertsapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "creditcards", schema = "public", indexes = {
        @Index(name = "customers_creditcards_idx", columnList = "customerid")
})
public class Creditcard {
    @Id
    @ColumnDefault("nextval('creditcards_creditcardid_seq')")
    @Column(name = "creditcardid", nullable = false)
    private Integer id;

    @Size(max = 10)
    @NotNull
    @Column(name = "ccname", nullable = false, length = 10)
    private String ccname;

    @Size(max = 50)
    @NotNull
    @Column(name = "ccnumber", nullable = false, length = 50)
    private String ccnumber;

    @NotNull
    @Column(name = "ccexpiry", nullable = false)
    private Instant ccexpiry;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customerid", nullable = false)
    private org.group4.travelexpertsapi.entity.Customer customerid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCcname() {
        return ccname;
    }

    public void setCcname(String ccname) {
        this.ccname = ccname;
    }

    public String getCcnumber() {
        return ccnumber;
    }

    public void setCcnumber(String ccnumber) {
        this.ccnumber = ccnumber;
    }

    public Instant getCcexpiry() {
        return ccexpiry;
    }

    public void setCcexpiry(Instant ccexpiry) {
        this.ccexpiry = ccexpiry;
    }

    public org.group4.travelexpertsapi.entity.Customer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(org.group4.travelexpertsapi.entity.Customer customerid) {
        this.customerid = customerid;
    }

}