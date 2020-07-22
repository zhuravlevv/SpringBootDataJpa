package com.lessons.service;

import com.lessons.dao.DepartmentDao;
import com.lessons.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    public List<DepartmentDto> getAll();

    public DepartmentDto getById(Integer id);

    public DepartmentDto save(DepartmentDto departmentDto);

    public DepartmentDto update(DepartmentDto newDepartment, Integer id);

    public void delete(Integer id);
}
