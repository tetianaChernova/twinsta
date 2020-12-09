package com.example.twinsta.domain.neo4j;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;

@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UserNeo {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String email;

//	@JsonIgnoreProperties("subscribers")
	@Relationship(type = "FOLLOWS")
	public Set<UserNeo> subscriptions;

	public void follows(UserNeo userToFollow) {
		if (isNull(subscriptions)){
			subscriptions = new HashSet<>();
		}
		subscriptions.add(userToFollow);
	}

//	@JsonIgnoreProperties("subscriptions")
//	@Relationship(type = "IS FOLLOWED BY", direction = Relationship.INCOMING)
//	private List<User> subscribers = new ArrayList<>();

}
