package com.backend.web.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

public class JpaUserDetails extends User implements Serializable {
    private String id;

    public JpaUserDetails()
    {
        super("test","test", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
    public JpaUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public JpaUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
    public JpaUserDetails(com.backend.domain.authenticationUser.User user)
    {
        this(user.getUsername(),user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
        this.id=user.getId();
    }
    public String getId() {
        return id;
    }


}
