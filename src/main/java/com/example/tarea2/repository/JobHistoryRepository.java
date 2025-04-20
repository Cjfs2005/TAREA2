package com.example.tarea2.repository;
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

}
