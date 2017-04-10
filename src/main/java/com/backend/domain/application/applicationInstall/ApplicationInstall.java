package com.backend.domain.application.applicationInstall;

import com.backend.domain.application.Application;
import utils.IByteSource;

import java.util.Date;

public class ApplicationInstall implements IApplicationInstall, IByteSource {

    private String id;
    private Date installDate;
    private Application app;
    private IApplicationInstallData installData;
    public ApplicationInstall()
    {

    }
    public ApplicationInstall(IApplicationInstallData data)
    {
        setInstallData(data);
    }
    @Override
    public String getId() {
        return this.id;
    }
    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Date getInstallDate() {
        return this.installDate;
    }

    @Override
    public void setInstallDate(Date installDate) {
        this.installDate=installDate;
    }

    @Override
    public IApplicationInstallData getInstallData() {
        return this.installData;
    }

    @Override
    public void setInstallData(IApplicationInstallData installData) {
        this.installData=installData;
    }

    @Override
    public void setApp(Application app) {
        this.app = app;
    }

    @Override
    public Application getApp() {
        return app;
    }

    @Override
    public byte[] asByteArray() {
        return (installData.getClientOS()
                +installData.getClientLocale()
                +installData.getClientOSVersion()
                +app.getName()
                +installDate.toString()
                +Math.random()).getBytes();
    }
}


