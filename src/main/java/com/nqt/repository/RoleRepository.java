package com.nqt.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nqt.entities.ERole;
import com.nqt.entities.Role;

@Repository
public interface RoleRepository {
	Optional<Role> findByName(ERole name);
}
