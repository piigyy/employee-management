package com.management.employe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired private EmployeeService service;

    @PostMapping("/employees")
    public String createNewEmployee(Employee employee, RedirectAttributes re) {
        try {
            service.createEmployee(employee);
            re.addFlashAttribute("message", "success save employee '" + employee.getName() + "'");
            return "redirect:/employees";
        } catch (Exception exc) {
            re.addFlashAttribute("error", exc.getMessage());
            return "redirect:/employees";
        }
    }

    @GetMapping("/employees")
    public String getEmployees(
            @RequestParam(required = false, name = "sort-salary", defaultValue = "ASC") String sortSalary,
            @RequestParam(required = false, name = "search-name", defaultValue = "") String searchName,
            @RequestParam(required = false, name = "search-salary", defaultValue = "") String searchSalary,
            @RequestParam(required = false, name = "search-date", defaultValue = "") String searchDate,
            Model model
    ) {
        Employee employee = new Employee();
        List<Employee> employees = service.getEmployees(sortSalary, searchName, searchSalary, searchDate);

        model.addAttribute("employees", employees);
        model.addAttribute("employeeObject", employee);
        model.addAttribute("sortSalary", sortSalary);
        model.addAttribute("searchName", searchName);
        model.addAttribute("searchSalary", searchSalary);
        model.addAttribute("searchDate", searchDate);
        return "employees";
    }

    @PutMapping("employees/{id}")
    public String updateEmployee(@PathVariable("id") int employeeID, Employee employee, RedirectAttributes re) {
        try {
            employee.setId(employeeID);
            service.updateEmployee(employee);
            re.addFlashAttribute("message", "success update employee " + employeeID);
            return "redirect:/employees";
        } catch (Exception exc) {
            re.addFlashAttribute("error", exc.getMessage());
            return "redirect:/employees";
        }
    }

    @DeleteMapping("/employees/{id}")
    public String removeEmployee(@PathVariable("id") int employeeID, RedirectAttributes re) {
        try {
            Employee employee = service.getEmployeeById(employeeID);
            service.removeEmployee(employeeID);
            re.addFlashAttribute("message", "success delete employee " + employee.getName() + " (Employee ID: " + employee.getId() + ")");
            return "redirect:/employees";
        } catch (Exception exc) {
            re.addFlashAttribute("error", exc.getMessage());
            return "redirect:/employees";
        }
    }

}
