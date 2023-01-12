package com.employee.EmployeeManagement.repositories;

import com.employee.EmployeeManagement.constants.Roles;
import com.employee.EmployeeManagement.entity.LoggedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<LoggedUser,Long> {
    Optional<LoggedUser> findByUserNameAndRole(String userName, Roles roles);
}
