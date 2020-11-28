package com.example.twinsta.repos;

import com.example.twinsta.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Long> {
	List<Message> findMessageByTag(String tag);
}
