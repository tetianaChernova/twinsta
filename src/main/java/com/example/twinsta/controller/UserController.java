package com.example.twinsta.controller;

import com.example.twinsta.domain.neo4j.UserNeo;
import com.example.twinsta.domain.psql.Role;
import com.example.twinsta.domain.psql.User;
import com.example.twinsta.repos.neo4j.UserRepository;
import com.example.twinsta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Controller
@RequestMapping("/user")
public class UserController {
	@Value("${upload.path}")
	private String uploadPath;

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

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
		model.addAttribute("user", user);
		return "profile";
	}

	@PostMapping("/profile")
	public String editUserProfile(
			@RequestParam("file") MultipartFile file,
			@RequestParam String password,
			@RequestParam(required = false) String email,
			@AuthenticationPrincipal User user) throws IOException {
		String filename = ControllerUtils.setUploadedFile(file, uploadPath);
		if (nonNull(filename) && isFalse(isEmpty(filename))) {
			user.setFilename(filename);
		}
		userService.updateProfile(user, password, email);
		return "redirect:/logout";
	}

	@GetMapping("subscribe/{username}")
	public String subscribe(
			@AuthenticationPrincipal User currentUser,
			@PathVariable String username) {
		userService.subscribe(currentUser, username);
		return "redirect:/user-messages/" + username;
	}

	@GetMapping("unsubscribe/{username}")
	public String unsubscribe(
			@AuthenticationPrincipal User currentUser,
			@PathVariable String username) {
		userService.unsubscribe(currentUser, username);
		return "redirect:/user-messages/" + username;
	}

	@GetMapping("{type}/{username}/list")
	public String userList(
			Model model,
			@PathVariable String username,
			@PathVariable String type) {
		model.addAttribute("type", type);
		model.addAttribute("userChannel", username);

		if ("subscriptions".equals(type)) {
			model.addAttribute("users", userService.getUserSubscriptions(username));
		} else {
			model.addAttribute("users", userService.getUserSubscribers(username));
		}
		return "subscriptions";
	}

	@GetMapping("/recommendations")
	public String getUserRecommendations(Model model, @AuthenticationPrincipal User user) {
		Iterable<UserNeo> recommendations = userService.getUserRecommendations(user);
		model.addAttribute("recommendations", recommendations);
		return "recommendations";
	}
}
