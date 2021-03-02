package com.aitrich;

import java.util.List;
import java.util.function.Function;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.hibernate.reactive.mutiny.Mutiny;

import io.smallrye.mutiny.Uni;

@ApplicationScoped
@Transactional
public class UserService {
	
	@Inject
	Repository repository;
	
	@Inject
	Mutiny.Session session;


	public Uni<List<UserEntity>> getAllUser() {
		return repository.findAll().list();
	}


	public Uni<UserEntity> getUser(long id) {
		return session.find(UserEntity.class, id);
	}


	@Transactional
	public Uni<UserEntity> saveUser(UserEntity userEntity) {
		
		return repository.persist(userEntity).chain(repository :: flush)
				.onItem().transform(ignore -> userEntity);
	}
	

	@Transactional
	public Uni<Boolean> deleteUser(long userId) {

		return repository.deleteById(userId).chain(repository :: flush)
				.onItem().transform(ignore -> true);
	}
	
	@Transactional
	public Uni<UserEntity> updateUser(UserEntity userEntity) {
		
		
		Function<UserEntity,Uni<? extends UserEntity>> update = entity ->
		{
			entity.setName(userEntity.getName());
			return repository.flush().onItem().transform(ignore -> entity);
		};
		
		return repository.findById(userEntity.getId()).onItem().ifNotNull().transformToUni(update);
	
		
	}

}
