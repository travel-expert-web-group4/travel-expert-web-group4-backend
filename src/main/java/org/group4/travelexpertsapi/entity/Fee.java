package org.group4.travelexpertsapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@Table(name = "fees", schema = "public")
public class Fee {
    @Id
    @Size(max = 10)
    @Column(name = "feeid", nullable = false, length = 10)
    private String feeid;

    @Size(max = 50)
    @NotNull
    @Column(name = "feename", nullable = false, length = 50)
    private String feename;

    @NotNull
    @Column(name = "feeamt", nullable = false, precision = 19, scale = 4)
    private BigDecimal feeamt;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "feedesc", length = 50)
    private String feedesc;

    public String getFeeid() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid;
    }

    public String getFeename() {
        return feename;
    }

    public void setFeename(String feename) {
        this.feename = feename;
    }

    public BigDecimal getFeeamt() {
        return feeamt;
    }

    public void setFeeamt(BigDecimal feeamt) {
        this.feeamt = feeamt;
    }

    public String getFeedesc() {
        return feedesc;
    }

    public void setFeedesc(String feedesc) {
        this.feedesc = feedesc;
    }

}