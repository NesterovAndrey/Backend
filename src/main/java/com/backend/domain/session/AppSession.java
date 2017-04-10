package com.backend.domain.session;

import com.backend.domain.application.Application;
import com.backend.domain.application.applicationInstall.IApplicationInstall;
import utils.IByteSource;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Date;

@JsonTypeName("application")
public class AppSession implements Session, IByteSource {
    private Long id;
    private Application app;
    private IApplicationInstall install;
    private Date startTime = new Date();
    private Date endTime;

    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long value) {
        this.id = value;

    }

    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public IApplicationInstall getInstall() {
        return install;
    }

    @Override
    public void setInstall(IApplicationInstall install) {
        this.install = install;
    }

    @Override
    public byte[] asByteArray() {
        return app.toString().concat(startTime.toString()).getBytes();
    }

}
