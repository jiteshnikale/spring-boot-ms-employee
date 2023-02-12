package com.fiserv.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDepartmentDto {
    private EmployeeDto employeeDto;
    private DepartmentDto departmentDto;
}
