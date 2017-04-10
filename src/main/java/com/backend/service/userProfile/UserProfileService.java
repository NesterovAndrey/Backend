package com.backend.service.userProfile;

import com.backend.domain.authenticationUser.profile.UserProfile;
import com.backend.repository.ProfileRepository;
import utils.precondition.IValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserProfileService implements IUserProfileService {

    @Autowired
    private IValidator validator;
    @Autowired
    private ProfileRepository profileRepository;
    public UserProfile getProfileByID(Long id)
    {
        validator.notNull(id,"Profile ID must be not null");
        validator.isTrue(id>=0,"Profile ID must be positive value");
        UserProfile profile=profileRepository.findOne(id);
        return profile;
    }
    public UserProfile getProfileByPrincipal(String principal)
    {
        validator.notNull(principal,"Principal must be not null");
        validator.isTrue(!principal.isEmpty(),"Principal must be not empty");
        UserProfile profile=profileRepository.findByPrincipal(principal);
        return profile;
    }
    public UserProfile save(UserProfile profile)
    {
        validator.notNull(profile,"Profile must be not null");
        return profileRepository.save(profile);
    }
    public List<UserProfile> getAll()
    {
        return profileRepository.findAll();
    }
}
