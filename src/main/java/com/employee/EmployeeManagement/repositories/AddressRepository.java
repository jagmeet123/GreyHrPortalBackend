package com.employee.EmployeeManagement.repositories;

import com.employee.EmployeeManagement.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {

}
