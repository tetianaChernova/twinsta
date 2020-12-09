package com.example.twinsta.repos;

import com.example.twinsta.domain.psql.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
	User findByUsername(String username);

	User findByActivationCode(String code);
}
