package com.lessons.service;

import com.lessons.dto.DepartmentDto;
import com.lessons.dto.EmployeeDto;
import com.lessons.service.impl.DepartmentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class DepartmentServiceImplTest {

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void getAll(){
        List<DepartmentDto> departments = departmentService.getAll();

        assertNotNull(departments);
    }

    @Test
    public void save(){
        DepartmentDto newDepartment = new DepartmentDto(124, "departmentServiceSave", new BigDecimal("0"));

        DepartmentDto createdDepartment = departmentService.save(newDepartment);

        assertNotNull(createdDepartment.getId());
        assertEquals(newDepartment.getName(), createdDepartment.getName());

    }

    @Test
    public void getById(){

        DepartmentDto newDepartment = new DepartmentDto(124, "departmentServiceGetById", new BigDecimal("0"));
        DepartmentDto createdDepartment = departmentService.save(newDepartment);

        BigDecimal bigDecimal1 = new BigDecimal("200");
        EmployeeDto employee1 = new EmployeeDto(null, "employee1", createdDepartment.getId(), bigDecimal1);
        BigDecimal bigDecimal2 = new BigDecimal("300");
        EmployeeDto employee2 = new EmployeeDto(null, "employee2", createdDepartment.getId(), bigDecimal2);
        employeeService.save(employee1);
        employeeService.save(employee2);

        DepartmentDto returnedDepartment = departmentService.getById(createdDepartment.getId());

        assertEquals(new BigDecimal("250.00000"), returnedDepartment.getAverageSalary());
        assertEquals(newDepartment.getName(), returnedDepartment.getName());
    }

    @Test
    public void update(){
        DepartmentDto department = new DepartmentDto(124, "departmentServiceUpdate", new BigDecimal("0"));
        DepartmentDto createdDepartment = departmentService.save(department);
        DepartmentDto newDepartment = new DepartmentDto(124, "departmentServiceGetByIdNew", new BigDecimal("0"));
        DepartmentDto updatedDepartment = departmentService.update(newDepartment, createdDepartment.getId());

        assertEquals(newDepartment.getName(), updatedDepartment.getName());
    }

    @Test
    public void delete(){
        DepartmentDto department = new DepartmentDto(124, "departmentServiceDelete", new BigDecimal("0"));
        DepartmentDto createdDepartment = departmentService.save(department);

        departmentService.delete(createdDepartment.getId());
        DepartmentDto returnedDepartment = departmentService.getById(createdDepartment.getId());

        assertNull(returnedDepartment);
    }
}
