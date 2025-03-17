package org.group4.travelexpertsapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "suppliers", schema = "public")
public class Supplier {
    @Id
    @Column(name = "supplierid", nullable = false)
    private Integer id;

    @Column(name = "supname", length = Integer.MAX_VALUE)
    private String supname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSupname() {
        return supname;
    }

    public void setSupname(String supname) {
        this.supname = supname;
    }

}