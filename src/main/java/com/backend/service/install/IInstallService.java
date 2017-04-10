package com.backend.service.install;

import com.backend.domain.application.applicationInstall.IApplicationInstall;
import com.backend.service.IBaseService;

import java.util.Collection;

public interface IInstallService extends IBaseService<IApplicationInstall,String>{
    IApplicationInstall saveInstall(IApplicationInstall install);
    Collection<IApplicationInstall> findAll();
}
