package com.nqt.dao;

import java.util.List;

import com.nqt.entities.Department;

public interface DepartmentDAO {
	public void addDepartment(Department depart);

	public void updateDepartment(Department depart);

	public List<Department> listDepartment();

	public Department getDepartmentById(Long id);

	public void removeDepartment(Department depart);
}
