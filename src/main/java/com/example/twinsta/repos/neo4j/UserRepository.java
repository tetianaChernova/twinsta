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

	@Query("MATCH (a:UserNeo),(b:UserNeo) WHERE a.name = $username AND b.name = $userToFollow " +
			"CREATE (a)-[r:FOLLOWS { name: a.name + '<->' + b.name }]->(b) RETURN type(r), r.name")
	void subscribe(@Param("username") String username, @Param("userToFollow") String userToFollow);

	@Query("MATCH (:UserNeo {name: $username})-[r:FOLLOWS]-(:UserNeo {name: $userToUnFollow}) DELETE r")
	void unsubscribe(@Param("username") String username, @Param("userToUnFollow") String userToUnFollow);

	UserNeo getUserNeoByName(String name);
}
