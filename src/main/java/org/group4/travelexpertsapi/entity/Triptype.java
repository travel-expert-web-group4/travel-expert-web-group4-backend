package org.group4.travelexpertsapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "triptypes", schema = "public")
public class Triptype {
    @Id
    @Size(max = 1)
    @Column(name = "triptypeid", nullable = false, length = 1)
    private String triptypeid;

    @Size(max = 25)
    @ColumnDefault("NULL")
    @Column(name = "ttname", length = 25)
    private String ttname;

    public String getTriptypeid() {
        return triptypeid;
    }

    public void setTriptypeid(String triptypeid) {
        this.triptypeid = triptypeid;
    }

    public String getTtname() {
        return ttname;
    }

    public void setTtname(String ttname) {
        this.ttname = ttname;
    }

}