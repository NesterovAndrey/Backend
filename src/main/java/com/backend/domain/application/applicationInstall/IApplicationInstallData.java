package com.backend.domain.application.applicationInstall;

public interface IApplicationInstallData {

    Long getId();
    void setId(Long id);
    String getClientOS();
    void setClientOS(String os);
    String getClientOSVersion();
    void setClientOSVersion(String osVersion);
    String getClientLocale();
    void setClientLocale(String locale);
}
