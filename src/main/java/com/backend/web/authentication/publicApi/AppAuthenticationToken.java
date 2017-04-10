package com.backend.web.authentication.publicApi;

import com.backend.domain.application.Application;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class AppAuthenticationToken extends AbstractAuthenticationToken {


    private final Application app;
    public AppAuthenticationToken(Application app)
    {
        super(null);
        this.app=app;

        setAuthenticated(false);
    }
    public AppAuthenticationToken(Application app, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.app=app;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return app.getPrivateKey();
    }

    @Override
    public Object getPrincipal() {
        return app.getId();
    }
}
