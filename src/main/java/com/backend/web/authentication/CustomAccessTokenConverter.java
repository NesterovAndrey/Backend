package com.backend.web.authentication;

import com.backend.service.userProfile.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.Map;

public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

    @Autowired
    IUserProfileService profileService;

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
            Authentication auth= super.extractAuthentication(map);
            return (OAuth2Authentication) auth;
    }
}
