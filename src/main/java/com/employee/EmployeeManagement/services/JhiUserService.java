package com.employee.EmployeeManagement.services;
import com.employee.EmployeeManagement.entity.Company;
import com.employee.EmployeeManagement.entity.JhiUsers;
import com.employee.EmployeeManagement.repositories.JhiUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JhiUserService {
    private final Logger log =  LoggerFactory.getLogger(JhiUserService.class);

    @Autowired
    private JhiUserRepository jhiUserRepository;

    public JhiUsers findOneById(String id){
        log.debug("Request to get the user by Id {}",id);
        Optional<JhiUsers> user = jhiUserRepository.findById(id);
        return user.isPresent() ? user.get() : null;
    }

    public void deleteUserById(String id){
        log.debug("Request to delete the user by Id {}",id);
        jhiUserRepository.deleteById(id);
    }
    public List<JhiUsers> findAllUsers(Pageable pageable){
        log.debug("Request to get all the users");
        return jhiUserRepository.findAllByActiveIsTrue(pageable);
    }

    public JhiUsers save(JhiUsers user){
        log.debug("Saving the users");
        return jhiUserRepository.save(user);
    }

    public List<JhiUsers> findAllUsersByCompany(Company company, Pageable pageable){
        log.debug("Getting the users By Company Id");
        return jhiUserRepository.findAllByCompanyAndActiveIsTrue(company,pageable);
    }
}
