package com.backend.domain.authenticationUser;

import com.backend.web.validation.UsernameExists;

import javax.validation.constraints.Pattern;


public class UserDTO {
    private static final String PATTERN =
            "^[a-z0-9_-]{3,15}$";

    private String id;

    @Pattern(regexp=PATTERN,message = "Wrong username")
    @UsernameExists(message = "Username exists")
    private String username;
    @Pattern(regexp=PATTERN,message = "Wrong password")
    private String password;
    private String role="USER";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
