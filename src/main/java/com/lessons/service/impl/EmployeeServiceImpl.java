package com.lessons.service.impl;

import com.lessons.dao.DepartmentDao;
import com.lessons.dao.EmployeeDao;
import com.lessons.dto.EmployeeDto;
import com.lessons.entity.Department;
import com.lessons.entity.Employee;
import com.lessons.service.EmployeeService;
import com.lessons.service.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    private final DepartmentDao departmentDao;

    private final EmployeeMapper employeeMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override
    public List<EmployeeDto> getAll() {
        LOGGER.trace("getAll()");
        return employeeDao.findAll()
                .stream()
                .map(employeeMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getById(Integer id) {
        LOGGER.trace("getById({})", id);
        try {
            return employeeMapper.fromEntity(employeeDao
                    .findById(id)
                    .orElseThrow(Exception::new));
        }
        catch (Exception e){
            LOGGER.error("Employee with id = {} doesn't exist.", id);
        }
        return null;
    }

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        LOGGER.trace("save({})", employeeDto);
        return employeeMapper.fromEntity(employeeDao
                .save(employeeMapper
                        .toEntity(employeeDto)));
    }

    @Override
    public EmployeeDto update(EmployeeDto newEmployee, Integer id) {
        LOGGER.trace("update({}, {})", newEmployee, id);
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
            LOGGER.error("Employee with id = {} doesn't exist.", id);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        LOGGER.trace("delete({})", id);
        employeeDao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByDepartmentId(Integer departmentId) {
        LOGGER.trace("deleteByDepartmentId({})", departmentId);
        try {
            Department department = departmentDao.findById(departmentId).orElseThrow(Exception::new);
            employeeDao.deleteAllByDepartment(department);
        } catch (Exception e){
            LOGGER.error("Department with id = {} doesn't exist.", departmentId);
        }
    }
}
