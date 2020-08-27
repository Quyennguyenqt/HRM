package com.nqt.dao;

import java.util.List;

import com.nqt.entities.Role;

public interface RoleDAO {
	public void addRole(Role role);

	public void updateRole(Role role);

	public List<Role> listRole();

	public Role getRoleById(Long id);

	public void removeRole(Role role);
	
	
}
