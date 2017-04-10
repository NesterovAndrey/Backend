package com.backend.service.role;

import com.backend.domain.application.Application;
import com.backend.domain.application.roles.Role;
import com.backend.domain.application.roles.UserRole;
import com.backend.domain.authenticationUser.profile.UserProfile;

import java.util.Collection;

public interface IRoleService {
    UserRole findByProfileAndApp(UserProfile userProfile, Application app);
    UserRole createRole(UserProfile userProfile, Application app, Role role);
    Collection<UserRole> findAllByProfile(UserProfile userProfile);
}
