package com.nqt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nqt.dao.RoleDAO;
import com.nqt.entities.Role;
import com.nqt.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleDAO roleDAO;

	@Override
	public void addRole(Role role) {
		roleDAO.addRole(role);

	}

	@Override
	public void updateRole(Role role) {
		roleDAO.updateRole(role);

	}

	@Override
	public List<Role> listRole() {
		return roleDAO.listRole();
	}

	@Override
	public Role getRoleById(Long id) {
		Role role = roleDAO.getRoleById(id);
		return role;
	}

	@Override
	public void removeRole(Role role) {
		roleDAO.removeRole(role);

	}

//	private boolean checkId(Role role, boolean b) {
//		Role rl = getRoleById(role.getRoleId());
//		if (rl == null && b == true) {
//			addRole(role);
//			return true;
//		} else if (rl != null && b == false) {
//			updateRole(role);
//			return true;
//		}
//		return false;
//	}
//
//	@Override
//	public boolean save(Role role, boolean b) {
//		if (checkId(role, b)) {
//			return true;
//		}
//		return false;
//	}

}
