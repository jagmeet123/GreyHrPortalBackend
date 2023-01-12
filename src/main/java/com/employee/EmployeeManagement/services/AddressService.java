package com.employee.EmployeeManagement.services;

import com.employee.EmployeeManagement.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {


    @Autowired
    private AddressRepository addressRepository;

    public void deleteAllAddress(List<Long> ids){
        addressRepository.deleteById(ids.get(0));
    }
}
