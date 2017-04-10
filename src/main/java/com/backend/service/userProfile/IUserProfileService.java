package com.backend.service.userProfile;

import com.backend.domain.authenticationUser.profile.UserProfile;

import java.util.List;


public interface IUserProfileService {
    UserProfile getProfileByID(Long id);
    UserProfile getProfileByPrincipal(String principal);
    UserProfile save(UserProfile profile);
    List<UserProfile> getAll();
}
