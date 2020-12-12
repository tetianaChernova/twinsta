package com.example.twinsta.service;

import com.example.twinsta.domain.neo4j.UserNeo;
import com.example.twinsta.domain.psql.Role;
import com.example.twinsta.domain.psql.User;
import com.example.twinsta.repos.UserRepo;
import com.example.twinsta.repos.neo4j.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private UserRepository userNeo4jRepo;
	@Autowired
	private MailSender mailSender;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (isNull(user)) {
			throw new UsernameNotFoundException("User not found");
		}
		return user;
	}

	public boolean addUser(User user) {
		User userFromDb = userRepo.findByUsername(user.getUsername());

		if (nonNull(userFromDb)) {
			return false;
		}
		user.setActive(false);
		user.setRoles(Collections.singleton(Role.USER));
		user.setActivationCode(UUID.randomUUID().toString());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);

		if (isFalse(isEmpty(user.getEmail()))) {

			sendMessage(user);
		}
		return true;
	}

	private void sendMessage(User user) {
		String message = String.format(
				"Hello %s! \n" + "Welcome to Twinsta! Please visit next link: http:/localhost:8080/activate/%s",
				user.getUsername(), user.getActivationCode()
		);

		mailSender.send(user.getEmail(), "Activation code", message);
	}

	public boolean activateUser(String code) {
		User user = userRepo.findByActivationCode(code);
		if (isNull(user)) {
			return false;
		}
		user.setActive(true);
		user.setActivationCode(null);
//		userRepo.save(user);
		UserNeo userNeo = UserNeo.builder().name(user.getUsername()).email(user.getEmail()).build();
		userNeo4jRepo.save(userNeo);
		return true;
	}

	public List<User> findAll() {
		return userRepo.findAll();
	}

	public void saveUser(User user, String username, Map<String, String> form) {
		user.setUsername(username);

		Set<String> roles = Arrays.stream(Role.values())
				.map(Role::name)
				.collect(Collectors.toSet());

		user.getRoles().clear();
		form.keySet().forEach(key -> {
			if (roles.contains(key)) {
				user.getRoles().add(Role.valueOf(key));
			}
		});
		userRepo.save(user);
	}

	public User updateProfile(User user, String password, String email) {
		String userEmail = user.getEmail();

		boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
				(userEmail != null && !userEmail.equals(email));

		if (isEmailChanged) {
			user.setEmail(email);
			if (isFalse(isEmpty(email))) {
				user.setActivationCode(UUID.randomUUID().toString());
			}
		}

		if (isFalse(isEmpty(password))) {
			user.setPassword(passwordEncoder.encode(password));
		}

		userRepo.save(user);
		if (isEmailChanged) {
			sendMessage(user);
		}
		return user;
	}


	public UserNeo getUserNeoByName(String name) {
		return userNeo4jRepo.getUserNeoByName(name);
	}

	public void subscribe(User follower, String userToFollow) {
		userNeo4jRepo.subscribe(follower.getUsername(), userToFollow);
	}

	public void unsubscribe(User follower, String userToUnFollow) {
		userNeo4jRepo.unsubscribe(follower.getUsername(), userToUnFollow);
	}

	public List<UserNeo> getUserSubscriptions(String username) {
		return userNeo4jRepo.getUserSubscriptions(username);
	}

	public List<UserNeo> getUserSubscribers(String username) {
		return userNeo4jRepo.getUserSubscribers(username);
	}

	public Iterable<UserNeo> getUserRecommendations(User user) {
		return userNeo4jRepo.findRecommendationsForUser(user.getUsername());
	}
}
