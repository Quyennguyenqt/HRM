package com.nqt.dao;

import java.util.List;

import com.nqt.entities.ContracEmp;
import com.nqt.entities.Employee;
import com.nqt.entities.TrainningEmployee;

public interface EmployeeDAO {
	public Long addEmployee(Employee employee);

	public void updateEmployee(Employee employee);

	public List<Employee> listEmployee();

	public Employee getEmployeeById(Long id);

	public void removeEmployee(Employee employee);
	
	public void addContractEmp(ContracEmp conEmp);
	
	public void addTrainEmp(TrainningEmployee trainEmp);
	
	public void removeContractEmp(Long id);
	
	public void updateEmpTrain(TrainningEmployee tremp);
	
	public void updateContractEmp(ContracEmp contrEmp);
}
