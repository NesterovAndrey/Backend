package com.backend.web.rest.controller.event;

import com.backend.domain.event.Event;
import com.backend.domain.event.EventDTO;
import com.backend.service.event.IEventService;
import com.backend.service.session.ISessionService;
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
@RequestMapping(value = "/private/application/{appID}/event")
public class EventPrivateController {
    @Autowired
    private IResourceValidator<Event,Long> eventValidator;
    @Autowired
    private IEventService eventService;
    @Autowired
    private ISessionService sessionService;
    @Autowired
    private IMapperUtil mapperUtil;
    @RequestMapping(method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userRoleSecurityService.checkUserRole(#principal,#appID,T(com.backend.domain.application.roles.Role).ADMIN)")
    public ResponseEntity<Collection<EventDTO>> getAllEvent(Principal principal) {
        return new ResponseEntity<>(mapperUtil.map(eventService.getAllEvents(),EventDTO.class), HttpStatus.OK);
    }
    @RequestMapping(value = "/{eventID}", method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userRoleSecurityService.checkUserRole(#principal,#appID,T(com.backend.domain.application.roles.Role).ADMIN)")
    public  ResponseEntity<EventDTO> getEvent(@PathVariable("eventID") Long eventID,Principal principal) {

        eventValidator.validate(eventID);
        return new ResponseEntity<>(mapperUtil.map(eventService.findByID(eventID),EventDTO.class), HttpStatus.OK);
    }
    @RequestMapping(value = "/{eventID}", method = RequestMethod.DELETE,produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userRoleSecurityService.checkUserRole(#principal,#appID,T(com.backend.domain.application.roles.Role).OWNER)")
    public ResponseEntity<EventDTO> deleteEvent(Principal principal,@PathVariable("eventID") Long eventID) {
             eventValidator.validate(eventID);
            return new ResponseEntity<>(mapperUtil.map(eventService.deleteEvent(eventID),EventDTO.class), HttpStatus.OK);
    }
    @RequestMapping( method = RequestMethod.DELETE,produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userRoleSecurityService.checkUserRole(#principal,#appID,T(com.backend.domain.application.roles.Role).OWNER)")
    public ResponseEntity<Collection<EventDTO>> deleteAllEvents(Principal principal) {
            return new ResponseEntity<>(mapperUtil.map(eventService.deleteAll(),EventDTO.class), HttpStatus.OK);
    }

}
