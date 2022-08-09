package com.example.rqchallenge.employees;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.rqchallenge.dto.Employee;
import com.example.rqchallenge.service.EmployeeService;

@RestController
public class IEmployeeControllerImpl implements IEmployeeController {

	@Autowired
	EmployeeService employeeService;

	@Override
	public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
		return employeeService.getAllEmployees();
	}

	@Override
	public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
		return employeeService.getEmployeesByNameSearch(searchString);
	}

	@Override
	public ResponseEntity<Employee> getEmployeeById(String id) {
		return employeeService.getEmployeeById(id);
	}

	@Override
	public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
		return employeeService.getHighestSalaryOfEmployees();
	}

	@Override
	public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
		return employeeService.getTopTenHighestEarningEmployeeNames();
	}

	@Override
	public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
		return employeeService.createEmployee(employeeInput);
	}

	@Override
	public ResponseEntity<String> deleteEmployeeById(String id) {
		return employeeService.deleteEmployeeById(id);
	}
}

@ControllerAdvice
@ResponseBody
class ErrorResponseHandler {
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleException(final Exception exception, final HttpServletRequest request) {
		ResponseEntity<String> reponse = new ResponseEntity<String>(
				"Error while processing your request, please try after sometime.\n"
						+ "You can also check payload for correctness and send request again.",
				HttpStatus.INTERNAL_SERVER_ERROR);
		return reponse;
	}
}