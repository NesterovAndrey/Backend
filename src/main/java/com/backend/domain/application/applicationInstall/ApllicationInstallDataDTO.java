package com.backend.domain.application.applicationInstall;

public class ApllicationInstallDataDTO {

    private String clientOS="unknown";
    private String clientOSVersion="0";
    private String clientLocale="unknown";

    public String getClientOS() {
        return clientOS;
    }
    public void setClientOS(String os)
    {
        this.clientOS=os;
    }
    public String getClientOSVersion() {
        return clientOSVersion;
    }
    public void setClientOSVersion(String osVersion)
    {
        this.clientOSVersion=osVersion;
    }

    public String getClientLocale() {
        return clientLocale;
    }
    public void setClientLocale(String locale)
    {
        this.clientLocale=locale;
    }

}
