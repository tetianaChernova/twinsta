package com.example.twinsta.controller;

import com.example.twinsta.domain.Role;
import com.example.twinsta.domain.User;
import com.example.twinsta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public String getUsers(Model model) {
		model.addAttribute("users", userService.findAll());
		return "userList";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("{user}")
	public String editUser(@PathVariable User user, Model model) {
		model.addAttribute("user", user);
		model.addAttribute("roles", Role.values());
		return "userEdit";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public String saveUser(
			@RequestParam String username,
			@RequestParam Map<String, String> form,
			@RequestParam("userId") User user) {
		userService.saveUser(user, username, form);
		return "redirect:/user";
	}

	@GetMapping("/profile")
	public String getUserProfile(Model model, @AuthenticationPrincipal User user) {
		model.addAttribute("username", user.getUsername());
		model.addAttribute("email", user.getEmail());
		return "profile";
	}

	@PostMapping("/profile")
	public String editUserProfile(
			@AuthenticationPrincipal User user,
			@RequestParam String password,
			@RequestParam String email) {
		userService.updateProfile(user, password, email);
		return "redirect:/login";
	}

	@GetMapping("subscribe/{user}")
	public String subscribe(
			@AuthenticationPrincipal User currentUser,
			@PathVariable User user) {
		userService.subscribe(currentUser, user);
		return "redirect:/user-messages/" + user.getId();
	}

	@GetMapping("unsubscribe/{user}")
	public String unsubscribe(
			@AuthenticationPrincipal User currentUser,
			@PathVariable User user) {
		userService.unsubscribe(currentUser, user);
		return "redirect:/user-messages/" + user.getId();
	}

	@GetMapping("{type}/{user}/list")
	public String userList(
			Model model,
			@PathVariable User user,
			@PathVariable String type) {
		model.addAttribute("type", type);
		model.addAttribute("userChannel", user);

		if ("subscriptions".equals(type)) {
			model.addAttribute("users", user.getSubscriptions());
		} else {
			model.addAttribute("users", user.getSubscribers());
		}
		return "subscriptions";
	}
}
