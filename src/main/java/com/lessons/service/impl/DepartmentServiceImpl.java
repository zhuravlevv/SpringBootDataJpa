package com.lessons.service.impl;

import com.lessons.dao.DepartmentDao;
import com.lessons.dto.DepartmentDto;
import com.lessons.entity.Department;
import com.lessons.service.DepartmentService;
import com.lessons.service.mapper.DepartmentMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDao departmentDao;

    private final DepartmentMapper departmentMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Override
    public List<DepartmentDto> getAll() {
        LOGGER.trace("getAll()");
        return departmentDao.findAll()
                .stream()
                .map(departmentMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getById(Integer id) {
        LOGGER.trace("getById({})", id);
        try {
            return departmentMapper.fromEntity(departmentDao
                    .findById(id)
                    .orElseThrow(Exception::new));
        }
        catch (Exception e){
            LOGGER.error("Department with id = {} doesn't exist.", id);
        }
        return null;
    }

    @Override
    public DepartmentDto save(DepartmentDto departmentDto) {
        LOGGER.trace("save({})", departmentDto);
        return departmentMapper.fromEntity(departmentDao.save(departmentMapper.toEntity(departmentDto)));
    }

    @Override
    public DepartmentDto update(DepartmentDto newDepartment, Integer id) {
        LOGGER.trace("update({}, {})", newDepartment, id);
        try {
            Department department = departmentDao.findById(id).orElseThrow(Exception::new);
            department.setName(newDepartment.getName());
            departmentDao.save(department);
            return departmentMapper.fromEntity(department);
        }
        catch (Exception e){
            LOGGER.error("Department with id = {} doesn't exist.", id);
            System.out.println();
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        LOGGER.trace("delete({})", id);
        departmentDao.deleteById(id);
    }
}
