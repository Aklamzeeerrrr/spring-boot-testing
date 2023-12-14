package com.armelkragba.springboot.service.imp;

import com.armelkragba.springboot.model.Employee;
import com.armelkragba.springboot.repository.EmployeeRepository;
import com.armelkragba.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.Optional;

@Service
public class EmployeeServiceImp implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {

        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());

        if(savedEmployee.isPresent()){
            throw new ResolutionException("Employee already exist with given email :" + employee.getEmail());
        }

        return employeeRepository.save(employee);
    }
}
