package com.nqt.service;

import java.util.List;

import com.nqt.entities.Department;

public interface DepartmentService {
	public void addDepartment(Department department);

	public void updateDepartment(Department department);

	public List<Department> listDepartment();

	public Department getDepartmentById(Long id);

	public void removeDepartment(Department department);
	
	public boolean save(Department department, boolean b);
}
