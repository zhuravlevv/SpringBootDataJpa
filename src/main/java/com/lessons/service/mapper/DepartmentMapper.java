package com.lessons.service.mapper;

import com.lessons.dto.DepartmentDto;
import com.lessons.entity.Department;
import com.lessons.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DepartmentMapper implements Mapper<Department, DepartmentDto> {

    @Override
    public Department toEntity(DepartmentDto departmentDto) {
        return Department.builder()
                .name(departmentDto.getName())
                .build();
    }

    @Override
    public DepartmentDto fromEntity(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .averageSalary(department
                        .getEmployees()
                        .stream()
                        .map(Employee::getSalary)
                        .reduce(BigDecimal::add)
                        .orElseGet(()->new BigDecimal("0")))
                .build();
    }
}
