package com.backend.service.app;

import com.backend.domain.application.Application;
import com.backend.domain.authenticationUser.profile.UserProfile;
import com.backend.service.IBaseService;

import java.util.Collection;


public interface IAppService extends IBaseService<Application,String> {
    Application createApp(Application app);
    Application deleteApp(String appID);
    Collection<Application> findAllByUser(UserProfile profile);
    Collection<Application> getAll();
}
