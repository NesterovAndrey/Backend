package com.backend.repository;

import com.backend.domain.application.Application;
import com.backend.domain.authenticationUser.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppRepository extends JpaRepository<Application,String> {

    List<Application> findAllByOwner(UserProfile user);

}
