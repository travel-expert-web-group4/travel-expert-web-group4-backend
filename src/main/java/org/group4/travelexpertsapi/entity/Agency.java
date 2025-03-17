package org.group4.travelexpertsapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "agencies", schema = "public")
public class Agency {
    @Id
    @ColumnDefault("nextval('agencies_agencyid_seq')")
    @Column(name = "agencyid", nullable = false)
    private Integer id;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "agncyaddress", length = 50)
    private String agncyaddress;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "agncycity", length = 50)
    private String agncycity;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "agncyprov", length = 50)
    private String agncyprov;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "agncypostal", length = 50)
    private String agncypostal;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "agncycountry", length = 50)
    private String agncycountry;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "agncyphone", length = 50)
    private String agncyphone;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "agncyfax", length = 50)
    private String agncyfax;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAgncyaddress() {
        return agncyaddress;
    }

    public void setAgncyaddress(String agncyaddress) {
        this.agncyaddress = agncyaddress;
    }

    public String getAgncycity() {
        return agncycity;
    }

    public void setAgncycity(String agncycity) {
        this.agncycity = agncycity;
    }

    public String getAgncyprov() {
        return agncyprov;
    }

    public void setAgncyprov(String agncyprov) {
        this.agncyprov = agncyprov;
    }

    public String getAgncypostal() {
        return agncypostal;
    }

    public void setAgncypostal(String agncypostal) {
        this.agncypostal = agncypostal;
    }

    public String getAgncycountry() {
        return agncycountry;
    }

    public void setAgncycountry(String agncycountry) {
        this.agncycountry = agncycountry;
    }

    public String getAgncyphone() {
        return agncyphone;
    }

    public void setAgncyphone(String agncyphone) {
        this.agncyphone = agncyphone;
    }

    public String getAgncyfax() {
        return agncyfax;
    }

    public void setAgncyfax(String agncyfax) {
        this.agncyfax = agncyfax;
    }

}