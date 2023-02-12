package com.fiserv.employeeservice.mapper;

import com.fiserv.employeeservice.dto.EmployeeDto;
import com.fiserv.employeeservice.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IAutoEmployeeMapper {
    IAutoEmployeeMapper MAPPER = Mappers.getMapper(IAutoEmployeeMapper.class);

    EmployeeDto mapToEmployeeDto(Employee employee);
    Employee mapToEmployee(EmployeeDto employeeDto);
}
