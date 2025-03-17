package org.group4.travelexpertsapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "affiliations", schema = "public")
public class Affiliation {
    @Id
    @Size(max = 10)
    @Column(name = "affilitationid", nullable = false, length = 10)
    private String affilitationid;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "affname", length = 50)
    private String affname;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "affdesc", length = 50)
    private String affdesc;

    public String getAffilitationid() {
        return affilitationid;
    }

    public void setAffilitationid(String affilitationid) {
        this.affilitationid = affilitationid;
    }

    public String getAffname() {
        return affname;
    }

    public void setAffname(String affname) {
        this.affname = affname;
    }

    public String getAffdesc() {
        return affdesc;
    }

    public void setAffdesc(String affdesc) {
        this.affdesc = affdesc;
    }
}