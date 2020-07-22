package com.lessons.service.impl;

import com.lessons.dao.DepartmentDao;
import com.lessons.dao.EmployeeDao;
import com.lessons.dto.DepartmentDto;
import com.lessons.dto.EmployeeDto;
import com.lessons.entity.Employee;
import com.lessons.service.EmployeeService;
import com.lessons.service.mapper.EmployeeMapper;
import com.sun.prism.es2.ES2Graphics;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    private final DepartmentDao departmentDao;

    private final EmployeeMapper employeeMapper;


    @Override
    public List<EmployeeDto> getAll() {
        return employeeDao.findAll()
                .stream()
                .map(employeeMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getById(Integer id) {
        try {
            return employeeMapper.fromEntity(employeeDao
                    .findById(id)
                    .orElseThrow(Exception::new));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        return employeeMapper.fromEntity(employeeDao
                .save(employeeMapper
                        .toEntity(employeeDto)));
    }

    @Override
    public EmployeeDto update(EmployeeDto newEmployee, Integer id) {
        try {
            Employee employee = employeeDao
                    .findById(id)
                    .orElseThrow(Exception::new);
            employee.setSalary(newEmployee.getSalary());
            employee.setName(newEmployee.getName());
            employee.setDepartment(departmentDao
                            .findById(newEmployee
                            .getDepartmentId())
                    .orElseThrow(Exception::new));
            return employeeMapper.fromEntity(employee);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        employeeDao.deleteById(id);
    }
}
