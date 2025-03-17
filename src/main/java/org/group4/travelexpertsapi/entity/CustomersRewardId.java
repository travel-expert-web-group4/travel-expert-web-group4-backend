package org.group4.travelexpertsapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CustomersRewardId implements Serializable {
    private static final long serialVersionUID = -3490652043893024805L;
    @NotNull
    @Column(name = "customerid", nullable = false)
    private Integer customerid;

    @NotNull
    @Column(name = "rewardid", nullable = false)
    private Integer rewardid;

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public Integer getRewardid() {
        return rewardid;
    }

    public void setRewardid(Integer rewardid) {
        this.rewardid = rewardid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CustomersRewardId entity = (CustomersRewardId) o;
        return Objects.equals(this.rewardid, entity.rewardid) &&
                Objects.equals(this.customerid, entity.customerid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rewardid, customerid);
    }

}