package com.backend.web.rest.controller.application;

import com.backend.domain.application.Application;
import com.backend.domain.application.ApplicationDTO;
import com.backend.service.app.IAppService;
import com.backend.service.role.IRoleService;
import com.backend.domain.application.roles.Role;
import com.backend.domain.application.roles.UserRole;
import com.backend.domain.authenticationUser.profile.UserProfile;
import utils.mapping.IMapperUtil;
import com.backend.web.validation.IResourceValidator;
import com.backend.web.rest.exception.ValidationException;
import utils.message.MessageItemImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;

@RestController
@RequestMapping(path="/private/application")
public class ApplicationController {

    @Autowired
    private IResourceValidator<Application,String> appValidator;
    @Autowired
    private IAppService appService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMapperUtil mapperUtil;
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ApplicationDTO> createApp(@Valid @RequestBody ApplicationDTO appDTO, BindingResult bindingResult, @AuthenticationPrincipal Principal principal)
    {
        checkErrors(bindingResult);
        OAuth2Authentication auth=(OAuth2Authentication) principal;
        UserProfile user=(UserProfile)auth.getPrincipal();
        UserRole role=new UserRole();
        Application app= mapperUtil.map(appDTO,Application.class);
        app.setOwner(user);
        app=appService.createApp(app);
        role.setApp(app);
        role.setProfile(user);
        roleService.createRole(user,app, Role.OWNER);
        return new ResponseEntity<>(mapperUtil.map(app,ApplicationDTO.class), HttpStatus.CREATED);
    }
    @RequestMapping(method=RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Collection<ApplicationDTO>> getAllApps(Principal principal)
    {
        return new ResponseEntity<>(mapperUtil.map(appService.getAll(),ApplicationDTO.class), HttpStatus.OK);
    }
    @RequestMapping(path="/{appID}",method=RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userRoleSecurityService.checkUserRole(#principal,#appID,T(com.backend.domain.application.roles.Role).USER)")
    public ResponseEntity<ApplicationDTO> getApp(Principal principal, @PathVariable("appID") String appID)
    {
        appValidator.validate(appID);
        return new ResponseEntity<>(mapperUtil.map(appService.findByID(appID),ApplicationDTO.class), HttpStatus.OK);
    }
    @RequestMapping(path = "/{appID}",method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userRoleSecurityService.checkUserRole(#principal,#appID,T(com.backend.domain.application.roles.Role).OWNER)")
    public ResponseEntity<ApplicationDTO> deleteApp(Principal principal, @PathVariable String appID)
    {
        appValidator.validate(appID);
        return new ResponseEntity<>(mapperUtil.map(appService.findByID(appID),ApplicationDTO.class), HttpStatus.OK);
    }
    private void checkErrors(BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            throw new ValidationException(new MessageItemImpl("exception.validation.fields",new Object[]{processBindingFields(bindingResult)}));
        }
    }
    private String processBindingFields(BindingResult bindingResult)
    {
        List<String> errors= new ArrayList<>();
        for(FieldError error:bindingResult.getFieldErrors())
        {
            errors.add(new Formatter().format("%s:%s",error.getField(),error.getDefaultMessage()).toString());
        }
        return StringUtils.join(errors,"\n");
    }


}
