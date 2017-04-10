package com.backend.domain.application.applicationInstall;

public class ApplicationInstallDTO {
    private String id;
    private ApllicationInstallDataDTO data;
    private String appID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ApllicationInstallDataDTO getData() {
        return data;
    }

    public void setData(ApllicationInstallDataDTO data) {
        this.data = data;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }
}
