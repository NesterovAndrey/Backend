package com.backend.web.rest.controller.session;

import com.backend.domain.application.Application;
import com.backend.domain.application.applicationInstall.IApplicationInstall;
import com.backend.service.install.IInstallService;
import com.backend.service.session.ISessionService;
import com.backend.domain.session.Session;
import com.backend.domain.session.SessionDTO;
import utils.mapping.IMapperUtil;
import com.backend.web.validation.IResourceValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/public/application/{appID}/install/{installID}/session")
public class SessionPublicController {

    @Autowired
    private IResourceValidator<Application,String> appValidator;
    @Autowired
    private IResourceValidator<IApplicationInstall,String> installValidator;
    @Autowired
    private IInstallService installService;
    @Autowired
    private ISessionService sessionService;
    @Autowired
    private IMapperUtil mapperUtil;

    @RequestMapping(method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public ResponseEntity<SessionDTO> createSession(@PathVariable String appID, @PathVariable String installID, @RequestBody SessionDTO sessionDTO) {
        appValidator.validate(appID);
        installValidator.validate(installID);

        sessionDTO.setAppID(appID);

        IApplicationInstall install=installService.findByID(installID);
        Session session= mapperUtil.map(sessionDTO,Session.class);
        Logger logger= LoggerFactory.getLogger(this.getClass());
        logger.info("SESS "+session+" "+sessionDTO+" "+mapperUtil);
        session.setInstall(install);
        return new ResponseEntity<>(mapperUtil.map(sessionService.createSession(session),SessionDTO.class),
                HttpStatus.CREATED);
    }


}
