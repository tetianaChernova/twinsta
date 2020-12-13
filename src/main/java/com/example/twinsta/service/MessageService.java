package com.example.twinsta.service;

import com.example.twinsta.domain.dto.MessageDto;
import com.example.twinsta.domain.psql.Message;
import com.example.twinsta.domain.psql.User;
import com.example.twinsta.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Service
public class MessageService {
	@Autowired
	private MessageRepo messageRepo;

	public Iterable<MessageDto> getMessageList(String filter, User user) {
		return (nonNull(filter) && isFalse(filter.isEmpty()))
				? messageRepo.findMessageByTag(filter, user)
				: messageRepo.findAll(user);
	}

	public Iterable<MessageDto> getAllMessages(User user) {
		return messageRepo.findAll(user);
	}

	public Iterable<MessageDto> getUserMessages(String authorName, User user) {
		return messageRepo.findByUserName(authorName, user);
	}

	public Message getMessageById(Long messageId) {
		return messageRepo.findMessageById(messageId);
	}
}
