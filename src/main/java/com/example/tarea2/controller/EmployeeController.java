package com.example.tarea2.controller;

import com.example.tarea2.entity.*;
import com.example.tarea2.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    final EmployeeRepository employeeRepository;
    final JobRepository jobRepository;
    final DepartmentRepository departmentRepository;

    public EmployeeController(EmployeeRepository employeeRepository,
                              JobRepository jobRepository,
                              DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.jobRepository = jobRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping(value={"","/","/list"})
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employee/list";
    }

    @GetMapping("/new")
    public String newEmployeeForm(Model model) {
        model.addAttribute("jobs", jobRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("managers", employeeRepository.findAll());
        return "employee/newForm";
    }

    @PostMapping("/save")
    public String saveEmployee(Employee employee, RedirectAttributes attr) {
        if (employee.getHireDate() == null) {
            employee.setHireDate(LocalDateTime.now());
        }
        employeeRepository.save(employee);
        attr.addFlashAttribute("success", "Empleado guardado exitosamente");
        return "redirect:/employee/list";
    }

    @GetMapping("/edit")
    public String editEmployeeForm(@RequestParam("id") int id, Model model) {
        Optional<Employee> optEmployee = employeeRepository.findById(id);

        if (optEmployee.isPresent()) {
            Employee employee = optEmployee.get();
            model.addAttribute("employee", employee);
            model.addAttribute("jobs", jobRepository.findAll());
            model.addAttribute("departments", departmentRepository.findAll());
            model.addAttribute("managers", employeeRepository.findAll());
            return "employee/editForm";
        } else {
            return "redirect:/employee/list";
        }
    }


    //Esta función solo permite eliminar empleados que no son manager de otros empleados
    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        Optional<Employee> optEmployee = employeeRepository.findById(id);

        if (optEmployee.isPresent()) {
            employeeRepository.deleteById(id);
        }
        redirectAttributes.addFlashAttribute("success", "Empleado eliminado exitosamente");
        return "redirect:/employee/list";
    }

    @PostMapping("/BuscarEmpleados")
    public String searchEmployees(@RequestParam("searchField") String searchTerm, Model model) {

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return "redirect:/employee/list";
        }
        List<Employee> employees = employeeRepository.searchEmployees(searchTerm.trim());
        model.addAttribute("employees", employees);
        return "employee/list";
    }
}