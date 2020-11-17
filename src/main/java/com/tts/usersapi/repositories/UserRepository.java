package com.tts.usersapi.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

import com.tts.usersapi.models.UserV2;

@Repository
public interface UserRepository extends CrudRepository<UserV2, Long> {
    List<UserV2> findByState(String state);
}
