package com.nqt.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.nqt.entities.Role;

@Repository
public interface RoleRepository {
	Optional<Role> findByName(String name);
}
