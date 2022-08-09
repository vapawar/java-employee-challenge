package com.example.rqchallenge.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.rqchallenge.dto.Employee;
import com.example.rqchallenge.service.EmployeeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	final static Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	OkHttpClient client;
	@Autowired
	ObjectMapper mapper;

	final String URL = "https://dummy.restapiexample.com/api/v1";
	public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

	@Override
	public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
		Employee employee = null;
		RequestBody body = RequestBody.create(employeeInput.toString(), JSON);
		Request request = new Request.Builder().url(URL + "/create").post(body).build();
		try (Response response = client.newCall(request).execute()) {
			if (response.code() == HttpStatus.OK.value()) {
				long id = mapper.readTree(response.body().string()).get("data").get("id").asLong();
				employee = mapper.convertValue(employeeInput, Employee.class);
				employee.setId(id);
			}
		} catch (Exception e) {
			logger.error("Error while saving details for employee: {}", employeeInput.get("employee_name"), e);
		}
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Employee> getEmployeeById(String id) {
		Employee employee = null;
		Request request = new Request.Builder().url(URL + "/employee/" + id).build();
		try (Response response = client.newCall(request).execute()) {
			employee = mapper.treeToValue(mapper.readTree(response.body().string()).get("data"), Employee.class);
		} catch (Exception e) {
			logger.error("Error while retriving employee details for id:{}", id, e);
		}
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteEmployeeById(String id) {
		String deleteRespose = null;
		Request request = new Request.Builder().url(URL + "/delete/" + id).delete().build();
		try (Response response = client.newCall(request).execute()) {
			deleteRespose = response.body().string();
		} catch (Exception e) {
			logger.error("Error while deleting employee details with id:{}", id, e);
		}
		return new ResponseEntity<String>(deleteRespose, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Employee>> getAllEmployees() {
		Request request = new Request.Builder().url(URL + "/employees").build();
		List<Employee> employees = null;
		try (Response response = client.newCall(request).execute()) {
			String employeeData = mapper.readTree(response.body().string()).get("data").toPrettyString();
			employees = mapper.readValue(employeeData, new TypeReference<List<Employee>>() {
			});
		} catch (Exception e) {
			logger.error("Error while retriving all employee details", e);
		}
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
		List<Employee> employees = null;
		Request request = new Request.Builder().url(URL + "/employees").build();
		try (Response response = client.newCall(request).execute()) {
			String employeeData = mapper.readTree(response.body().string()).get("data").toPrettyString();
			employees = mapper.readValue(employeeData, new TypeReference<List<Employee>>() {
			});
			employees = employees.stream().filter(x -> x.getEmployee_name().contains(searchString))
					.collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Error while retriving employee names matching the search string:{}", searchString, e);
		}
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
		int highestSalary = 0;
		Request request = new Request.Builder().url(URL + "/employees").build();
		try (Response response = client.newCall(request).execute()) {
			String employeeData = mapper.readTree(response.body().string()).get("data").toPrettyString();
			List<Employee> employees = mapper.readValue(employeeData, new TypeReference<List<Employee>>() {
			});
			highestSalary = employees.stream().map(x -> x.getEmployee_salary())
					.max(Comparator.comparing(Float::valueOf)).get();
		} catch (Exception e) {
			logger.error("Error while retriving highest salary", e);
		}
		return new ResponseEntity<>(highestSalary, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
		List<String> employeeResponse = null;
		Request request = new Request.Builder().url(URL + "/employees").build();
		try (Response response = client.newCall(request).execute()) {
			String employeeData = mapper.readTree(response.body().string()).get("data").toPrettyString();
			List<Employee> employees = mapper.readValue(employeeData, new TypeReference<List<Employee>>() {
			});
			employees.sort(Comparator.comparing(Employee::getEmployee_salary).reversed());
			employeeResponse = employees.stream().map(x -> x.getEmployee_name()).limit(10).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Error while retriving ten highest earning employees", e);
		}
		return new ResponseEntity<List<String>>(employeeResponse, HttpStatus.OK);
	}

}
