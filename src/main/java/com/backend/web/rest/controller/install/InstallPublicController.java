package com.backend.web.rest.controller.install;


import com.backend.domain.application.Application;
import com.backend.service.app.IAppService;
import com.backend.domain.application.applicationInstall.*;
import utils.mapping.IMapperUtil;
import com.backend.web.validation.IResourceValidator;
import com.backend.service.install.IInstallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/public/application/{appID}/install")
public class InstallPublicController {

    @Autowired
    private IResourceValidator<Application,String> appValidator;
    @Autowired
    private IInstallService installService;
    @Autowired
    private IAppService appService;
    @Autowired
    private IMapperUtil mapperUtil;


    @RequestMapping(method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public ResponseEntity<ApplicationInstallDTO> initializeInstall(@PathVariable String appID, @RequestBody ApllicationInstallDataDTO apllicationInstallDataDTO) {

        appValidator.validate(appID);
        IApplicationInstall install=new ApplicationInstall(mapperUtil.map(apllicationInstallDataDTO,IApplicationInstallData.class));
        install.setApp(appService.findByID(appID));
        install=installService.saveInstall(install);
        return new ResponseEntity<>(mapperUtil.map(install,ApplicationInstallDTO.class), HttpStatus.CREATED);
    }

}
