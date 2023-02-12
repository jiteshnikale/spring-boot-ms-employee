package com.fiserv.employeeservice.service;

import com.fiserv.employeeservice.dto.EmployeeDepartmentDto;
import com.fiserv.employeeservice.dto.EmployeeDto;

public interface IEmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    EmployeeDto getEmployeeById(Long id);

    EmployeeDepartmentDto getEmployeeByIdWithDepartment(Long id);
}
