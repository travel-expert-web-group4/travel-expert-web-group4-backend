package org.group4.travelexpertsapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "agents", schema = "public")
public class Agent {
    @Id
    @ColumnDefault("nextval('agents_agentid_seq')")
    @Column(name = "agentid", nullable = false)
    private Integer id;

    @Size(max = 20)
    @ColumnDefault("NULL")
    @Column(name = "agtfirstname", length = 20)
    private String agtfirstname;

    @Size(max = 5)
    @ColumnDefault("NULL")
    @Column(name = "agtmiddleinitial", length = 5)
    private String agtmiddleinitial;

    @Size(max = 20)
    @ColumnDefault("NULL")
    @Column(name = "agtlastname", length = 20)
    private String agtlastname;

    @Size(max = 20)
    @ColumnDefault("NULL")
    @Column(name = "agtbusphone", length = 20)
    private String agtbusphone;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "agtemail", length = 50)
    private String agtemail;

    @Size(max = 20)
    @ColumnDefault("NULL")
    @Column(name = "agtposition", length = 20)
    private String agtposition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agencyid")
    private Agency agencyid;

    @Size(max = 20)
    @ColumnDefault("'agent'")
    @Column(name = "role", length = 20)
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAgtfirstname() {
        return agtfirstname;
    }

    public void setAgtfirstname(String agtfirstname) {
        this.agtfirstname = agtfirstname;
    }

    public String getAgtmiddleinitial() {
        return agtmiddleinitial;
    }

    public void setAgtmiddleinitial(String agtmiddleinitial) {
        this.agtmiddleinitial = agtmiddleinitial;
    }

    public String getAgtlastname() {
        return agtlastname;
    }

    public void setAgtlastname(String agtlastname) {
        this.agtlastname = agtlastname;
    }

    public String getAgtbusphone() {
        return agtbusphone;
    }

    public void setAgtbusphone(String agtbusphone) {
        this.agtbusphone = agtbusphone;
    }

    public String getAgtemail() {
        return agtemail;
    }

    public void setAgtemail(String agtemail) {
        this.agtemail = agtemail;
    }

    public String getAgtposition() {
        return agtposition;
    }

    public void setAgtposition(String agtposition) {
        this.agtposition = agtposition;
    }

    public Agency getAgencyid() {
        return agencyid;
    }

    public void setAgencyid(Agency agencyid) {
        this.agencyid = agencyid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}