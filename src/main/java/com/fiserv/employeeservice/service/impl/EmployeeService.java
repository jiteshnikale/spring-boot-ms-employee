package com.fiserv.employeeservice.service.impl;

import com.fiserv.employeeservice.dto.EmployeeDto;
import com.fiserv.employeeservice.entity.Employee;
import com.fiserv.employeeservice.exception.EmailAlreadyExistsException;
import com.fiserv.employeeservice.exception.ResourceNotFoundException;
import com.fiserv.employeeservice.mapper.IAutoEmployeeMapper;
import com.fiserv.employeeservice.repository.IEmployeeRepository;
import com.fiserv.employeeservice.service.IEmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService implements IEmployeeService {

    private IEmployeeRepository employeeRepository;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Optional<Employee> existingEmployee = employeeRepository.findByEmail(employeeDto.getEmail());

        if (existingEmployee.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        //convert EmployeeDto into Employee JPA entity
        Employee employee = IAutoEmployeeMapper.MAPPER.mapToEmployee(employeeDto);

        Employee savedEmployee = employeeRepository.save(employee);

        return IAutoEmployeeMapper.MAPPER.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Employee not found for the user id: %d", id))
        );

        return IAutoEmployeeMapper.MAPPER.mapToEmployeeDto(employee);
    }
}
