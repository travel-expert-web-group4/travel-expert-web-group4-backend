package org.group4.travelexpertsapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "users", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "users_agentid_key", columnNames = {"agentid"}),
        @UniqueConstraint(name = "users_email_key", columnNames = {"email"})
})
public class User {
    @Id
    @ColumnDefault("nextval('users_user_id_seq')")
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "agentid", nullable = false)
    private Agent agentid;

    @Size(max = 50)
    @NotNull
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Size(max = 20)
    @NotNull
    @Column(name = "role", nullable = false, length = 20)
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Agent getAgentid() {
        return agentid;
    }

    public void setAgentid(Agent agentid) {
        this.agentid = agentid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}