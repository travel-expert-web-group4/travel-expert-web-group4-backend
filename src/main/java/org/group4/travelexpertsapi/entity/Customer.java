package org.group4.travelexpertsapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.group4.travelexpertsapi.entity.auth.WebUser;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "customers", schema = "public", indexes = {
        @Index(name = "employees_customers_idx", columnList = "agentid")
})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerid", nullable = false)
    private Integer id;

    @Size(max = 25)
    @NotNull
    @Column(name = "custfirstname", nullable = false, length = 25)
    private String custfirstname;

    @Size(max = 25)
    @NotNull
    @Column(name = "custlastname", nullable = false, length = 25)
    private String custlastname;

    @Size(max = 75)
    @NotNull
    @Column(name = "custaddress", nullable = false, length = 75)
    private String custaddress;

    @Size(max = 50)
    @NotNull
    @Column(name = "custcity", nullable = false, length = 50)
    private String custcity;

    @Size(max = 2)
    @NotNull
    @Column(name = "custprov", nullable = false, length = 2)
    private String custprov;

    @Size(max = 7)
    @NotNull
    @Column(name = "custpostal", nullable = false, length = 7)
    private String custpostal;

    @Size(max = 25)
    @ColumnDefault("NULL")
    @Column(name = "custcountry", length = 25)
    private String custcountry;

    @Size(max = 20)
    @ColumnDefault("NULL")
    @Column(name = "custhomephone", length = 20)
    private String custhomephone;

    @Size(max = 20)
    @NotNull
    @Column(name = "custbusphone", nullable = false, length = 20)
    private String custbusphone;

    @Size(max = 50)
    @NotNull
    @Column(name = "custemail", nullable = false, unique = true, length = 50)
    private String custemail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agentid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Agent agentid;

    @OneToOne(mappedBy = "customer")
    @JsonBackReference(value = "user-customer")
    private WebUser webUser;

//    @Column(name = "profile_image_url")
//    private String profileImageUrl;

    public Customer() {
    }

    public Customer(String custemail, String custbusphone, String custhomephone, String custcountry, String custpostal, String custprov, String custcity, String custaddress, String custlastname, String custfirstname) {
        this.custemail = custemail;
        this.custbusphone = custbusphone;
        this.custhomephone = custhomephone;
        this.custcountry = custcountry;
        this.custpostal = custpostal;
        this.custprov = custprov;
        this.custcity = custcity;
        this.custaddress = custaddress;
        this.custlastname = custlastname;
        this.custfirstname = custfirstname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustfirstname() {
        return custfirstname;
    }

    public void setCustfirstname(String custfirstname) {
        this.custfirstname = custfirstname;
    }

    public String getCustlastname() {
        return custlastname;
    }

    public void setCustlastname(String custlastname) {
        this.custlastname = custlastname;
    }

    public String getCustaddress() {
        return custaddress;
    }

    public void setCustaddress(String custaddress) {
        this.custaddress = custaddress;
    }

    public String getCustcity() {
        return custcity;
    }

    public void setCustcity(String custcity) {
        this.custcity = custcity;
    }

    public String getCustprov() {
        return custprov;
    }

    public void setCustprov(String custprov) {
        this.custprov = custprov;
    }

    public String getCustpostal() {
        return custpostal;
    }

    public void setCustpostal(String custpostal) {
        this.custpostal = custpostal;
    }

    public String getCustcountry() {
        return custcountry;
    }

    public void setCustcountry(String custcountry) {
        this.custcountry = custcountry;
    }

    public String getCusthomephone() {
        return custhomephone;
    }

    public void setCusthomephone(String custhomephone) {
        this.custhomephone = custhomephone;
    }

    public String getCustbusphone() {
        return custbusphone;
    }

    public void setCustbusphone(String custbusphone) {
        this.custbusphone = custbusphone;
    }

    public String getCustemail() {
        return custemail;
    }

    public void setCustemail(String custemail) {
        this.custemail = custemail;
    }

    public Agent getAgentid() {
        return agentid;
    }

    public void setAgentid(Agent agentid) {
        this.agentid = agentid;
    }

    public WebUser getWebUser() {
        return webUser;
    }

    public void setWebUser(WebUser webUser) {
        this.webUser = webUser;
    }

//    public String getProfileImageUrl() {
//        return profileImageUrl;
//    }
//
//    public void setProfileImageUrl(String profileImageUrl) {
//        this.profileImageUrl = profileImageUrl;
//    }
}