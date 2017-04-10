package com.backend.domain.application.roles;

import com.backend.domain.application.ApplicationDTO;
import com.backend.domain.authenticationUser.profile.UserProfileDTO;

public class UserRoleDTO {
    private UserProfileDTO profile;
    private ApplicationDTO app;
    private String role;

    public UserProfileDTO getProfile() {
        return profile;
    }

    public void setProfile(UserProfileDTO profile) {
        this.profile = profile;
    }

    public ApplicationDTO getApp() {
        return app;
    }

    public void setApp(ApplicationDTO app) {
        this.app = app;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
