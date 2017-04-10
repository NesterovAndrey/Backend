package com.backend.web.rest.controller.metrics;

import com.backend.domain.application.Application;
import com.backend.domain.metrcis.*;
import com.backend.domain.query.NativeQueryEnum;
import com.backend.web.validation.IResourceValidator;
import com.backend.service.metrics.IMetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping(value = "/private/application/{appID}/metrics")
public class MetricsController {

    @Autowired
    private IResourceValidator<Application,String> appValidator;
    @Autowired
    private IMetricsService metricsService;

    Logger logger= LoggerFactory.getLogger(this.getClass());

    private final String fromParam="from";
    private final String toParam="to";
    private final Boolean dateRequired=false;
    private final String dateFromDefault="01-01-2017";
    private final String dateToDefault="12-31-2017";
    private final String datePattern="MM-dd-yyyy";

    private final String dayParam="day";
    private final Boolean dayRequired=false;
    private final String dayDefault="1";

    @RequestMapping(value = "/dau")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userRoleSecurityService.checkUserRole(#principal,#appID,T(com.backend.domain.application.roles.Role).USER)")
    public ResponseEntity<Collection<IMetricData>> getDAU(@RequestParam(value=fromParam,required = false,defaultValue =dateFromDefault) @DateTimeFormat(pattern=datePattern) Date from,
                                                         @RequestParam(value=toParam,required = false,defaultValue = dateToDefault) @DateTimeFormat(pattern=datePattern) Date to,
                                                         @PathVariable String appID, Principal principal)
    {
        appValidator.validate(appID);
        return new ResponseEntity<>(metricsService.getActiveUsers(NativeQueryEnum.DAU,from, to), HttpStatus.OK);
    }
    @RequestMapping(value = "/wau")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userRoleSecurityService.checkUserRole(#principal,#appID,T(com.backend.domain.application.roles.Role).USER)")
    public ResponseEntity<Collection<IMetricData>> getWAU(@RequestParam(value=fromParam,required = false,defaultValue =dateFromDefault) @DateTimeFormat(pattern=datePattern) Date from,
                                                         @RequestParam(value=toParam,required = false,defaultValue = dateToDefault) @DateTimeFormat(pattern=datePattern) Date to,
                                                         @PathVariable String appID, Principal principal)
    {
        appValidator.validate(appID);
        return new ResponseEntity<>(metricsService.getActiveUsers(NativeQueryEnum.WAU,from, to), HttpStatus.OK);
    }
    @RequestMapping(value = "/mau")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userRoleSecurityService.checkUserRole(#principal,#appID,T(com.backend.domain.application.roles.Role).USER)")
    public ResponseEntity<Collection<IMetricData>> getMAU(@RequestParam(value=fromParam,required = false,defaultValue =dateFromDefault) @DateTimeFormat(pattern=datePattern) Date from,
                                                         @RequestParam(value=toParam,required = false,defaultValue = dateToDefault) @DateTimeFormat(pattern=datePattern) Date to,
                                                         @PathVariable String appID, Principal principal)
    {
        appValidator.validate(appID);
        return new ResponseEntity<>(metricsService.getActiveUsers(NativeQueryEnum.MAU,from, to), HttpStatus.OK);
    }
    @RequestMapping(value = "/retention")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userRoleSecurityService.checkUserRole(#principal,#appID,T(com.backend.domain.application.roles.Role).USER)")
    public ResponseEntity<Collection<IMetricData>> getRetention(@RequestParam(value=dayParam,required = false,defaultValue =dayDefault) int day,
                                                               @PathVariable String appID, Principal principal)
    {
        appValidator.validate(appID);
        return new ResponseEntity<>(metricsService.getRetention(NativeQueryEnum.RETENTION,day), HttpStatus.OK);
    }
    @RequestMapping(value = "/retention/new")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userRoleSecurityService.checkUserRole(#principal,#appID,T(com.backend.domain.application.roles.Role).USER)")
    public ResponseEntity<Collection<IMetricData>> getNewUserRetention(@RequestParam(value=dayParam,required = false,defaultValue =dayDefault) int day,
                                                                      @PathVariable String appID, Principal principal)
    {
        appValidator.validate(appID);
        return new ResponseEntity<>(metricsService.getRetention(NativeQueryEnum.NEW_USER_RETENTION,day), HttpStatus.OK);
    }
    @RequestMapping(value = "/retention/existing")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userRoleSecurityService.checkUserRole(#principal,#appID,T(com.backend.domain.application.roles.Role).USER)")
    public ResponseEntity<Collection<IMetricData>> getExistingUserRetention(@RequestParam(value=dayParam,required = false,defaultValue =dayDefault) int day,
                                                                           @PathVariable String appID, Principal principal)
    {
        appValidator.validate(appID);
        return new ResponseEntity<>(metricsService.getRetention(NativeQueryEnum.EXISTING_USER_RETENTION,day), HttpStatus.OK);
    }

}
