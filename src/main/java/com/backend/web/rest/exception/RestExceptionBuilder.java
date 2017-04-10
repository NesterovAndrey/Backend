package com.backend.web.rest.exception;

public class RestExceptionBuilder extends BaseRestException {
    public RestExceptionBuilder() {
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
        setSameIfNull(this.message, this.developerMessage);

    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
        setSameIfNull(this.developerMessage, this.message);
    }

    private void setSameIfNull(String source, String target) {
        if (target == null && source != null) {
            target = source;
        }
    }


}