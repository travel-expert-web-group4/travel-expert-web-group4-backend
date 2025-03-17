package org.group4.travelexpertsapi.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "products_suppliers", schema = "public", indexes = {
        @Index(name = "productid_sidx", columnList = "productid"),
        @Index(name = "products_sproducts_suppliers1_idx", columnList = "productid"),
        @Index(name = "product_supplier_id_sidx", columnList = "supplierid"),
        @Index(name = "suppliers_products_suppliers1_sidx", columnList = "supplierid")
})
public class ProductsSupplier {
    @Id
    @ColumnDefault("nextval('products_suppliers_productsupplierid_seq')")
    @Column(name = "productsupplierid", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productid")
    private Product productid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplierid")
    private org.group4.travelexpertsapi.entity.Supplier supplierid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProductid() {
        return productid;
    }

    public void setProductid(Product productid) {
        this.productid = productid;
    }

    public org.group4.travelexpertsapi.entity.Supplier getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(org.group4.travelexpertsapi.entity.Supplier supplierid) {
        this.supplierid = supplierid;
    }

}