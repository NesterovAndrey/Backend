package com.backend.web.rest.exception;

public abstract class BaseRestException {
    protected int status;
    protected int code;
    protected String message;
    protected String developerMessage;

    public int getStatus()
    {
        return status;
    }
    public int getCode()
    {
        return code;
    }
    public String getMessage()
    {
        return message;
    }
    public  String getDeveloperMessage()
    {
        return developerMessage;
    }
}
