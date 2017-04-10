package com.backend.domain.application;

import com.backend.domain.IEntity;
import com.backend.domain.authenticationUser.profile.UserProfile;

import java.util.Date;

public interface Application extends IEntity<String> {

    String getId();
    void setId(String id);

    UserProfile getOwner();
    void setOwner(UserProfile owner);

    String getPrivateKey();
    void setPrivateKey(String privateKey);

    String getName();
    void setName(String title);

    Date getDateAdded();
    void setDateAdded(Date date_added);

}
