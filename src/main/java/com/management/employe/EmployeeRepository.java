package com.management.employe;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(value = "SELECT e FROM Employee e", nativeQuery = false)
    public List<Employee> getEmployees(Sort sort);

    @Query(value = "SELECT e FROM Employee e WHERE LOWER(e.name) LIKE %:name%", nativeQuery = false)
    public List<Employee> getEmployees(
            Sort sort,
            @Param("name") String name
    );

    @Query(value = "SELECT e FROM Employee e WHERE e.salary = :salary", nativeQuery = false)
    public List<Employee> getEmployees(
            Sort sort,
            @Param("salary") float salary
    );

    @Query(value = "SELECT e FROM Employee e WHERE e.date = :date", nativeQuery = false)
    public List<Employee> getEmployees(
            Sort sort,
            @Param("date") LocalDate date
    );

    @Query(value = "SELECT e FROM Employee e WHERE LOWER(e.name) LIKE %:name% AND e.salary = :salary", nativeQuery = false)
    public List<Employee> getEmployees(
            Sort sort,
            @Param("name") String name,
            @Param("salary") float salary
    );

    @Query(value = "SELECT e FROM Employee e WHERE LOWER(e.name) LIKE %:name% AND e.date = :date", nativeQuery = false)
    public List<Employee> getEmployees(
            Sort sort,
            @Param("name") String name,
            @Param("date") LocalDate date
    );

    @Query(value = "SELECT e FROM Employee e WHERE e.salary = :salary AND e.date = :date", nativeQuery = false)
    public List<Employee> getEmployees(
            Sort sort,
            @Param("salary") float salary,
            @Param("date") LocalDate date
    );

    @Query(value = "SELECT e FROM Employee e WHERE LOWER(e.name) LIKE %:name% AND e.salary = :salary AND e.date = :date", nativeQuery = false)
    public List<Employee> getEmployees(
            Sort sort,
            @Param("name") String name,
            @Param("salary") float salary,
            @Param("date") LocalDate date
    );
}
