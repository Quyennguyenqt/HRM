package com.nqt.service;

import java.util.List;

import com.nqt.entities.Role;

public interface RoleService {
	public void addRole(Role role);

	public void updateRole(Role role);

	public List<Role> listRole();

	public Role getRoleById(Long id);

	public void removeRole(Role role);
	
	//public boolean save(Role role, boolean b);

}
