package org.group4.travelexpertsapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "regions", schema = "public")
public class Region {
    @Id
    @Size(max = 5)
    @Column(name = "regionid", nullable = false, length = 5)
    private String regionid;

    @Size(max = 25)
    @ColumnDefault("NULL")
    @Column(name = "regionname", length = 25)
    private String regionname;

    public String getRegionid() {
        return regionid;
    }

    public void setRegionid(String regionid) {
        this.regionid = regionid;
    }

    public String getRegionname() {
        return regionname;
    }

    public void setRegionname(String regionname) {
        this.regionname = regionname;
    }

}