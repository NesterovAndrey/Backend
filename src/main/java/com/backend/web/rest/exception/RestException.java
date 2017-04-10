package com.backend.web.rest.exception;

public class RestException extends BaseRestException {

    public RestException() {
    }

    public RestException(RestExceptionBuilder builder) {

        this.code = builder.getCode();
        this.status = builder.getStatus();
        this.message = builder.getMessage();
        this.developerMessage = builder.getDeveloperMessage();
    }


}