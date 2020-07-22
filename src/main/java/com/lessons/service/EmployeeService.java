package com.lessons.service;

import com.lessons.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    public List<EmployeeDto> getAll();

    public EmployeeDto getById(Integer id);

    public EmployeeDto save(EmployeeDto employeeDto);

    public EmployeeDto update(EmployeeDto newEmployee, Integer id);

    public void delete(Integer id);
}
