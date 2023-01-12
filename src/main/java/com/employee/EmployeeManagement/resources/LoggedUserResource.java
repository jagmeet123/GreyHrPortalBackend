package com.employee.EmployeeManagement.resources;

import com.employee.EmployeeManagement.dto.LoggedUserDTO;
import com.employee.EmployeeManagement.entity.LoggedUser;
import com.employee.EmployeeManagement.services.LoggedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:3000")
public class LoggedUserResource {

    @Autowired
    private LoggedUserService loggedUserService;

    @PostMapping("/authenticate")
    public ResponseEntity<LoggedUser> getLoggedInUser(@RequestBody LoggedUserDTO userDTO){
        if(StringUtils.isEmpty(userDTO.getUserName())){
            throw new RuntimeException("Please Provide the user Name");
        }
        if(StringUtils.isEmpty(userDTO.getPassword())){
            throw new RuntimeException("Please Provide the Password");
        }
        Optional<LoggedUser> loggedUserDTO = loggedUserService.findOneByUserName(userDTO.getUserName(),userDTO.getRole());
        if(!loggedUserDTO.isPresent()){
            throw new RuntimeException("User Not found");
        }
        Boolean isPasswordCorrect = loggedUserDTO.get().getPassword().equals(userDTO.getPassword());
        if(!isPasswordCorrect){
            throw new RuntimeException("Please Provide the Correct Password");
        }
        if(!userDTO.getRole().equals(loggedUserDTO.get().getRole())){
            throw new RuntimeException("Incorrect Credentials");
        }
        return ResponseEntity.ok(loggedUserDTO.get());
    }
}
