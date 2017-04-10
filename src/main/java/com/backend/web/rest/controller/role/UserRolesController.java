package com.backend.web.rest.controller.role;

import com.backend.domain.application.ApplicationDTO;
import com.backend.domain.application.roles.UserRole;
import com.backend.domain.application.roles.UserRoleDTO;
import com.backend.domain.authenticationUser.profile.UserProfile;
import com.backend.service.role.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utils.mapping.IMapperUtil;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping(value = "/private/roles")
public class UserRolesController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMapperUtil mapperUtil;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<UserRoleDTO>> getRoles(Principal principal)
    {
        AbstractAuthenticationToken auth=(AbstractAuthenticationToken) principal;
        UserProfile user=(UserProfile)auth.getPrincipal();
        Collection<UserRole> roles=roleService.findAllByProfile(user);
        Logger logger= LoggerFactory.getLogger(this.getClass());
        return new ResponseEntity<Collection<UserRoleDTO>>(mapperUtil.map(roles,UserRoleDTO.class), HttpStatus.OK);
    }
}
