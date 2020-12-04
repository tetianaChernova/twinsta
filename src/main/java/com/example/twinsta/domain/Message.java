package com.example.twinsta.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import static java.util.Objects.nonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Please fill the message")
	@Length(max = 2048, message = "Message is too long (more than 2kB)")
	private String text;
	@Length(max = 255, message = "Tag message is too long")
	private String tag;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User author;

	private String filename;

	public String getAuthorName() {
		return nonNull(author) ? author.getUsername() : "<none>";
	}
}
