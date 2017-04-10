package com.backend.repository;

import com.backend.domain.authenticationUser.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<UserProfile,Long> {
    UserProfile findByPrincipal(String principal);
}
