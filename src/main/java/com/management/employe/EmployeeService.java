package com.management.employe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired private EmployeeRepository repo;

    public List<Employee> getEmployees(String salarySort, String name, String salaryQuery, String dateQuery) {
        List<Order> orders = new ArrayList<>();
        Direction salarySortDirection = Direction.ASC;

        if (salarySort.equals("DESC")) {
            salarySortDirection = Direction.DESC;
        }

        orders.add(new Order(salarySortDirection, "salary"));


        if (!name.isEmpty() && !salaryQuery.isEmpty() && dateQuery.isEmpty()) {
            float salary = Float.parseFloat(salaryQuery);
            return repo.getEmployees(Sort.by(orders), name, salary);
        } else if (!dateQuery.isEmpty() && !name.isEmpty() && salaryQuery.isEmpty()) {
            LocalDate date = LocalDate.parse(dateQuery);
            return repo.getEmployees(Sort.by(orders), name, date);
        } else if (!dateQuery.isEmpty() && !salaryQuery.isEmpty()) {
            float salary = Float.parseFloat(salaryQuery);
            LocalDate date = LocalDate.parse(dateQuery);
            return repo.getEmployees(Sort.by(orders), salary, date);
        } else if (!name.isEmpty()) {
            return repo.getEmployees(Sort.by(orders), name);
        } else if (!salaryQuery.isEmpty()) {
            float salary = Float.parseFloat(salaryQuery);
            return repo.getEmployees(Sort.by(orders), salary);
        } else if (!dateQuery.isEmpty()) {
            LocalDate date = LocalDate.parse(dateQuery);
            return repo.getEmployees(Sort.by(orders), date);
        }

        return repo.getEmployees(Sort.by(orders));
    }

    public void createEmployee(Employee employee) throws Exception {
        try {
            repo.save(employee);
        } catch (Exception exc) {
            throw new Exception("error trying to create new employee: " + exc.getMessage());
        }
    }

    public Employee getEmployeeById(int employeeID) throws Exception {
        Optional<Employee> employee = repo.findById(employeeID);
        if (employee.isPresent()) {
            return employee.get();
        }

        throw new Exception("employee with id " + employeeID + " not found");
    }

    public void removeEmployee(int employeeID) {
        repo.deleteById(employeeID);
    }

    public void updateEmployee(Employee employee) {
        repo.save(employee);
    }
}
