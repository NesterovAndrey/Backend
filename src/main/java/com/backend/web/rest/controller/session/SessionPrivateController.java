package com.backend.web.rest.controller.session;

import com.backend.domain.application.Application;
import com.backend.service.app.IAppService;
import com.backend.service.session.ISessionService;
import com.backend.domain.session.SessionDTO;
import utils.mapping.IMapperUtil;
import com.backend.web.validation.IResourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;


@RestController
@RequestMapping(value = "/private/application/{appID}/session")
public class SessionPrivateController {
    @Autowired
    private IResourceValidator<Application,String> appValidator;
    @Autowired
    private ISessionService sessionService;
    @Autowired
    private IAppService appService;
    @Autowired
    private IMapperUtil mapperUtil;

    @RequestMapping(method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userRoleSecurityService.checkUserRole(#principal,#appID,T(com.backend.domain.application.roles.Role).OWNER)")
    public ResponseEntity<Collection<SessionDTO>> getAllSessions(@PathVariable("appID") String appID) {
        appValidator.validate(appID);
        return new ResponseEntity<>(mapperUtil.map(sessionService.findByApp(appService.findByID(appID)),SessionDTO.class), HttpStatus.OK);
    }
    @RequestMapping(value = "/{sessionID}", method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userRoleSecurityService.checkUserRole(#principal,#appID,T(com.backend.domain.application.roles.Role).ADMIN)")
    public ResponseEntity<SessionDTO> getSession(@PathVariable("sessionID") Long sessionID,@PathVariable String appID, Principal principal) {
        appValidator.validate(appID);
        return new ResponseEntity<>(mapperUtil.map(sessionService.findByID(sessionID),SessionDTO.class), HttpStatus.OK);
    }
    @RequestMapping(value = "/{sessionID}", method = RequestMethod.DELETE,produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userRoleSecurityService.checkUserRole(#principal,#appID,T(com.backend.domain.application.roles.Role).OWNER)")
    public ResponseEntity<SessionDTO> deleteSession(@PathVariable("sessionID") Long sessionID) {
            return new ResponseEntity<>(mapperUtil.map(sessionService.deleteSession(sessionID),SessionDTO.class), HttpStatus.OK);
    }



}
