package org.group4.travelexpertsapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PackagesProductsSupplierId implements Serializable {
    private static final long serialVersionUID = 2870704287205793260L;
    @NotNull
    @Column(name = "packageid", nullable = false)
    private Integer packageid;

    @NotNull
    @Column(name = "productsupplierid", nullable = false)
    private Integer productsupplierid;

    public Integer getPackageid() {
        return packageid;
    }

    public void setPackageid(Integer packageid) {
        this.packageid = packageid;
    }

    public Integer getProductsupplierid() {
        return productsupplierid;
    }

    public void setProductsupplierid(Integer productsupplierid) {
        this.productsupplierid = productsupplierid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PackagesProductsSupplierId entity = (PackagesProductsSupplierId) o;
        return Objects.equals(this.productsupplierid, entity.productsupplierid) &&
                Objects.equals(this.packageid, entity.packageid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productsupplierid, packageid);
    }

}