package com.armelkragba.springboot.service;

import com.armelkragba.springboot.exception.RessourceNotFoundException;
import com.armelkragba.springboot.model.Employee;
import com.armelkragba.springboot.repository.EmployeeRepository;
import com.armelkragba.springboot.service.imp.EmployeeServiceImp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImp employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp(){
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImp(employeeRepository);

         employee = Employee.builder()
                .id(1L)
                .firstName("Elohim")
                .lastName("Adonai")
                .email("adonai@gmail.com")
                .build();
    }

    //JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){

        //given - precondition or setup
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        BDDMockito.given(employeeRepository.save(employee))
                .willReturn(employee);

        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);


        //then - verify the output
        Assertions.assertThat(savedEmployee).isNotNull();
    }

    //JUnit test for saveEmployee method which throws exception
    @DisplayName("JUnit test for saveEmployee method which throws exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenReturnThrowsException(){

        //given - precondition or setup
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));


        //when - action or the behaviour that we are going to test
        org.junit.jupiter.api.Assertions.assertThrows(RessourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });


        //then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
    }
}
