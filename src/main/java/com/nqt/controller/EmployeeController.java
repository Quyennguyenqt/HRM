package com.nqt.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nqt.entities.Employee;
import com.nqt.service.EmployeeService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api")
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

	@RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
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

	@RequestMapping(value = "/employees/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id, @RequestBody Employee emp) {
		emp.setEmpId(id);
		if (empService.save(emp, false)) {
			return new ResponseEntity<>(emp, HttpStatus.OK);
		}
		return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);

	}

	@RequestMapping(value = "/employees/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id) {
		Employee empCurr = empService.getEmployeeById(id);
		if (empCurr == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		empService.removeEmployee(empCurr);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/employees/export/pdf", produces = "application/text", method = RequestMethod.GET)
	//@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<InputStreamResource> exportEmployeePdf() {
		List<Employee> employees = empService.listEmployee();
		
		ByteArrayInputStream bais = empService.productPDFReport(employees);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=employee.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bais));
	}

	@GetMapping(value = "/employees/export/excel", produces = "application/json")
	public ResponseEntity<InputStreamResource> exportEmployeeExcel() throws IOException {
		List<Employee> employees = empService.listEmployee();
		ByteArrayInputStream bais = empService.productExcelReport(employees);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=employee.xlsx");
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(bais));
	}
}
