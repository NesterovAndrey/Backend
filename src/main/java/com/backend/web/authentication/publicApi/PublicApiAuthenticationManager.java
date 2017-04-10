package com.backend.web.authentication.publicApi;


import com.backend.service.app.IAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

import java.util.List;


public class PublicApiAuthenticationManager extends ProviderManager {

    @Autowired
    private IAppService appService;

    public PublicApiAuthenticationManager(List<AuthenticationProvider> providers) {
        super(providers);
    }

    public PublicApiAuthenticationManager(List<AuthenticationProvider> providers, AuthenticationManager parent) {
        super(providers, parent);
    }

}
