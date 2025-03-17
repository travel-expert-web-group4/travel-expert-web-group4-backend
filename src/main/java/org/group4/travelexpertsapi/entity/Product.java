package org.group4.travelexpertsapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "products", schema = "public")
public class Product {
    @Id
    @ColumnDefault("nextval('products_productid_seq')")
    @Column(name = "productid", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "prodname", nullable = false, length = 50)
    private String prodname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname;
    }

}