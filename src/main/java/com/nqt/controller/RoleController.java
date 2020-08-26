package com.nqt.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nqt.entities.Role;
import com.nqt.service.RoleService;

@RestController
public class RoleController {
	@Autowired
	RoleService roleService;

	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public ResponseEntity<List<Role>> findAllRole() {
		List<Role> role = roleService.listRole();
		if (role.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(role, HttpStatus.OK);
	}

	@RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
	public ResponseEntity<Role> getRoleById(@PathVariable("id") Long id) {
		//Optional<Role> role = Optional.of(roleService.getRoleById(id));
		Role role = roleService.getRoleById(id);
		if (role == null) {
			return new ResponseEntity<>(role, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(role, HttpStatus.OK);
	}

	@RequestMapping(value = "/roles", method = RequestMethod.POST)
	public ResponseEntity<Role> createRole(@RequestBody Role role) {
		Role roleCurr = roleService.getRoleById(role.getRoleId());
		if(roleCurr == null) {
			roleService.addRole(role);
			return new ResponseEntity<>(role, HttpStatus.CREATED);
		}
		return new ResponseEntity<Role>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/roles", method = RequestMethod.PUT)
	public ResponseEntity<Role> updateRole(@RequestBody Role role) {
		Role roleCurr = roleService.getRoleById(role.getRoleId());
		if(roleCurr != null) {
			roleService.updateRole(role);
			return new ResponseEntity<>(role, HttpStatus.OK);
		}
		return new ResponseEntity<Role>(HttpStatus.NO_CONTENT);

	}

	@RequestMapping(value = "/roles",method = RequestMethod.DELETE)
	public ResponseEntity<Role> deleteRole(@RequestBody Role role) {
		Optional<Role> roleCurr = Optional.of(roleService.getRoleById(role.getRoleId()));
		if (!roleCurr.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		roleService.removeRole(roleCurr.get());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
