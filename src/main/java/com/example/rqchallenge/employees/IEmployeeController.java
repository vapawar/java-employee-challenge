package com.example.rqchallenge.employees;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.rqchallenge.dto.Employee;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/v1")
public interface IEmployeeController {

	@GetMapping
	ResponseEntity<List<Employee>> getAllEmployees() throws IOException;

	@GetMapping("/search/{searchString}")
	ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString);

	@GetMapping("/{id}")
	ResponseEntity<Employee> getEmployeeById(@PathVariable("id") String id);

	@GetMapping("/highestSalary")
	ResponseEntity<Integer> getHighestSalaryOfEmployees();

	@GetMapping("/topTenHighestEarningEmployeeNames")
	ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames();

	@PostMapping
	ResponseEntity<Employee> createEmployee(@RequestBody Map<String, Object> employeeInput);

	@DeleteMapping("/{id}")
	ResponseEntity<String> deleteEmployeeById(@PathVariable String id);

}
