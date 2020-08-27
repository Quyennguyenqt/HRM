package com.nqt.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nqt.entities.ERole;
import com.nqt.entities.Role;
import com.nqt.entities.User;
import com.nqt.entities.UserRole;
import com.nqt.payload.request.LoginRequest;
import com.nqt.payload.request.SignupRequest;
import com.nqt.payload.response.JwtResponse;
import com.nqt.payload.response.MessageResponse;
import com.nqt.security.jwt.JwtUtils;
import com.nqt.security.services.UserDetailsImpl;
import com.nqt.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;


	@Autowired
	UserService userService;
	

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userService.existByUserName(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userService.existByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User usernew = new User();
		usernew.setUsername(signUpRequest.getUsername());
		usernew.setEmail(signUpRequest.getEmail());
		usernew.setPassword(encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Role role = new Role();
		if (strRoles == null) {
			// new RuntimeException("Error: Role is not found.");
			role.setRoleName(ERole.user);
		} else {
			role.setRoleName(ERole.admin);

		}
		UserRole ur = new UserRole();
		ur.setRole(role);
		Set<UserRole> setUr = new HashSet<>();
		setUr.add(ur);
		usernew.setUserRoles(setUr);
		// user.setRoles(roles);
		userService.save(usernew, true);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}