package com.lessons.service.impl;

import com.lessons.dao.DepartmentDao;
import com.lessons.dto.DepartmentDto;
import com.lessons.entity.Department;
import com.lessons.service.DepartmentService;
import com.lessons.service.mapper.DepartmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDao departmentDao;

    private final DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentDto> getAll() {
        return departmentDao.findAll()
                .stream()
                .map(departmentMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getById(Integer id) {
        try {
            return departmentMapper.fromEntity(departmentDao
                    .findById(id)
                    .orElseThrow(Exception::new));
        }
        catch (Exception e){
            System.out.println("Department with id = " + id + " doesn't exist.");
        }
        return null;
    }

    @Override
    public DepartmentDto save(DepartmentDto departmentDto) {
        return departmentMapper.fromEntity(departmentDao.save(departmentMapper.toEntity(departmentDto)));
    }

    @Override
    public DepartmentDto update(DepartmentDto newDepartment, Integer id) {
        try {
            Department department = departmentDao.findById(id).orElseThrow(Exception::new);
            department.setName(newDepartment.getName());
            departmentDao.save(department);
            return departmentMapper.fromEntity(department);
        }
        catch (Exception e){
            System.out.println("Department with id = " + id + " doesn't exist.");
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        departmentDao.deleteById(id);
    }
}
