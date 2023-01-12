package com.employee.EmployeeManagement.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="company")
public class Company {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "domain")
    private String domain;

    @Column(name = "active")
    private Boolean active=true;

    @Column(name="email",unique = true)
    private String companyEmail;

    // one company can have many address
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private List<Address> companyAddress;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "company")
    private List<JhiUsers> jhiUsers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Address> getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(List<Address> companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<JhiUsers> getJhiUsers() {
        return jhiUsers;
    }

    public void setJhiUsers(List<JhiUsers> jhiUsers) {
        this.jhiUsers = jhiUsers;
    }
}
