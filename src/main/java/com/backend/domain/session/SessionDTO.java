package com.backend.domain.session;

import com.backend.domain.IEntityDTO;

public class SessionDTO implements IEntityDTO<Long> {
    Long id;
    String appID;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppID() {
        return this.appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

}
