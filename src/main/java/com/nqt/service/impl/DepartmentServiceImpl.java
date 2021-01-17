package com.nqt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nqt.dao.DepartmentDAO;
import com.nqt.entities.Department;
import com.nqt.service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	DepartmentDAO departDAO;

	@Override
	public void addDepartment(Department department) {
		departDAO.addDepartment(department);

	}

	@Override
	public void updateDepartment(Department department) {
		departDAO.updateDepartment(department);

	}

	@Override
	public List<Department> listDepartment() {
		return departDAO.listDepartment();
	}

	@Override
	public Department getDepartmentById(Long id) {
		return departDAO.getDepartmentById(id);
	}

	@Override
	public void removeDepartment(Department department) {
		departDAO.removeDepartment(department);

	}

	@Override
	public boolean save(Department department, boolean b) {
		if (checkId(department, b)) {
			return true;
		}
		return false;
	}

	private boolean checkId(Department department, boolean b) {
		Department rl = getDepartmentById(department.getDepartmentId());
		if (rl == null && b == true) {
			addDepartment(department);
			return true;
		} else if (rl != null && b == false) {
			updateDepartment(department);
			return true;
		}
		return false;
	}

}
