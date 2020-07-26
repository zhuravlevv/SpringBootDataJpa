package com.lessons.controller;

import com.lessons.dto.EmployeeDto;
import com.lessons.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("employee")
    public ResponseEntity<List<EmployeeDto>> getAll(){
        List<EmployeeDto> employees = employeeService.getAll();
        if(employees == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("employee/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Integer id){
        if(id == null)
            return ResponseEntity.badRequest().body(null);
        EmployeeDto employee = employeeService.getById(id);
        if(employee == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(employee);
    }

    @PostMapping("employee")
    public ResponseEntity<EmployeeDto> add(@RequestBody EmployeeDto employee){
        if(employee == null)
            return ResponseEntity.badRequest().body(null);
        EmployeeDto createdEmployee = employeeService.save(employee);
        return ResponseEntity.created(URI
                .create("employee/" + createdEmployee
                        .getId()))
                .body(createdEmployee);
    }

    @PutMapping("employee/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable Integer id, @RequestBody EmployeeDto newEmployee){
        if(id == null || newEmployee == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(employeeService.update(newEmployee, id));
    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity<EmployeeDto> delete(@PathVariable Integer id){
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("employee/delete/{departmentId}")
    public ResponseEntity<EmployeeDto> deleteByDepartmentId(@PathVariable Integer departmentId){
        employeeService.deleteByDepartmentId(departmentId);
        return ResponseEntity.noContent().build();
    }
}
