package com.example.tarea2.repository;
import com.example.tarea2.DTO.AboveSalaryDTO;
import com.example.tarea2.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobHistoryRepository extends JpaRepository<JobHistory, JobHistoryId> {

    @Query("SELECT jh FROM JobHistory jh WHERE " +
            "LOWER(jh.employee.firstName) LIKE LOWER(CONCAT('%', :searchField, '%')) OR " +
            "LOWER(jh.employee.lastName) LIKE LOWER(CONCAT('%', :searchField, '%')) OR " +
            "LOWER(jh.job.jobTitle) LIKE LOWER(CONCAT('%', :searchField, '%')) OR " +
            "LOWER(jh.department.departmentName) LIKE LOWER(CONCAT('%', :searchField, '%'))")
    List<JobHistory> searchJobHistoryMultipleParameters(@Param("searchField") String searchField);

    @Query(value = "SELECT e.first_name AS nombre, " +
            "e.last_name AS apellido, " +
            "jh.start_date AS fechaInicio, " +
            "jh.end_date AS fechaFin, " +
            "j.job_title AS nombrePuesto " +
            "FROM job_history jh " +
            "JOIN employees e ON jh.employee_id = e.employee_id " +
            "JOIN jobs j ON jh.job_id = j.job_id\n" +
            "WHERE e.salary > 15000 ", nativeQuery = true)
    List<AboveSalaryDTO> obtenerSalaryReport();

}
