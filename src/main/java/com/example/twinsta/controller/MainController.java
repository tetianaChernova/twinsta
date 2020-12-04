package com.example.twinsta.controller;

import com.example.twinsta.domain.Message;
import com.example.twinsta.domain.User;
import com.example.twinsta.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static com.example.twinsta.controller.ControllerUtils.getErrors;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Controller
public class MainController {

	@Value("${upload.path}")
	private String uploadPath;

	@Autowired
	private MessageRepo messageRepo;

	@GetMapping("/")
	public String greeting(Map<String, Object> model) {
		return "greeting";
	}

	@GetMapping("/main")
	public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
		Iterable<Message> messages;

		if (nonNull(filter) && isFalse(filter.isEmpty())) {
			messages = messageRepo.findMessageByTag(filter);
		} else {
			messages = messageRepo.findAll();
		}

		model.addAttribute("messages", messages);
		model.addAttribute("filter", filter);
		return "main";
	}

	@PostMapping("/main")
	public String add(
			@AuthenticationPrincipal User user,
			@Valid Message message,
			BindingResult bindingResult,
			Model model,
			@RequestParam("file") MultipartFile file) throws IOException {
		message.setAuthor(user);
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = getErrors(bindingResult);
			model.mergeAttributes(errorMap);
			model.addAttribute("message", message);
		} else {
			if (nonNull(file) && isFalse(file.getOriginalFilename().isEmpty())) {
				File uploadDir = new File(uploadPath);
				if (isFalse(uploadDir.exists())) {
					uploadDir.mkdir();
				}
				String uuidFile = UUID.randomUUID().toString();
				String resultFilename = uuidFile + "." + file.getOriginalFilename();
				file.transferTo(new File(uploadPath + "/" + resultFilename));
				message.setFilename(resultFilename);
			}
			model.addAttribute("message", null);
			messageRepo.save(message);
		}
		Iterable<Message> messages = messageRepo.findAll();
		model.addAttribute("messages", messages);
		return "main";
	}
}