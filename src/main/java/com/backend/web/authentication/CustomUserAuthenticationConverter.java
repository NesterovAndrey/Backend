package com.backend.web.authentication;

import com.backend.service.userProfile.IUserProfileService;
import com.backend.domain.authenticationUser.profile.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.Map;

public class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {
    @Autowired
    private IUserProfileService profileService;

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        Authentication auth = super.extractAuthentication(map);

        UserProfile userProfile;

            userProfile = profileService.getProfileByPrincipal((String) auth.getPrincipal());

            userProfile=userProfile==null?
                            profileService.save(new UserProfile((String)auth.getPrincipal(),(String)auth.getPrincipal(),auth.getAuthorities()))
                            :userProfile;

        Logger logger= LoggerFactory.getLogger(this.getClass());
        logger.info("USERPROFILE "+userProfile);
        return new UsernamePasswordAuthenticationToken(userProfile,auth.getCredentials(),auth.getAuthorities());
    }
}
