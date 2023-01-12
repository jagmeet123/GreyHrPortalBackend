package com.employee.EmployeeManagement.dto;

import com.employee.EmployeeManagement.entity.Address;
import com.employee.EmployeeManagement.entity.Company;
import java.time.ZonedDateTime;
import java.util.List;

public class JhiUserDTO {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private ZonedDateTime dateOfBirth;

    private String designation;

    private CompanyDTO company;

    private Boolean active;

    private String contactNumber;

    private List<Address> addresses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(ZonedDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
