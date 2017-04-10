package com.backend.domain.application;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ApplicationDTO {
    private static final String PATTERN =
            "^[A-Za-z0-9_-]{1,42}$";

    private String id;
    @NotNull
    @Pattern(regexp=PATTERN,message = "Wrong username")
    private String name;
    private Long ownerID;
    private String privateKey;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public Long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Long ownerID) {
        this.ownerID = ownerID;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
