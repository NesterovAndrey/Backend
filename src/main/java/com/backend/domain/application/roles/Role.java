package com.backend.domain.application.roles;

public enum Role {
    OWNER(0),ADMIN(1),USER(2);

    public final int priority;
     Role(int priority)
    {
        this.priority=priority;
    }
}
