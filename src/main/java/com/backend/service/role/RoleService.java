package com.backend.service.role;

import com.backend.domain.application.Application;
import com.backend.domain.application.roles.Role;
import com.backend.domain.application.roles.UserRole;
import com.backend.domain.authenticationUser.profile.UserProfile;
import com.backend.repository.RoleRepository;
import utils.precondition.IValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class RoleService implements IRoleService {
    @Autowired
    private IValidator validator;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserRole findByProfileAndApp(UserProfile userProfile, Application app) {
        validator.notNull(app,"Application must be not null");
        validator.notNull(userProfile,"UserProfile must be not null");

        return roleRepository.findByProfileAndApp(userProfile,app);
    }

    @Override
    public UserRole createRole(UserProfile userProfile, Application app, Role role) {
        validator.notNull(app,"Application must be not null");
        validator.notNull(userProfile,"UserProfile must be not null");
        validator.notNull(role,"Role must be not null");

        UserRole userRole=new UserRole();
        userRole.setApp(app);
        userRole.setProfile(userProfile);
        userRole.setRole(role);
        return roleRepository.save(userRole);
    }

    @Override
    public Collection<UserRole> findAllByProfile(UserProfile userProfile) {
        validator.notNull(userProfile,"UserProfile must be not null");
        return roleRepository.findByProfile(userProfile);
    }
}
