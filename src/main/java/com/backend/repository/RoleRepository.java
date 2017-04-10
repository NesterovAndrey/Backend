package com.backend.repository;

import com.backend.domain.application.Application;
import com.backend.domain.application.roles.UserRole;

import com.backend.domain.authenticationUser.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RoleRepository extends JpaRepository<UserRole,Long> {
    UserRole findByProfileAndApp(UserProfile profile, Application app);
    Collection<UserRole> findByProfile(UserProfile profile);
}
