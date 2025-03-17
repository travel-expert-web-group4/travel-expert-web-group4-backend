package org.group4.travelexpertsapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "classes", schema = "public")
public class Class {
    @Id
    @Size(max = 5)
    @Column(name = "classid", nullable = false, length = 5)
    private String classid;

    @Size(max = 20)
    @NotNull
    @Column(name = "classname", nullable = false, length = 20)
    private String classname;

    @Size(max = 50)
    @ColumnDefault("NULL")
    @Column(name = "classdesc", length = 50)
    private String classdesc;

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getClassdesc() {
        return classdesc;
    }

    public void setClassdesc(String classdesc) {
        this.classdesc = classdesc;
    }

}