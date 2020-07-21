package com.lessons.dao;

import com.lessons.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentDao extends JpaRepository<Department, Integer> {
}
