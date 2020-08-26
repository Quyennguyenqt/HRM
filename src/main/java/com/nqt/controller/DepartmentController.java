package com.nqt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nqt.entities.Department;
import com.nqt.service.DepartmentService;

@RestController
public class DepartmentController {
	
	@Autowired
	DepartmentService departSerivce;
	
	@RequestMapping(value = "/departments", method = RequestMethod.GET)
	public ResponseEntity<List<Department>> findAllDepartment() {
		List<Department> department = departSerivce.listDepartment();
		if (department.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		//return new ResponseEntity<>(parts, HttpStatus.OK);
		return new ResponseEntity<List<Department>>(department, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/departments/{id}", method = RequestMethod.GET)
	public ResponseEntity<Department> findById(@PathVariable("id") Long id) {
		Department department = departSerivce.getDepartmentById(id);
		if (department == null) {
			return new ResponseEntity<Department>(department, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Department>(department, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/departments", method = RequestMethod.POST)
	public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
		if (departSerivce.save(department, true)) {
			return new ResponseEntity<Department>(department, HttpStatus.CREATED);
		}
		return new ResponseEntity<Department>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/departments", method = RequestMethod.PUT)
	public ResponseEntity<Department> updateDepartment(@RequestBody Department department) {
		if (departSerivce.save(department, false)) {
			return new ResponseEntity<Department>(department, HttpStatus.OK);
		}
		return new ResponseEntity<Department>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/departments")
	public ResponseEntity<Department> deleteDepartment(@RequestBody Department department) {
		Department departmentCurr = departSerivce.getDepartmentById(department.getDepartmentId());
		if (departmentCurr == null) {
			return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
		}
		departSerivce.removeDepartment(departmentCurr);
		return new ResponseEntity<Department>(departmentCurr,HttpStatus.OK);
	}
}
