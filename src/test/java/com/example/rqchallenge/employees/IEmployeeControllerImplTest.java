package com.example.rqchallenge.employees;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.rqchallenge.dto.Employee;
import com.example.rqchallenge.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(IEmployeeController.class)
class IEmployeeControllerImplTest {

	final static Logger logger = LoggerFactory.getLogger(IEmployeeControllerImplTest.class);

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	EmployeeService employeeService;

	@Test
	void testGetAllEmployees() throws Exception {
		logger.info("testGetAllEmployees");

		Employee employee1 = new Employee();
		employee1.setId(1);
		employee1.setEmployee_name("tigor test");
		Employee employee2 = new Employee();
		employee2.setId(2);
		employee2.setEmployee_name("random user");
		ArrayList<Employee> emps = new ArrayList<>();
		emps.add(employee1);
		emps.add(employee2);
		ResponseEntity<List<Employee>> response = new ResponseEntity<List<Employee>>(emps, HttpStatus.OK);
		when(employeeService.getAllEmployees()).thenReturn(response);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1").accept(MediaType.APPLICATION_JSON))
				.andReturn();
		logger.info("Status:" + result.getResponse().getStatus() + "::" + result.getResponse().getContentAsString());
		assertNotNull(result.getResponse());
		assertEquals(200, result.getResponse().getStatus());
		assertNotNull(result.getResponse().getContentAsString());
	}

	@Test
	void testGetEmployeesByNameSearch() throws Exception {
		logger.info("testGetEmployeesByNameSearch");

		Employee employee1 = new Employee();
		employee1.setId(1);
		employee1.setEmployee_name("tigor test");
		Employee employee2 = new Employee();
		employee2.setId(2);
		employee2.setEmployee_name("random user");
		ArrayList<Employee> emps = new ArrayList<>();
		emps.add(employee1);
		emps.add(employee2);
		ResponseEntity<List<Employee>> response = new ResponseEntity<List<Employee>>(emps, HttpStatus.OK);
		when(employeeService.getEmployeesByNameSearch(Mockito.anyString())).thenReturn(response);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1/search/st").accept(MediaType.APPLICATION_JSON))
				.andReturn();
		logger.info("Status:" + result.getResponse().getStatus() + "::" + result.getResponse().getContentAsString());
		assertNotNull(result.getResponse());
		assertEquals(200, result.getResponse().getStatus());
		assertNotNull(result.getResponse().getContentAsString());
	}

	@Test
	void testGetEmployeeById() throws Exception {
		logger.info("testGetEmployeeById");

		Employee employee = new Employee();
		employee.setId(1);
		employee.setEmployee_name("tigor test");
		ResponseEntity<Employee> response = new ResponseEntity<Employee>(employee, HttpStatus.OK);
		when(employeeService.getEmployeeById(Mockito.anyString())).thenReturn(response);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/1").accept(MediaType.APPLICATION_JSON))
				.andReturn();
		logger.info("Status:" + result.getResponse().getStatus() + "::" + result.getResponse().getContentAsString());
		assertNotNull(result.getResponse());
		assertEquals(200, result.getResponse().getStatus());
		assertNotNull(result.getResponse().getContentAsString());
	}

	@Test
	void testGetHighestSalaryOfEmployees() throws Exception {
		logger.info("testGetHighestSalaryOfEmployees");

		ResponseEntity<Integer> response = new ResponseEntity<Integer>(750000, HttpStatus.OK);
		when(employeeService.getHighestSalaryOfEmployees()).thenReturn(response);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1/highestSalary").accept(MediaType.APPLICATION_JSON))
				.andReturn();
		logger.info("Status:" + result.getResponse().getStatus() + "::" + result.getResponse().getContentAsString());
		assertNotNull(result.getResponse());
		assertEquals(200, result.getResponse().getStatus());
		assertNotNull(result.getResponse().getContentAsString());
	}

	@Test
	void testGetTopTenHighestEarningEmployeeNames() throws Exception {
		logger.info("testGetTopTenHighestEarningEmployeeNames");

		ArrayList<String> emps = new ArrayList<>();
		emps.add("test1");
		emps.add("random1");
		emps.add("test2");
		emps.add("random2");
		emps.add("test3");
		emps.add("random3");
		emps.add("test4");
		emps.add("random4");
		emps.add("test5");
		emps.add("random5");
		ResponseEntity<List<String>> response = new ResponseEntity<List<String>>(emps, HttpStatus.OK);
		when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(response);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/topTenHighestEarningEmployeeNames")
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		logger.info("Status:" + result.getResponse().getStatus() + "::" + result.getResponse().getContentAsString());
		assertNotNull(result.getResponse());
		assertEquals(200, result.getResponse().getStatus());
		assertNotNull(result.getResponse().getContentAsString());
	}

	@Test
	void testCreateEmployee() throws Exception {
		logger.info("testCreateEmployee");

		HashMap<String, Object> payload = new HashMap<>();
		payload.put("employee_name", "tigor test");
		payload.put("employee_age", Integer.valueOf(29));
		payload.put("employee_salary", Integer.valueOf(892605));
		payload.put("profile_image", "");
		Employee employee = new Employee();
		employee.setId(12);
		employee.setEmployee_name("tigor test");
		ResponseEntity<Employee> response = new ResponseEntity<Employee>(employee, HttpStatus.OK);
		when(employeeService.createEmployee(Mockito.anyMap())).thenReturn(response);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(payload)))
				.andReturn();
		logger.info("Status:" + result.getResponse().getStatus() + "::" + result.getResponse().getContentAsString());
		assertNotNull(result.getResponse());
		assertEquals(200, result.getResponse().getStatus());
		assertNotNull(result.getResponse().getContentAsString());
	}

	@Test
	void testDeleteEmployeeById() throws Exception {
		logger.info("testDeleteEmployeeById");

		HashMap<String, Object> payload = new HashMap<>();
		payload.put("status", "success");
		payload.put("data", 12);
		payload.put("message", "Successfully! Record has been deleted");
		ResponseEntity<String> response = new ResponseEntity<String>(payload.toString(), HttpStatus.OK);
		when(employeeService.deleteEmployeeById(Mockito.anyString())).thenReturn(response);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/12")).andReturn();
		logger.info("Status:" + result.getResponse().getStatus() + "::" + result.getResponse().getContentAsString());
		assertNotNull(result.getResponse());
		assertEquals(200, result.getResponse().getStatus());
		assertNotNull(result.getResponse().getContentAsString());
	}
}
