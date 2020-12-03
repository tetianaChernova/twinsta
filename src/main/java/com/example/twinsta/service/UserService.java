package com.example.twinsta.service;

import com.example.twinsta.domain.Role;
import com.example.twinsta.domain.User;
import com.example.twinsta.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private MailSender mailSender;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsername(username);
	}

	public boolean addUser(User user) {
		User userFromDb = userRepo.findByUsername(user.getUsername());

		if (nonNull(userFromDb)) {
			return false;
		}
		user.setActive(false);
		user.setRoles(Collections.singleton(Role.USER));
		user.setActivationCode(UUID.randomUUID().toString());
		userRepo.save(user);

		if (isFalse(isEmpty(user.getEmail()))) {

			String message = String.format(
					"Hello %s! \n" + "Welcome to Twinsta! Please visit next link: http:/localhost:8080/activate/%s",
					user.getUsername(), user.getActivationCode()
			);

			mailSender.send(user.getEmail(), "Activation code", message);
		}
		return true;
	}

	public boolean activateUser(String code) {
		User user = userRepo.findByActivationCode(code);
		if (isNull(user)){
			return false;
		}
		user.setActive(true);
		user.setActivationCode(null);
		userRepo.save(user);
		return true;
	}
}
