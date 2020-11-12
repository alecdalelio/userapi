package com.tts.usersapi.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import com.tts.usersapi.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByState(String state);
}
