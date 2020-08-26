package com.nqt.service;

import java.util.List;

import com.nqt.entities.Employee;

public interface EmployeeService {
	public void addEmployee(Employee employee);

	public void updateEmployee(Employee employee);

	public List<Employee> listEmployee();

	public Employee getEmployeeById(Long id);

	public void removeEmployee(Employee employee);

	public boolean save(Employee employee, boolean b);
	
	public void removeContracEmp(Long id);
}
