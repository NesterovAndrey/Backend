package com.backend.domain.authenticationUser.profile;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Vector;

public class UserProfile extends User {
    private Long id;
    private String principal;
    private String username;
    public UserProfile()
    {
        super(" "," ", new Vector<>());
        principal="NO_NAME";

    }
    public UserProfile(String username,String principal, Collection<? extends GrantedAuthority> authorities) {
        super(username,"Not defined", authorities);
        this.principal=principal;
        this.username=username;
    }

    public UserProfile(String username,String principal,boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username,"Not defined", enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.principal=principal;
        this.username=username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username)
    {
        this.username=username;
    }
}