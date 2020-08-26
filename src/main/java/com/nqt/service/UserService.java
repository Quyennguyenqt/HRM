package com.nqt.service;

import java.util.List;

import com.nqt.entities.User;

public interface UserService {
	public void addUser(User user);

	public void updateUser(User userNew);

	public List<User> listUser();

	public User getUserById(Long id);

	public void removeUser(User user);
	
	public boolean save(User user, boolean b);
	
	public boolean existByUserName(String username);
	
	public boolean existByEmail(String email);
}
