package com.backend.service.app;

import com.backend.domain.application.Application;
import com.backend.domain.authenticationUser.profile.UserProfile;
import utils.uid.AppID;
import com.backend.repository.AppRepository;
import utils.precondition.IValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Date;

public class AppService implements IAppService {
    @Autowired
    private AppRepository appRepository;
    @Autowired
    private IValidator validator;
    public Application createApp(Application app)
    {
        validator.notNull(app,"Application must be not null");
        validator.notNull(app.getName(),"Application name must be not null");
        validator.isTrue(!app.getName().isEmpty(),"Application name must be not empty");

        app.setDateAdded(new Date());
        app.setPrivateKey(new AppID(app).toString());
        app=appRepository.save(app);
        return app;
    }
    public Application findByID(String id)
    {
        validator.notNull(id,"Application id must be not null");
        validator.isTrue(!id.isEmpty(),"Application ID must be not empty");
        Application app =appRepository.findOne(id);
        return app;
    }

    public Application deleteApp(String id) {
        validator.notNull(id,"Application id must be not null");
        validator.isTrue(!id.isEmpty(),"Application ID must be not empty");
        Application app = findByID(id);
        appRepository.delete(app);
        return app;
    }

    @Override
    public Collection<Application> findAllByUser(UserProfile userProfile) {
        validator.notNull(userProfile,"Userprofile must be not null");
        return appRepository.findAllByOwner(userProfile);
    }

    @Override
    public Collection<Application> getAll() {
        return appRepository.findAll();
    }

}
