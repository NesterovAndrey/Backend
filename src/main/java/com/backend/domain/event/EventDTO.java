package com.backend.domain.event;

import com.backend.domain.event.data.DataDTO;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class EventDTO {
    private Long id;
    @NotEmpty
    private String name;
    private Long sessionID;
    private String appID;

    private List<DataDTO> dataList;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSessionID() {
        return sessionID;
    }

    public void setSessionID(Long sessionID) {
        this.sessionID = sessionID;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public List<DataDTO> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataDTO> dataList) {
        this.dataList = dataList;
    }
}
