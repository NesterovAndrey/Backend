package com.backend.domain.event;

import com.backend.domain.IEntity;
import com.backend.domain.application.Application;
import com.backend.domain.event.data.Data;
import com.backend.domain.session.Session;

import java.util.List;

public class Event implements IEntity<Long> {

    private Long id;
    private String name;
    private Application app;
    private Session session;
    private List<Data> data;


    public Event()
    {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
