package com.example.twinsta.controller;

import com.example.twinsta.domain.Role;
import com.example.twinsta.domain.User;
import com.example.twinsta.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

import static java.util.Objects.nonNull;

@Controller
public class RegistrationController {

	@Autowired
	private UserRepo userRepo;

	@GetMapping("/registration")
	public String registration() {
		return "registration";
	}

	@PostMapping("/registration")
	public String addUser(User user, Map<String, Object> model) {
		User userFromDb = userRepo.findByUsername(user.getUsername());
		if (nonNull(userFromDb)) {
			model.put("errorMessage", "User exists!");
			return "registration";
		}

		user.setActive(true);
		user.setRoles(Collections.singleton(Role.USER));
		userRepo.save(user);
		return "redirect:/login";
	}
}
