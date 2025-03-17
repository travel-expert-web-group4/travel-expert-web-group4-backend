package org.group4.travelexpertsapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "packages", schema = "public")
public class Package {
    @Id
    @ColumnDefault("nextval('packages_packageid_seq')")
    @Column(name = "packageid", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "pkgname", nullable = false, length = 50)
    private String pkgname;

    @Column(name = "pkgstartdate")
    private Instant pkgstartdate;

    @Column(name = "pkgenddate")
    private Instant pkgenddate;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "pkgdesc", length = 50)
    private String pkgdesc;

    @NotNull
    @Column(name = "pkgbaseprice", nullable = false, precision = 19, scale = 4)
    private BigDecimal pkgbaseprice;

    @ColumnDefault("NULL")
    @Column(name = "pkgagencycommission", precision = 19, scale = 4)
    private BigDecimal pkgagencycommission;

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

}