package com.backend.web.authentication.publicApi;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class PublicApiAuthenticationToken extends AbstractAuthenticationToken {

    private PublicApiAuthenticationData authenticationData;

    public PublicApiAuthenticationToken(PublicApiAuthenticationData authenticationData)
    {
        super(null);
        this.authenticationData=authenticationData;

        setAuthenticated(false);
    }
    public PublicApiAuthenticationToken(Object principal, Object credentials,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.setAuthenticated(true);
    }
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }
    @Override
    public Object getCredentials() {
        return authenticationData.getSignature();
    }

    @Override
    public Object getPrincipal() {
        return authenticationData.getAppID();
    }

}
