package com.nqt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nqt.entities.User;
import com.nqt.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> findAllUser() {
		List<User> user = userService.listUser();
		if (user.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
		User user = userService.getUserById(id);
		if (user == null) {
			return new ResponseEntity<User>(user, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {
		if (userService.save(user, true)) {
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		}
		return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		if (userService.save(user, false)) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/users", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@RequestBody User user) {
		User u = userService.getUserById(user.getUserId());
		if (u == null) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		userService.removeUser(u);
		return new ResponseEntity<User>(HttpStatus.OK);
	}

}
