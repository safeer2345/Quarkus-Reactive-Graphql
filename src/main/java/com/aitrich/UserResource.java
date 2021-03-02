package com.aitrich;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import io.smallrye.mutiny.Uni;

@GraphQLApi
public class UserResource {

	@Inject
	UserService userService;

	@Inject
	Repository repo;

	@Query("getAllUser")
	public Uni<List<UserEntity>> getAllUser() {
		return userService.getAllUser();
	}

	@Query
	@Transactional
	public Uni<UserEntity> getUser(long id) {

		return userService.getUser(id);
	}

	@Mutation
	public Uni<UserEntity> saveUser(UserEntity userEntity) {
	
		return userService.saveUser(userEntity);
	}

	@Mutation
	public Uni<UserEntity> updateUser(UserEntity userEntity) {
		
		return userService.updateUser(userEntity);
	}

	@Mutation("deleteUser")
	public Uni<Boolean> deleteUser(@Name(value = "userId") long userId) {

		return userService.deleteUser(userId);
	}
}