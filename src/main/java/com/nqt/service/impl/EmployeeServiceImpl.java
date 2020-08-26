package com.nqt.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nqt.dao.EmployeeDAO;
import com.nqt.entities.ContracEmp;
import com.nqt.entities.Employee;
import com.nqt.entities.TrainningEmployee;
import com.nqt.service.EmployeeService;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDAO empDAO;

	@Override
	public void addEmployee(Employee employee) {
		Calendar c = Calendar.getInstance();
		List<TrainningEmployee> trainEmp = new ArrayList<TrainningEmployee>(employee.getTrainEmp());
		List<ContracEmp> contracEmp = new ArrayList<ContracEmp>(employee.getContracEmp());
		
		Long id = empDAO.addEmployee(employee);
		employee.setEmpId(id);
		
		
		ContracEmp ctemp = new ContracEmp();
		ctemp.setContractEmpStartDate(c.getTime());
		c.add(Calendar.MONTH, 6);
		ctemp.setContractEmpEndDate(c.getTime());
		ctemp.setContractEmpStatus(contracEmp.get(0).getContractEmpStatus());
		ctemp.setEmpConId(employee);
		ctemp.setContractId(contracEmp.get(0).getContractId());
		empDAO.addContractEmp(ctemp);
		
		TrainningEmployee trainEmpl = new TrainningEmployee();
		trainEmpl.setTrainingEmpStartDate(c.getTime());
		c.add(Calendar.MONTH, 2);
		trainEmpl.setTrainingEmpStartEnd(c.getTime());
		trainEmpl.setTrainingEmpStatus(trainEmp.get(0).getTrainingEmpStatus());
		trainEmpl.setTrainingEmpResult(trainEmp.get(0).getTrainingEmpResult());
		trainEmpl.setEmTrainId(employee);
		trainEmpl.setTrainingId(trainEmp.get(0).getTrainingId());
		empDAO.addTrainEmp(trainEmpl);

	}

	@Override
	public void updateEmployee(Employee employee) {
		Employee empCurr = getEmployeeById(employee.getEmpId());
		if (empCurr != null) {
			empDAO.updateEmployee(employee);
			Calendar c = Calendar.getInstance();
			List<TrainningEmployee> trainEmpList = new ArrayList<TrainningEmployee>(employee.getTrainEmp());
			List<ContracEmp> contracEmpList = new ArrayList<ContracEmp>(employee.getContracEmp());
			
			ContracEmp ctemp = new ContracEmp();
			ctemp.setContractEmpStartDate(c.getTime());
			c.add(Calendar.MONTH, 6);
			ctemp.setContractEmpEndDate(c.getTime());
			ctemp.setContractEmpStatus(contracEmpList.get(0).getContractEmpStatus());
			ctemp.setEmpConId(employee);
			ctemp.setContractId(contracEmpList.get(0).getContractId());
			empDAO.updateContractEmp(ctemp);
			
			TrainningEmployee trainEmpl = new TrainningEmployee();
			trainEmpl.setTrainingEmpStartDate(c.getTime());
			c.add(Calendar.MONTH, 2);
			trainEmpl.setTrainingEmpStartEnd(c.getTime());
			trainEmpl.setTrainingEmpStatus(trainEmpList.get(0).getTrainingEmpStatus());
			trainEmpl.setTrainingEmpResult(trainEmpList.get(0).getTrainingEmpResult());
			trainEmpl.setEmTrainId(employee);
			trainEmpl.setTrainingId(trainEmpList.get(0).getTrainingId());
			empDAO.updateEmpTrain(trainEmpl);
		}
		
		
		

	}

	@Override
	public List<Employee> listEmployee() {
		return empDAO.listEmployee();
	}

	@Override
	public Employee getEmployeeById(Long id) {
		return empDAO.getEmployeeById(id);
	}

	@Override
	public void removeEmployee(Employee employee) {
		// empDAO.removeContractEmp(employee.getEmpId());
		empDAO.removeEmployee(employee);

	}

	@Override
	public boolean save(Employee employee, boolean b) {
		if (checkId(employee, b)) {
			return true;
		}
		return false;
	}

	private boolean checkId(Employee employee, boolean b) {
		Employee rl = getEmployeeById(employee.getEmpId());
		if (rl == null && b == true) {
			addEmployee(employee);
			return true;
		} else if (rl != null && b == false) {
			updateEmployee(employee);
			return true;
		}
		return false;

	}

	@Override
	public void removeContracEmp(Long id) {
		empDAO.removeContractEmp(id);

	}

}
