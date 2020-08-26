package com.nqt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nqt.dao.UserDAO;
import com.nqt.entities.Role;
import com.nqt.entities.User;
import com.nqt.entities.UserRole;
import com.nqt.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	UserDAO userDAO;
	private final String admin = "ADMIN";
	private final String us = "USER";
	private final String monitor = "MONITOR";
	private final Long AD = 1L;
	private final Long US = 2L;
	private final Long CTB = 3L;

	@Override
	public void addUser(User user) {
		Long id = userDAO.addUser(user);
		user.setUserId(id);
		List<UserRole> list = new ArrayList<UserRole>(user.getUserRoles());
		UserRole ur = new UserRole();
		ur.setUser(user);
		Role role = list.get(0).getRole();
		if ( user.getUserRoles().isEmpty() || list.get(0).getRole() == null || role.getRoleId() == null) {
			role.setRoleId(US);
			ur.setRole(role);
			
		} else {
			ur.setRole(role);
			userDAO.addUserRole(ur);
			role.setRoleId(US);
			ur.setRole(role);
			ur.setId(null);
		}
		userDAO.addUserRole(ur);
	}

	@Override
	public void updateUser(User user) {
		User userCurr = getUserById(user.getUserId());
		if (userCurr != null) {
			userDAO.updateUser(user);
			List<UserRole> list = new ArrayList<UserRole>(user.getUserRoles());
			UserRole ur = new UserRole();
			Role role = list.get(0).getRole();
			ur.setRole(role);
			ur.setUser(user);
			userDAO.updateUserRole(ur);
		}
	}

	@Override
	public List<User> listUser() {
		return userDAO.listUser();
	}

	@Override
	public User getUserById(Long id) {
		return userDAO.getUserById(id);
	}

	@Override
	public void removeUser(User user) {
		// userDAO.removeUserRole(user.getUserId());
		userDAO.removeUser(user);

	}

	@Override
	public boolean save(User user, boolean b) {
		if (checkId(user, b)) {
			return true;
		}
		return false;
	}

	private boolean checkId(User user, boolean b) {
		User u = getUserById(user.getUserId());
		if (u == null && b == true) {
			addUser(user);
			return true;
		} else if (u != null && b == false) {
			updateUser(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean existByUserName(String username) {
		User user = userDAO.existByUserName(username);
		if (user == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean existByEmail(String email) {
		User user = userDAO.existByEmail(email);
		if (user == null) {
			return false;
		}
		return false;
	}

}
