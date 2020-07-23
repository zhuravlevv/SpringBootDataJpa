package com.lessons.service.mapper;

import com.lessons.dto.DepartmentDto;
import com.lessons.entity.Department;
import com.lessons.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        DepartmentDto departmentDto = DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
        if(department.getEmployees() != null) {
            if (department.getEmployees().size() != 0) {
                int size = department.getEmployees().size();
                System.out.println("Size = " + size);
                departmentDto.setAverageSalary(department
                        .getEmployees()
                        .stream()
                        .map(Employee::getSalary)
                        .reduce(BigDecimal::add)
                        .orElseGet(() -> new BigDecimal("0"))
                .divide(new BigDecimal(size), 10, RoundingMode.CEILING));
            }
        }
        return departmentDto;
    }
}
