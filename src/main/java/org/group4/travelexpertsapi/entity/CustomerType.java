package org.group4.travelexpertsapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.group4.travelexpertsapi.entity.auth.WebUser;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class CustomerType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @OneToMany(mappedBy = "customerType", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonManagedReference(value = "type-user")
    private List<WebUser> webUser;

    public CustomerType() {
    }

    public CustomerType(String name, BigDecimal discountPercentage) {
        this.name = name;
        this.discountPercentage = discountPercentage;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
