package org.example.msuserjwt.Repository;

import org.example.msuserjwt.entity.UserJwt;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserJwtRepository extends CrudRepository<UserJwt, Integer> {
    Optional<UserJwt> findByEmail(String email);
}
