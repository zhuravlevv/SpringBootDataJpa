package com.lessons.controller;

import com.lessons.dto.EmployeeDto;
import com.lessons.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class EmployeeController {

    private final EmployeeService employeeService;

    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping("employee")
    public ResponseEntity<List<EmployeeDto>> getAll(){
        LOGGER.trace("getAll()");
        List<EmployeeDto> employees = employeeService.getAll();
        if(employees == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("employee/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Integer id){
        LOGGER.trace("getById({})", id);
        if(id == null)
            return ResponseEntity.badRequest().body(null);
        EmployeeDto employee = employeeService.getById(id);
        if(employee == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(employee);
    }

    @PostMapping("employee")
    public ResponseEntity<EmployeeDto> add(@RequestBody EmployeeDto employee){
        LOGGER.trace("add({})", employee);
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
        LOGGER.trace("update({}, {})", id, newEmployee);
        if(id == null || newEmployee == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(employeeService.update(newEmployee, id));
    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity<EmployeeDto> delete(@PathVariable Integer id){
        LOGGER.trace("delete({})", id);
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("employee/delete/{departmentId}")
    public ResponseEntity<EmployeeDto> deleteByDepartmentId(@PathVariable Integer departmentId) {
        LOGGER.trace("deleteByDepartmentId({})", departmentId);
        employeeService.deleteByDepartmentId(departmentId);
        return ResponseEntity.noContent().build();
    }
}
