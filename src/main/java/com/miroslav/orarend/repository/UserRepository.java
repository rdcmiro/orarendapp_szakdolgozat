package com.miroslav.orarend.repository;

import com.miroslav.orarend.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(@Param("email") String email);

    boolean existsByEmail(@Param("email") String email);

    boolean existsByUsername(@Param("username") String username);


}
