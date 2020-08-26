package com.nqt.dao;

import java.util.List;

import com.nqt.entities.Role;
import com.nqt.entities.User;
import com.nqt.entities.UserRole;

public interface UserDAO {
	public Long addUser(User user);

	public void updateUser(User user);

	public List<User> listUser();

	public User getUserById(Long id);

	public void removeUser(User user);

	public void addUserRole(UserRole userole);

	public void updateUserRole(UserRole userrole);
	
	public User existByUserName(String username);
	
	public User existByEmail(String email);
	
	public List<User> findByUserName(String useranme);
	
	public List<Role> getRoleByUser(User user);
	
}
