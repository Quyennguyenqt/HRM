package com.nqt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nqt.entities.Employee;
import com.nqt.service.EmployeeService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
	@Autowired
	EmployeeService empService;

	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> findAllEmployee() {
		List<Employee> emp = empService.listEmployee();
		if (emp.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(emp, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	public ResponseEntity<Employee> getEmployeetById(@PathVariable("id") Long id) {

		Employee emp = empService.getEmployeeById(id);
		if (emp == null) {
			return new ResponseEntity<>(emp, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(emp, HttpStatus.OK);
	}

	@RequestMapping(value = "/employees", method = RequestMethod.POST)
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee emp) {
		if (empService.save(emp, true)) {
			return new ResponseEntity<>(emp, HttpStatus.CREATED);
		}
		return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/employees", method = RequestMethod.PUT)
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee emp) {
		if (empService.save(emp, false)) {
			return new ResponseEntity<>(emp, HttpStatus.OK);
		}
		return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);

	}

	@RequestMapping(value = "/employees", method = RequestMethod.DELETE)
	public ResponseEntity<Employee> deleteEmployee(@RequestBody Employee emp) {
		Employee empCurr = empService.getEmployeeById(emp.getEmpId());
		if (empCurr == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		empService.removeEmployee(empCurr);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
