package com.backend.web.authentication.security;

import com.backend.domain.application.Application;
import com.backend.service.app.IAppService;
import com.backend.service.user.IUserService;
import com.backend.web.authentication.JpaUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("webSecurityService")
public class WebSecurity {
    @Autowired
    private IAppService appService;
    @Autowired
    private IUserService userService;

    public boolean checkOwner(Authentication authentication,String appID)
    {
        JpaUserDetails jpaUserDetails=(JpaUserDetails)authentication.getPrincipal();
        Application app=appService.findByID(appID);
       return app.getOwner().getId().equals(jpaUserDetails.getId());

    }
}
