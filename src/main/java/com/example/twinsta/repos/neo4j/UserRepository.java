package com.example.twinsta.repos.neo4j;

import com.example.twinsta.domain.neo4j.UserNeo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends Neo4jRepository<UserNeo, Long> {

	@Query("MATCH (user:UserNeo { name: $name })<-[:FOLLOWS]-(follower)\n" +
			"RETURN follower")
	List<UserNeo> getUserSubscribers(@Param("name") String name);

	@Query("MATCH (:UserNeo { name: $name })-->(userToFollow)\n" +
			"RETURN userToFollow")
	List<UserNeo> getUserSubscriptions(@Param("name") String name);

	UserNeo findByName(String name);

}
