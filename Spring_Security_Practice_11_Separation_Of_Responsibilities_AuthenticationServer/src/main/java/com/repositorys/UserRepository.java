package com.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.entities.User;

public interface UserRepository extends JpaRepository <User, Integer>{

	Optional<User> findUserByUsername(String username);
	
}
