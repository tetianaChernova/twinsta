package com.example.twinsta.domain.util;

import com.example.twinsta.domain.User;

import static java.util.Objects.nonNull;

public abstract class MessageHelper {
	public static String getAuthorName(User author) {
		return nonNull(author) ? author.getUsername() : "<none>";
	}
}
