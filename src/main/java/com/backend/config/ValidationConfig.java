package com.backend.config;

import com.backend.domain.application.Application;
import com.backend.service.app.IAppService;
import com.backend.domain.application.applicationInstall.IApplicationInstall;
import com.backend.service.install.IInstallService;
import com.backend.service.user.IUserService;
import com.backend.domain.authenticationUser.User;
import com.backend.domain.event.Event;
import com.backend.service.event.IEventService;
import com.backend.service.session.ISessionService;
import com.backend.domain.session.Session;
import com.backend.web.validation.IResourceValidator;
import com.backend.web.validation.ResourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {

    @Autowired
    private IAppService appService;
    @Autowired
    private IEventService eventService;
    @Autowired
    private ISessionService sessionService;
    @Autowired
    private IInstallService installService;
    @Autowired
    private IUserService userService;



    @Bean
    public IResourceValidator<Application,String> appResourceValidator()
    {
        return new ResourceValidator<Application,String>("exception.app.not_found_id",appService);
    }
    @Bean
    public IResourceValidator<Event,Long> eventResourceValidator()
    {
        return new ResourceValidator<Event,Long>("exception.event.not_found_id",eventService);
    }
    @Bean
    public IResourceValidator<Session,Long> sessionResourceValidator()
    {
        return new ResourceValidator<Session,Long>("exception.session.not_found_id",sessionService);
    }
    @Bean
    public IResourceValidator<IApplicationInstall,String> installResourceValidator()
    {
        return new ResourceValidator<IApplicationInstall,String>("exception.install.not_found_id",installService);
    }
    @Bean
    public IResourceValidator<User,String> userResourceValidator()
    {
        return new ResourceValidator<User,String>("exception.user.not_found_id",userService);
    }
}
