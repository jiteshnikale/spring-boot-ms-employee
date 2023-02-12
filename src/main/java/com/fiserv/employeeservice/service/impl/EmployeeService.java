package com.fiserv.employeeservice.service.impl;

import com.fiserv.employeeservice.dto.DepartmentDto;
import com.fiserv.employeeservice.dto.EmployeeDepartmentDto;
import com.fiserv.employeeservice.dto.EmployeeDto;
import com.fiserv.employeeservice.entity.Employee;
import com.fiserv.employeeservice.exception.EmailAlreadyExistsException;
import com.fiserv.employeeservice.exception.ResourceNotFoundException;
import com.fiserv.employeeservice.mapper.IAutoEmployeeMapper;
import com.fiserv.employeeservice.repository.IEmployeeRepository;
import com.fiserv.employeeservice.service.IEmployeeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService implements IEmployeeService {
    private IEmployeeRepository employeeRepository;
    private RestTemplate restTemplate;
    private ModelMapper modelMapper;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Optional<Employee> existingEmployee = employeeRepository.findByEmail(employeeDto.getEmail());

        if (existingEmployee.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        //convert EmployeeDto into Employee JPA entity
        //Employee employee = IAutoEmployeeMapper.MAPPER.mapToEmployee(employeeDto);
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        /*employee.setDepartmentCode(employeeDto.getDepartmentCode());*/

        Employee savedEmployee = employeeRepository.save(employee);

        //return IAutoEmployeeMapper.MAPPER.mapToEmployeeDto(savedEmployee);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Employee not found for the user id: %d", id))
        );
        return IAutoEmployeeMapper.MAPPER.mapToEmployeeDto(employee);
    }

    @Override
    public EmployeeDepartmentDto getEmployeeByIdWithDepartment(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Employee not found for the user id: %d", id))
        );

        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity
                ("http://localhost:8080/api/departments/code/" + employee.getDepartmentCode(), DepartmentDto.class);

        DepartmentDto departmentDto = responseEntity.getBody();
        EmployeeDto employeeDto = IAutoEmployeeMapper.MAPPER.mapToEmployeeDto(employee);

        return new EmployeeDepartmentDto(employeeDto, departmentDto);
    }
}
