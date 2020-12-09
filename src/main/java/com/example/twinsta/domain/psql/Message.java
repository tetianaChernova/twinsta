package com.example.twinsta.domain.psql;

import com.example.twinsta.domain.util.MessageHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
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

	@ManyToMany
	@JoinTable(
			name = "message_likes",
			joinColumns = {@JoinColumn(name = "message_id")},
			inverseJoinColumns = {@JoinColumn(name = "user_id")})
	public Set<User> likes = new HashSet<>();

	public String getAuthorName() {
		return MessageHelper.getAuthorName(author);
	}
}
