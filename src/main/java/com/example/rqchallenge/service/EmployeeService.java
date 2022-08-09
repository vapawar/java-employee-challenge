package com.example.rqchallenge.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.example.rqchallenge.dto.Employee;

public interface EmployeeService {

	ResponseEntity<List<Employee>> getAllEmployees();

	ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString);

	ResponseEntity<Employee> getEmployeeById(String id);

	ResponseEntity<Integer> getHighestSalaryOfEmployees();

	ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames();

	ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput);

	ResponseEntity<String> deleteEmployeeById(String id);
}
