package com.fiserv.employeeservice.service;

import com.fiserv.employeeservice.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url="http://localhost:8080", value = "DEPARTMENT-SERVICE")
public interface APIClient {

    @GetMapping("/api/departments/code/{department-code}")
    DepartmentDto getDepartmentByCode(@PathVariable("department-code") String departmentCode);
}
