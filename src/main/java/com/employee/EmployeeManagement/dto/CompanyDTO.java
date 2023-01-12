package com.employee.EmployeeManagement.dto;

import com.employee.EmployeeManagement.entity.Address;
import com.employee.EmployeeManagement.entity.JhiUsers;

import javax.persistence.*;
import java.util.List;

public class CompanyDTO {

    private String id;

    private String companyName;

    private String domain;

    private Boolean active;

    private List<Address> companyAddress;

    private int employeeCount;


    private String companyEmail;


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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Address> getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(List<Address> companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }
}
