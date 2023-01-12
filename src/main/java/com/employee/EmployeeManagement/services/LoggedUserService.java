package com.employee.EmployeeManagement.services;

import com.employee.EmployeeManagement.constants.Roles;
import com.employee.EmployeeManagement.entity.LoggedUser;
import com.employee.EmployeeManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@Transactional
public class LoggedUserService {

    @Autowired
    private UserRepository userRepository;

    public LoggedUser createUser(String userName, String password, Roles role,String entityId){
        LoggedUser loggedUser = new LoggedUser();
        loggedUser.setUserName(userName);
        loggedUser.setPassword(password);
        loggedUser.setRole(role);
        loggedUser.setCreateAt(ZonedDateTime.now());
        loggedUser.setEntityId(entityId);
        LoggedUser user = userRepository.save(loggedUser);
        return user;
    }

    public Optional<LoggedUser> findOneByUserName(String userName,Roles roles){
        return userRepository.findByUserNameAndRole(userName,roles);
    }
}
