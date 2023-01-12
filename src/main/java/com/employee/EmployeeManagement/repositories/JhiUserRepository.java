package com.employee.EmployeeManagement.repositories;
import com.employee.EmployeeManagement.entity.Company;
import com.employee.EmployeeManagement.entity.JhiUsers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JhiUserRepository extends JpaRepository<JhiUsers,String> {

    List<JhiUsers> findAllByActiveIsTrue(Pageable pageable);

    List<JhiUsers> findAllByCompanyAndActiveIsTrue(Company company, Pageable pageable);
}
