package com.example.twinsta.repos;

import com.example.twinsta.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
