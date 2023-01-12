package com.employee.EmployeeManagement.resources;

import com.employee.EmployeeManagement.constants.Roles;
import com.employee.EmployeeManagement.dto.CompanyDTO;
import com.employee.EmployeeManagement.dto.JhiUserDTO;
import com.employee.EmployeeManagement.entity.Address;
import com.employee.EmployeeManagement.entity.Company;
import com.employee.EmployeeManagement.entity.JhiUsers;
import com.employee.EmployeeManagement.entity.LoggedUser;
import com.employee.EmployeeManagement.services.AddressService;
import com.employee.EmployeeManagement.services.CompanyService;
import com.employee.EmployeeManagement.services.JhiUserService;
import com.employee.EmployeeManagement.services.LoggedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/users")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class JhiUserController {

    @Autowired
    private JhiUserService jhiUserService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private LoggedUserService userService;

    @PostMapping("/add-user")
    public ResponseEntity<JhiUsers> addUser(@RequestBody JhiUsers jhiUser, @RequestHeader("companyId") String companyId){
        if(jhiUser==null){
            throw new RuntimeException("Data Not Available");
        }

        Optional<Company> company = companyService.findOne(companyId);
        if(!company.isPresent()){
            throw new RuntimeException("Company not Found");
        }
        String id = companyService.generateId(companyId);
        jhiUser.setId(id);
        jhiUser.setCompany(company.get());

        LoggedUser user = userService.createUser(jhiUser.getEmail(),"123123", Roles.USER, jhiUser.getId());
        JhiUsers result = jhiUserService.save(jhiUser);
        result.setCompany(company.get());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<JhiUsers>> getAllUsers(Pageable pageable){
        List<JhiUsers> allUsers = jhiUserService.findAllUsers(pageable);
        return ResponseEntity.ok().body(allUsers);
    }

    @GetMapping("/get-user/{id}")
    @Transactional
    public ResponseEntity<JhiUserDTO> getUserById(@PathVariable("id") String id){
        if(id==null){
            throw new RuntimeException("User Not Found");
        }
        JhiUsers user = jhiUserService.findOneById(id);
        if(user==null){
            throw new RuntimeException("User Not Found");
        }
        JhiUserDTO jhiUserDTO = new JhiUserDTO();
        jhiUserDTO.setId(user.getId());
        jhiUserDTO.setFirstName(user.getFirstName());
        jhiUserDTO.setLastName(user.getLastName());
        jhiUserDTO.setDateOfBirth(user.getDateOfBirth());

        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(user.getCompany().getId());
        companyDTO.setCompanyName(user.getCompany().getCompanyName());
        companyDTO.setDomain(user.getCompany().getDomain());
        jhiUserDTO.setCompany(companyDTO);

        jhiUserDTO.setEmail(user.getEmail());
        jhiUserDTO.setActive(user.getActive());
        jhiUserDTO.setContactNumber(user.getContactNumber());
        jhiUserDTO.setAddresses(user.getAddresses());
        jhiUserDTO.setDesignation(user.getDesignation());
        return ResponseEntity.ok().body(jhiUserDTO);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") String id){
        if(id==null){
            throw new RuntimeException("User Not Found");
        }
        JhiUsers user = jhiUserService.findOneById(id);
        if(user==null){
            throw new RuntimeException("User Not Found");
        }
        jhiUserService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<JhiUserDTO> updateUser(@PathVariable("id")String id,@RequestBody JhiUserDTO user){
        if(id==null){
            throw new RuntimeException("User Not Found");
        }
        JhiUsers existingUser = jhiUserService.findOneById(id);
        if(existingUser==null){
            throw new RuntimeException("User Not Found");
        }
        if(user.getActive()!=null){
            existingUser.setActive(user.getActive());
        }

        if(user.getFirstName()!=null){
            existingUser.setFirstName(user.getFirstName());
        }
        if(user.getLastName()!=null){
            existingUser.setLastName(user.getLastName());
        }
        if(user.getDesignation()!=null){
            existingUser.setDesignation(user.getDesignation());
        }
        if(user.getAddresses()!=null){
            existingUser.setAddresses(user.getAddresses());
        }
        jhiUserService.save(existingUser);
        user.setId(existingUser.getId());
        return ResponseEntity.ok().body(user);
    }
}
