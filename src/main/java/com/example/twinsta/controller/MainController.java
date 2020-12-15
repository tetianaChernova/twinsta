package com.example.twinsta.controller;

import com.example.twinsta.domain.dto.MessageDto;
import com.example.twinsta.domain.neo4j.UserNeo;
import com.example.twinsta.domain.psql.Message;
import com.example.twinsta.domain.psql.User;
import com.example.twinsta.repos.MessageRepo;
import com.example.twinsta.service.MessageService;
import com.example.twinsta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.twinsta.controller.ControllerUtils.getErrors;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Controller
public class MainController {

	@Value("${upload.path}")
	private String uploadPath;

	@Autowired
	private MessageRepo messageRepo;
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String greeting() {
		return "greeting";
	}

	@GetMapping("/main")
	public String main(
			@RequestParam(required = false, defaultValue = "") String filter,
			@AuthenticationPrincipal User user,
			Model model) {
		Iterable<MessageDto> messages = messageService.getMessageList(filter, user);
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
			model.addAttribute("messages", messageService.getMessageList("", user));
			model.addAttribute("filter", "");
		} else {
			String filename = ControllerUtils.setUploadedFile(file, uploadPath);
			if (nonNull(filename) && isFalse(isEmpty(filename))) {
				message.setFilename(filename);
			}
			model.addAttribute("message", null);
			messageRepo.save(message);
		}
		Iterable<MessageDto> messages = messageService.getAllMessages(user);
		model.addAttribute("messages", messages);
		return "main";
	}

	@GetMapping("/user-messages/{username}")
	public String userMessages(
			@PathVariable String username,
			@AuthenticationPrincipal User currentUser,
			Model model,
			@RequestParam(required = false) Message message) {
		UserNeo user = userService.getUserNeoByName(username);
		Iterable<MessageDto> messages = messageService.getUserMessages(username, currentUser);
		model.addAttribute("usr", user);
		model.addAttribute("messages", messages);
		model.addAttribute("message", message);
		model.addAttribute("isCurrentUser", currentUser.getUsername().equals(username));
		List<UserNeo> subscribers = userService.getUserSubscribers(username);
		List<UserNeo> subscriptions = userService.getUserSubscriptions(username);
		model.addAttribute("isSubscriber", subscribers.stream()
				.map(UserNeo::getName)
				.collect(Collectors.toList())
				.contains(currentUser.getUsername()));
		model.addAttribute("userChannel", username);
		model.addAttribute("subscriptionsCount", subscriptions.size());
		model.addAttribute("subscribersCount", subscribers.size());
		return "userMessages";
	}

	@GetMapping("/user-messages/{username}/{message}/edit")
	public String messageEditor(
			@PathVariable Message message,
			Model model) {
		model.addAttribute("message", message);
		return "messageEditor";
	}

	@PostMapping("/user-messages/{username}/{messageId}/edit")
	public String updateMessages(
			@PathVariable String username,
			@PathVariable Long messageId,
			@RequestParam("file") MultipartFile file,
			@Valid Message message,
			BindingResult bindingResult,
			Model model) throws IOException {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = getErrors(bindingResult);
			model.mergeAttributes(errorMap);
			model.addAttribute("message", message);
			return "messageEditor";
		} else {
			Message messageFromDb = messageService.getMessageById(messageId);
			if (nonNull(messageFromDb)) {
				if (isFalse(isEmpty(message.getText()))) {
					messageFromDb.setText(message.getText());
				}

				if (isFalse(isEmpty(message.getTag()))) {
					messageFromDb.setTag(message.getTag());
				}
				String filename = ControllerUtils.setUploadedFile(file, uploadPath);
				if (nonNull(filename) && isFalse(isEmpty(filename))) {
					messageFromDb.setFilename(filename);
				}
				messageRepo.save(messageFromDb);
			}
			return "redirect:/user-messages/" + username;
		}
	}

	@GetMapping("/messages/{message}/like")
	public String likeMessage(
			@AuthenticationPrincipal User currentUser,
			@PathVariable Message message,
			RedirectAttributes redirectAttributes,
			@RequestHeader(required = false) String referer) {
		Set<User> likes = message.getLikes();
		if (likes.contains(currentUser)) {
			likes.remove(currentUser);
		} else {
			likes.add(currentUser);
		}
		message.setLikes(likes);
		messageRepo.save(message);
		UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
		components.getQueryParams()
				.forEach(redirectAttributes::addAttribute);
		return "redirect:" + components.getPath();
	}
}