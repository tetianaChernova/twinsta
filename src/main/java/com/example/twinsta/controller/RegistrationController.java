package com.example.twinsta.controller;

import com.example.twinsta.domain.psql.User;
import com.example.twinsta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

import static com.example.twinsta.controller.ControllerUtils.getErrors;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Controller
public class RegistrationController {

	@Value("${upload.path}")
	private String uploadPath;

	@Autowired
	private UserService userService;

	@GetMapping("/registration")
	public String registration() {
		return "registration";
	}

	@PostMapping("/registration")
	public String addUser(
			@RequestParam("file") MultipartFile file,
			@RequestParam("passwordConfirm") String passwordConfirm,
			@Valid User user,
			BindingResult bindingResult,
			Model model) throws IOException {
		boolean isConfirmEmpty = isEmpty(passwordConfirm);
		if (isConfirmEmpty) {
			model.addAttribute("passwordConfirmError", "Password confirmation can't be empty");
		}
		if (nonNull(user.getPassword()) &&
				isFalse(user.getPassword().equals(passwordConfirm))) {
			model.addAttribute("passwordError", "Passwords should be equal");
			return "registration";
		}
		if (isConfirmEmpty || bindingResult.hasErrors()) {
			Map<String, String> errorMap = getErrors(bindingResult);
			model.mergeAttributes(errorMap);
			return "registration";
		}
		String filename = ControllerUtils.setUploadedFile(file, uploadPath);
		if (nonNull(filename) && isFalse(isEmpty(filename))) {
			user.setFilename(filename);
		}
		if (isFalse(userService.addUser(user))) {
			model.addAttribute("usernameError", "User exists!");
			return "registration";
		}
		return "redirect:/login";
	}

	@GetMapping("/activate/{code}")
	public String activate(Model model, @PathVariable String code) {
		boolean isActivated = userService.activateUser(code);
		if (isActivated) {
			model.addAttribute("messageType", "success");
			model.addAttribute("message", "User is successfully activated");
		} else {
			model.addAttribute("messageType", "danger");
			model.addAttribute("message", "Activation code is not found");
		}
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		httpSession.invalidate();
		return "redirect:/login";
	}
}
