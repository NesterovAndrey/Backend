package com.backend.repository;

import com.backend.domain.authenticationUser.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    User findUserByUsername(String name);
}
