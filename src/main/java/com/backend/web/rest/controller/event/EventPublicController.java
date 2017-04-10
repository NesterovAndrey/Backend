package com.backend.web.rest.controller.event;

import com.backend.domain.application.Application;
import com.backend.domain.event.Event;
import com.backend.domain.event.EventDTO;
import com.backend.service.event.IEventService;
import com.backend.domain.session.Session;
import utils.mapping.IMapperUtil;
import com.backend.web.validation.IResourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/public/application/{appID}/session/{sessionID}/event")
public class EventPublicController {

    @Autowired
    private IResourceValidator<Application,String> appValidator;
    @Autowired
    private IResourceValidator<Session,Long> sessionValidator;
    @Autowired
    private IEventService eventService;
    @Autowired
    private IMapperUtil mapperUtil;
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO, BindingResult bindingResult,@PathVariable("appID") String appID, @PathVariable("sessionID") Long sessionID) {
        appValidator.validate(appID);
        sessionValidator.validate(sessionID);

        eventDTO.setSessionID(sessionID);
        eventDTO.setAppID(appID);
        Event event =eventService.createEvent(mapperUtil.map(eventDTO,Event.class));
        return new ResponseEntity<>(mapperUtil.map(event,EventDTO.class), HttpStatus.CREATED);
    }


}
