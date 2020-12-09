package com.example.twinsta.domain.dto;

import com.example.twinsta.domain.psql.Message;
import com.example.twinsta.domain.psql.User;
import com.example.twinsta.domain.util.MessageHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MessageDto {
	private Long id;
	private String text;
	private String tag;
	private User author;
	private String filename;
	private Long likes;
	private Boolean meLiked;

	public MessageDto(Message message, Long likes, Boolean meLiked) {
		this.id = message.getId();
		this.text = message.getText();
		this.tag = message.getTag();
		this.author = message.getAuthor();
		this.filename = message.getFilename();
		this.likes = likes;
		this.meLiked = meLiked;
	}

	public String getAuthorName() {
		return MessageHelper.getAuthorName(author);
	}
}
