package com.example.tarea2.controller;

import com.example.tarea2.repository.DepartmentRepository;
import com.example.tarea2.repository.JobHistoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {


    final JobHistoryRepository jobHistoryRepository;
    final DepartmentRepository departmentRepository;

    public SearchController(JobHistoryRepository jobHistoryRepository, DepartmentRepository departmentRepository) {
        this.jobHistoryRepository = jobHistoryRepository;
        this.departmentRepository = departmentRepository;
    }

    //Cargar indice de reportes
    @GetMapping(value = "/search")
    public String searchIndex() {
        return "search/searchIndex";
    }

    //Cargar reporte de empleados que tienen un salario mayor a 15000

    @GetMapping(value = "/search/salary")
    public String salaryReport(Model model) {

        model.addAttribute("reportes", jobHistoryRepository.obtenerSalaryReport());
        return "search/salaryAbove";
    }

    //Cargar reporte de cantidad de departamentos con mas de 3 empleados por ciudad

    @GetMapping(value = "/search/department")
    public String departmentReport(Model model) {

        model.addAttribute("reportes", departmentRepository.getDepartmentReport());
        return "search/departmentAbove";
    }

    //Cargar reporte de gerentes con mas de 5 años de experiencia

    @GetMapping(value = "/search/experience")
    public String experienceReport(Model model) {

        model.addAttribute("reportes", departmentRepository.getExperienceReport());
        return "search/experienceAbove";
    }

}
