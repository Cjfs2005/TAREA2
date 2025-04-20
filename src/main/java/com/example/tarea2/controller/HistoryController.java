package com.example.tarea2.controller;

import com.example.tarea2.entity.JobHistory;
import com.example.tarea2.repository.JobHistoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class HistoryController {

    final JobHistoryRepository jobHistoryRepository;

    public HistoryController(JobHistoryRepository jobHistoryRepository) {
        this.jobHistoryRepository = jobHistoryRepository;
    }

    //Listar todos los historiales de todos los empleados

    @GetMapping(value={"/history","/history/list"})
    public String showHistory(Model model) {
        model.addAttribute("historyList", jobHistoryRepository.findAll());
        return "history/historyList";
    }

    //Buscar un empleado por nombre, apellido, puesto o departamento

    @GetMapping(value="/history/BuscarHistoriales")
    public String showBuscarHistoriales(@RequestParam("searchField") String searchField, Model model) {

        if (searchField == null || searchField.trim().isEmpty()) {
            return "redirect:/history/list";
        }

        List<JobHistory> historyList = jobHistoryRepository.searchJobHistoryMultipleParameters(searchField.trim());
        model.addAttribute("historyList", historyList);
        return "history/historyList";

    }


}
