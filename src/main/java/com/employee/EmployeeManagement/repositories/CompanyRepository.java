package com.employee.EmployeeManagement.repositories;

import com.employee.EmployeeManagement.entity.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,String> {
    List<Company> findAllByActiveIsTrue(Pageable pageable);

    Optional<Company> findByCompanyEmail(String email);

    @Query(value = "select count(*) from jhi_user where company_id=:companyId",nativeQuery = true)
    long countByCompanyId(String companyId);
}
