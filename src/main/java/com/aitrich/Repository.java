package com.aitrich;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Repository implements PanacheRepository<UserEntity> {

}
