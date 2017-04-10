package com.backend.web.authentication.security;

import com.backend.domain.application.Application;
import com.backend.service.app.IAppService;
import com.backend.service.role.IRoleService;
import com.backend.domain.application.roles.Role;
import com.backend.domain.application.roles.UserRole;
import com.backend.domain.authenticationUser.profile.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

@Component("userRoleSecurityService")
public class UserRoleSecurityService {
    @Autowired
    private IAppService appService;
    @Autowired
    private IRoleService roleService;

    public boolean checkUserRole(OAuth2Authentication auth, String appID, Role minRequiredRole)
    {
        Logger logger= LoggerFactory.getLogger(this.getClass());
        logger.info("AUTH IS NULL "+(auth==null));
        assert auth != null;
        UserProfile profile=(UserProfile)auth.getPrincipal();
        Application app=appService.findByID(appID);
        UserRole role=roleService.findByProfileAndApp(profile,app);

        logger.info("ROLE USER "+role.getRole().priority+" ROLE NEED "+minRequiredRole.priority);
        return role.getRole().priority<=minRequiredRole.priority;
    }
}
