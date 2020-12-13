package com.example.twinsta.repos;

import com.example.twinsta.domain.dto.MessageDto;
import com.example.twinsta.domain.psql.Message;
import com.example.twinsta.domain.psql.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Long> {
	@Query("SELECT new com.example.twinsta.domain.dto.MessageDto(" +
			"message, " +
			"COUNT(messagelikes), " +
			"(SUM(CASE WHEN messagelikes = :user THEN 1 ELSE 0 END) > 0)) " +
			"FROM Message message LEFT JOIN message.likes messagelikes " +
			"GROUP BY message")
	List<MessageDto> findAll(@Param("user") User user);

	@Query("SELECT new com.example.twinsta.domain.dto.MessageDto(" +
			"message, " +
			"COUNT(messagelikes), " +
			"(SUM(CASE WHEN messagelikes = :user THEN 1 ELSE 0 END) > 0)) " +
			"FROM Message message LEFT JOIN message.likes messagelikes " +
			"WHERE message.tag =:tag " +
			"GROUP BY message")
	List<MessageDto> findMessageByTag(@Param("tag") String tag, @Param("user") User user);

	@Query("SELECT new com.example.twinsta.domain.dto.MessageDto(" +
			"message, " +
			"COUNT(messagelikes), " +
			"(SUM(CASE WHEN messagelikes = :user THEN 1 ELSE 0 END) > 0)) " +
			"FROM Message message LEFT JOIN message.likes messagelikes " +
			"where message.author = :author " +
			"GROUP BY message")
	List<MessageDto> findByUser(@Param("author") User author, @Param("user") User user);

	@Query("SELECT new com.example.twinsta.domain.dto.MessageDto(" +
			"message, " +
			"COUNT(messagelikes), " +
			"(SUM(CASE WHEN messagelikes = :user THEN 1 ELSE 0 END) > 0)) " +
			"FROM Message message LEFT JOIN message.likes messagelikes " +
			"where message.author.username = :authorName " +
			"GROUP BY message")
	Iterable<MessageDto> findByUserName(@Param("authorName") String authorName, User user);

	Message findMessageById(Long messageId);
}
