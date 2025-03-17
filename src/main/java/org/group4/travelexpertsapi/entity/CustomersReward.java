package org.group4.travelexpertsapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "customers_rewards", schema = "public", indexes = {
        @Index(name = "customers_customers_rewards_idx", columnList = "customerid"),
        @Index(name = "rewards_customers_rewards_idx", columnList = "rewardid")
})
public class CustomersReward {
    @EmbeddedId
    private CustomersRewardId id;

    @MapsId("customerid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customerid", nullable = false)
    private Customer customerid;

    @MapsId("rewardid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rewardid", nullable = false)
    private org.group4.travelexpertsapi.entity.Reward rewardid;

    @Size(max = 25)
    @NotNull
    @Column(name = "rwdnumber", nullable = false, length = 25)
    private String rwdnumber;

    public CustomersRewardId getId() {
        return id;
    }

    public void setId(CustomersRewardId id) {
        this.id = id;
    }

    public Customer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Customer customerid) {
        this.customerid = customerid;
    }

    public org.group4.travelexpertsapi.entity.Reward getRewardid() {
        return rewardid;
    }

    public void setRewardid(org.group4.travelexpertsapi.entity.Reward rewardid) {
        this.rewardid = rewardid;
    }

    public String getRwdnumber() {
        return rwdnumber;
    }

    public void setRwdnumber(String rwdnumber) {
        this.rwdnumber = rwdnumber;
    }

}