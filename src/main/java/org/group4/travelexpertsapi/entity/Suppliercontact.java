package org.group4.travelexpertsapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "suppliercontacts", schema = "public", indexes = {
        @Index(name = "affiliations_supcon_idx", columnList = "affiliationid"),
        @Index(name = "suppliers_supcon_idx", columnList = "supplierid")
})
public class Suppliercontact {
    @Id
    @Column(name = "suppliercontactid", nullable = false)
    private Integer id;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "supconfirstname", length = 50)
    private String supconfirstname;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "supconlastname", length = 50)
    private String supconlastname;

    @Column(name = "supconcompany", length = Integer.MAX_VALUE)
    private String supconcompany;

    @Column(name = "supconaddress", length = Integer.MAX_VALUE)
    private String supconaddress;

    @Column(name = "supconcity", length = Integer.MAX_VALUE)
    private String supconcity;

    @Column(name = "supconprov", length = Integer.MAX_VALUE)
    private String supconprov;

    @Column(name = "supconpostal", length = Integer.MAX_VALUE)
    private String supconpostal;

    @Column(name = "supconcountry", length = Integer.MAX_VALUE)
    private String supconcountry;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "supconbusphone", length = 50)
    private String supconbusphone;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "supconfax", length = 50)
    private String supconfax;

    @Column(name = "supconemail", length = Integer.MAX_VALUE)
    private String supconemail;

    @Column(name = "supconurl", length = Integer.MAX_VALUE)
    private String supconurl;

    @ManyToOne(fetch = FetchType.LAZY)
    @ColumnDefault("NULL")
    @JoinColumn(name = "affiliationid")
    private Affiliation affiliationid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplierid")
    private org.group4.travelexpertsapi.entity.Supplier supplierid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSupconfirstname() {
        return supconfirstname;
    }

    public void setSupconfirstname(String supconfirstname) {
        this.supconfirstname = supconfirstname;
    }

    public String getSupconlastname() {
        return supconlastname;
    }

    public void setSupconlastname(String supconlastname) {
        this.supconlastname = supconlastname;
    }

    public String getSupconcompany() {
        return supconcompany;
    }

    public void setSupconcompany(String supconcompany) {
        this.supconcompany = supconcompany;
    }

    public String getSupconaddress() {
        return supconaddress;
    }

    public void setSupconaddress(String supconaddress) {
        this.supconaddress = supconaddress;
    }

    public String getSupconcity() {
        return supconcity;
    }

    public void setSupconcity(String supconcity) {
        this.supconcity = supconcity;
    }

    public String getSupconprov() {
        return supconprov;
    }

    public void setSupconprov(String supconprov) {
        this.supconprov = supconprov;
    }

    public String getSupconpostal() {
        return supconpostal;
    }

    public void setSupconpostal(String supconpostal) {
        this.supconpostal = supconpostal;
    }

    public String getSupconcountry() {
        return supconcountry;
    }

    public void setSupconcountry(String supconcountry) {
        this.supconcountry = supconcountry;
    }

    public String getSupconbusphone() {
        return supconbusphone;
    }

    public void setSupconbusphone(String supconbusphone) {
        this.supconbusphone = supconbusphone;
    }

    public String getSupconfax() {
        return supconfax;
    }

    public void setSupconfax(String supconfax) {
        this.supconfax = supconfax;
    }

    public String getSupconemail() {
        return supconemail;
    }

    public void setSupconemail(String supconemail) {
        this.supconemail = supconemail;
    }

    public String getSupconurl() {
        return supconurl;
    }

    public void setSupconurl(String supconurl) {
        this.supconurl = supconurl;
    }

    public Affiliation getAffiliationid() {
        return affiliationid;
    }

    public void setAffiliationid(Affiliation affiliationid) {
        this.affiliationid = affiliationid;
    }

    public org.group4.travelexpertsapi.entity.Supplier getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(org.group4.travelexpertsapi.entity.Supplier supplierid) {
        this.supplierid = supplierid;
    }

}