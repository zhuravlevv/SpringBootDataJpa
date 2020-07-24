package com.lessons.dao;

import com.lessons.entity.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class DepartmentDaoTest {

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    public void findAll(){
        List<Department> departments = departmentDao.findAll();

        assertNotNull(departments);
    }

    @Test
    public void findById(){
        Department newDepartment = new Department();
        newDepartment.setName("findByIdDepartmentName");
        Department department = departmentDao.save(newDepartment);

        try {
            Department foundedDepartment = departmentDao.findById(department.getId()).orElseThrow(Exception::new);
            assertEquals(newDepartment.getName(), foundedDepartment.getName());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
