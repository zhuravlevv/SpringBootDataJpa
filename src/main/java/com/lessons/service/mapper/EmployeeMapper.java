package com.lessons.service.mapper;

import com.lessons.dao.DepartmentDao;
import com.lessons.dto.EmployeeDto;
import com.lessons.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class EmployeeMapper implements Mapper<Employee, EmployeeDto> {

    private final DepartmentDao departmentDao;

    @Override
    public Employee toEntity(EmployeeDto employeeDto) {
        return Employee.builder()
                .department(departmentDao
                        .findById(employeeDto
                                .getDepartmentId())
                .orElse(null))
                .name(employeeDto.getName())
                .salary(employeeDto.getSalary())
                .build();
    }

    @Override
    public EmployeeDto fromEntity(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .departmentId(employee.getDepartment().getId())
                .salary(employee.getSalary())
                .build();
    }
}
