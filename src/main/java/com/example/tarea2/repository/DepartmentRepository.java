package com.example.tarea2.repository;

import com.example.tarea2.DTO.AboveDepartmentDTO;
import com.example.tarea2.DTO.AboveExperienceDTO;
import com.example.tarea2.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {


    @Query(value = "SELECT c.country_name AS pais, l.city AS ciudad, COUNT(DISTINCT d.department_id) AS cantidad\n" +
            "FROM departments d " +
            "JOIN locations l ON d.location_id = l.location_id " +
            "JOIN countries c ON l.country_id = c.country_id " +
            "WHERE d.department_id IN " +
            " ( SELECT department_id " +
            "   FROM employees " +
            "   GROUP BY department_id " +
            "   HAVING COUNT(employee_id) > 3 )" +
            "GROUP BY c.country_name, l.city", nativeQuery = true)
    List<AboveDepartmentDTO> getDepartmentReport();


    @Query(value = "SELECT d.department_name AS departamento, \n" +
            "e.first_name AS nombre, " +
            "e.last_name AS apellido," +
            "e.salary AS sueldo " +
            "FROM departments d " +
            "JOIN employees e ON d.manager_id = e.employee_id " +
            "JOIN job_history jh ON e.employee_id = jh.employee_id " +
            "WHERE TIMESTAMPDIFF(YEAR, e.hire_date, jh.end_date) > 5", nativeQuery = true)
    List<AboveExperienceDTO> getExperienceReport();



}