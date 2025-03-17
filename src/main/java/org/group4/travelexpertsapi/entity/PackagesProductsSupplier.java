package org.group4.travelexpertsapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "packages_products_suppliers", schema = "public", indexes = {
        @Index(name = "packages_products_suppliers_packageid_idx", columnList = "packageid"),
        @Index(name = "products_suppliers_packages_products_suppliers_idx", columnList = "productsupplierid")
})
public class PackagesProductsSupplier {
    @EmbeddedId
    private PackagesProductsSupplierId id;

    @MapsId("packageid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "packageid", nullable = false)
    private Package packageid;

    @MapsId("productsupplierid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "productsupplierid", nullable = false)
    private org.group4.travelexpertsapi.entity.ProductsSupplier productsupplierid;

    public PackagesProductsSupplierId getId() {
        return id;
    }

    public void setId(PackagesProductsSupplierId id) {
        this.id = id;
    }

    public Package getPackageid() {
        return packageid;
    }

    public void setPackageid(Package packageid) {
        this.packageid = packageid;
    }

    public org.group4.travelexpertsapi.entity.ProductsSupplier getProductsupplierid() {
        return productsupplierid;
    }

    public void setProductsupplierid(org.group4.travelexpertsapi.entity.ProductsSupplier productsupplierid) {
        this.productsupplierid = productsupplierid;
    }

}