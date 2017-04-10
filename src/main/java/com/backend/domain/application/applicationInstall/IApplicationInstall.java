package com.backend.domain.application.applicationInstall;

import com.backend.domain.application.Application;

import java.util.Date;

public interface IApplicationInstall {
    String getId();
    void setId(String id);
    Date getInstallDate();
    void setInstallDate(Date installDate);
    Application getApp();
    void setApp(Application app);
    IApplicationInstallData getInstallData();
    void setInstallData(IApplicationInstallData installData);
}
