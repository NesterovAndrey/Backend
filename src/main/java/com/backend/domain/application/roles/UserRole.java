package com.backend.domain.application.roles;

import com.backend.domain.application.Application;
import com.backend.domain.authenticationUser.profile.UserProfile;

public class UserRole {

    private Long id;
    private UserProfile profile;
    private Application app;
    private Role role;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserProfile getProfile() {
        return profile;
    }


    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
    }


}
