package com.example.tarea2.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "job_history")
public class JobHistory {

    @EmbeddedId
    private JobHistoryId id;

    @MapsId("employeeId")
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    //start_date ya esta mapeando en la llave embebida

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
