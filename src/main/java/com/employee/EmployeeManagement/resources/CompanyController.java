package com.employee.EmployeeManagement.resources;

import com.employee.EmployeeManagement.constants.Roles;
import com.employee.EmployeeManagement.dto.CompanyDTO;
import com.employee.EmployeeManagement.entity.Company;
import com.employee.EmployeeManagement.entity.JhiUsers;
import com.employee.EmployeeManagement.entity.LoggedUser;
import com.employee.EmployeeManagement.services.CompanyService;
import com.employee.EmployeeManagement.services.JhiUserService;
import com.employee.EmployeeManagement.services.LoggedUserService;
import org.apache.tomcat.util.http.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/company")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {

    private final Logger log = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JhiUserService jhiUserService;

    @Autowired
    private LoggedUserService userService;

    @PostMapping("/create-company")
    public ResponseEntity<LoggedUser> createCompany(@RequestBody CompanyDTO company){
        log.debug("REST : Request to create Company");
        if(company==null){
            throw new RuntimeException("Company Does not exist");
        }

        Optional<Company> existingCompany = companyService.findOneByEmail(company.getCompanyEmail());
        if(existingCompany.isPresent()){
            throw new RuntimeException("Company Email Id Already registered");
        }

        Company request = new Company();
        request.setId(company.getId());
        request.setCompanyAddress(company.getCompanyAddress());
        request.setActive(true);
        request.setCompanyName(company.getCompanyName());
        request.setCompanyEmail(company.getCompanyEmail());
        request.setDomain(company.getDomain());

        LoggedUser user = userService.createUser(company.getCompanyEmail(),"123123", Roles.COMPANY, company.getId());

        companyService.saveCompanyDetails(request);
        log.debug("END : Request to create Company");
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/get-all-companies")
    public ResponseEntity<List<CompanyDTO>> getAllCompaniesInformation(Pageable pageable){
        log.debug("REST : Request to get all Companies");
        List<Company> allCompanies= companyService.findAllCompanies(pageable);
        List<CompanyDTO> companyDTOs = new ArrayList<>();
        allCompanies.forEach(company->{
            CompanyDTO companyDTO = new CompanyDTO();
            companyDTO.setId(company.getId());
            companyDTO.setCompanyName(company.getCompanyName());
            companyDTO.setDomain(company.getDomain());
            companyDTO.setCompanyAddress(company.getCompanyAddress());
            companyDTO.setEmployeeCount(company.getJhiUsers().size());
            companyDTOs.add(companyDTO);
        });
        log.debug("END : Request to get all Companies");
        return ResponseEntity.ok().body(companyDTOs);
    }

    @GetMapping("/get-company-id/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") String id){
        log.debug("REST : Request to get Company By Id");
        Optional<Company> company= companyService.findOne(id);
        if(!company.isPresent()){
            throw new RuntimeException("Company Does not exist");
        }
        log.debug("END : Request to get Company By Id");
        return ResponseEntity.ok().body(company.get());
    }

    @PutMapping("/update-company-details/{id}")
    public ResponseEntity<Company> updateCompanyDetails(@PathVariable("id") String id, @RequestBody Company company){
        log.debug("REST : Request to Update Company {}",id);

        if(id==null){
            throw new RuntimeException("Company Does not exist");
        }
        Optional<Company> existingCompanyDetails = companyService.findOne(id);
        if(!existingCompanyDetails.isPresent()){
            throw new RuntimeException("Company Does not exist");
        }

        if(company.getCompanyName()!=null){
            existingCompanyDetails.get().setCompanyName(company.getCompanyName());
        }
        if(company.getDomain()!=null){
            existingCompanyDetails.get().setDomain(company.getDomain());
        }

        // de-active or active a company
        if(company.getActive()!=null){
            existingCompanyDetails.get().setActive(company.getActive());
        }

        if(company.getActive()!=null){
            existingCompanyDetails.get().setCompanyAddress(company.getCompanyAddress());
        }

        Company companyResult = companyService.saveCompanyDetails(existingCompanyDetails.get());
        log.debug("END : Request to Update Company {}",id);
        return ResponseEntity.ok().body(companyResult);
    }

    @DeleteMapping("/delete-company/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") String id){
        log.debug("REST : Deleting the company for {}",id);
        if(id==null){
            throw new RuntimeException("Company Details not present");
        }

        Optional<Company> existingCompanyDetails = companyService.findOne(id);
        if(!existingCompanyDetails.isPresent()){
            throw new RuntimeException("Company Does not exist");
        }

        companyService.deleteCompany(id);
        log.debug("END : Deleting the company {}",id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-all-users-by-company/{id}")
    public ResponseEntity<List<JhiUsers>> getCompanyUsers(@PathVariable("id") String id, Pageable pageable){
        Optional<Company> existingCompanyDetails = companyService.findOne(id);
        List<JhiUsers> users = jhiUserService.findAllUsersByCompany(existingCompanyDetails.get(),pageable);
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("/migrate-user/{id}")
    public ResponseEntity<Void> migrateUserToUserCompany(@PathVariable("id") String id, @RequestBody CompanyDTO companyDTO){
        if(companyDTO.getId()==null){
            throw new RuntimeException("Please Provide the company you want to migrate to");
        }
        Company company = companyService.findOne(companyDTO.getId()).get();
        if(company.getActive()==false){
            throw new RuntimeException("You have selected a company which is not active.");
        }
        JhiUsers user = jhiUserService.findOneById(id);
        user.setCompany(company);
        jhiUserService.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-if-company-exists/{id}")
    public ResponseEntity<String> migrateUserToUserCompany(@PathVariable("id") String id){
        if(id==null){
            throw new RuntimeException("Please Provide the company Id");
        }
        Optional<Company> company = companyService.findOne(id);
        if(company.isPresent()){
            return ResponseEntity.badRequest().body("Company Id Not Available");
        }
        return ResponseEntity.ok().body("Company Id Available");
    }
}
