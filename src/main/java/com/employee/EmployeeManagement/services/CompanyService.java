package com.employee.EmployeeManagement.services;

import com.employee.EmployeeManagement.entity.Company;
import com.employee.EmployeeManagement.repositories.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyService {
    private final Logger log = LoggerFactory.getLogger(CompanyService.class);
    @Autowired
    private CompanyRepository companyRepository;

    public Company saveCompanyDetails(Company companyDetails){
        return companyRepository.save(companyDetails);
    }

    public List<Company> findAllCompanies(Pageable pageable){
        return companyRepository.findAllByActiveIsTrue(pageable);
    }

    public Optional<Company> findOne(String id){
        return companyRepository.findById(id);
    }

    public void deleteCompany(String id){
        companyRepository.deleteById(id);
    }

    public Optional<Company> findOneByEmail(String email){
        return companyRepository.findByCompanyEmail(email);
    }

    public String generateId(String companyId){
        Long size = companyRepository.countByCompanyId(companyId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMYYdd");
        String id = companyId+formatter.format(ZonedDateTime.now())+'0'+ (size+1);
        return id;
    }
}
