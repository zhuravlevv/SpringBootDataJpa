package com.lessons.controller;

import com.lessons.dto.DepartmentDto;
import com.lessons.service.DepartmentService;
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
public class DepartmentController {

    private final DepartmentService departmentService;

    private final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @GetMapping("department")
    public ResponseEntity<List<DepartmentDto>> getAll(){
        LOGGER.trace("getAll()");
        List<DepartmentDto> departments = departmentService.getAll();
        if(departments == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("department/{id}")
    public ResponseEntity<DepartmentDto> getById(@PathVariable Integer id){
        LOGGER.trace("getById({})", id);
        if(id == null)
            return ResponseEntity.badRequest().body(null);
        DepartmentDto department = departmentService.getById(id);
        if(department == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(department);
    }

    @PostMapping("department")
    public ResponseEntity<DepartmentDto> add(@RequestBody DepartmentDto department){
        LOGGER.trace("add({})", department);
        if(department == null)
            return ResponseEntity.badRequest().body(null);
        DepartmentDto createdDepartment = departmentService.save(department);
        return ResponseEntity.created(URI
                .create("department/" + createdDepartment
                        .getId()))
                .body(createdDepartment);
    }

    @PutMapping("department/{id}")
    public ResponseEntity<DepartmentDto> update(@PathVariable Integer id, @RequestBody DepartmentDto newDepartment){
        LOGGER.trace("update({}, {})", id, newDepartment);
        if(id == null || newDepartment == null)
            return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok(departmentService.update(newDepartment, id));
    }

    @DeleteMapping("department/{id}")
    public ResponseEntity<DepartmentDto> delete(@PathVariable Integer id){
        LOGGER.trace("delete({})", id);
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
