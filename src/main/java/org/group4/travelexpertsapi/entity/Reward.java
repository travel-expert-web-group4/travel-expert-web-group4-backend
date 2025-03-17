package org.group4.travelexpertsapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "rewards", schema = "public")
public class Reward {
    @Id
    @Column(name = "rewardid", nullable = false)
    private Integer id;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "rwdname", length = 50)
    private String rwdname;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "rwddesc", length = 50)
    private String rwddesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRwdname() {
        return rwdname;
    }

    public void setRwdname(String rwdname) {
        this.rwdname = rwdname;
    }

    public String getRwddesc() {
        return rwddesc;
    }

    public void setRwddesc(String rwddesc) {
        this.rwddesc = rwddesc;
    }

}