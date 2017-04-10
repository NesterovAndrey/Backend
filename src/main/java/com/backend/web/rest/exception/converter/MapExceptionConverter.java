package com.backend.web.rest.exception.converter;

import com.backend.web.rest.exception.RestException;

import java.util.HashMap;
import java.util.Map;

public class MapExceptionConverter implements ExceptionConverter<Map<String,String>,RestException> {
    private static final String DEFAULT_STATUS_KEY = "status";
    private static final String DEFAULT_CODE_KEY = "code";
    private static final String DEFAULT_MESSAGE_KEY = "message";
    private static final String DEFAULT_DEVELOPER_MESSAGE_KEY = "developerMessage";


    private String statusKey = DEFAULT_STATUS_KEY;
    private String codeKey = DEFAULT_CODE_KEY;
    private String messageKey = DEFAULT_MESSAGE_KEY;
    private String developerMessageKey = DEFAULT_DEVELOPER_MESSAGE_KEY;

    @Override
    public Map<String, String> convert(RestException exception) {
        Map<String, String> m = new HashMap<>();

        m.put(getStatusKey(), Integer.toString(exception.getStatus()));

        int code = exception.getCode();
        if (code > 0) {
            m.put(getCodeKey(), Integer.toString(code));
        }

        String message = exception.getMessage();
        if (message != null) {
            m.put(getMessageKey(), message);
        }

        String devMsg = exception.getDeveloperMessage();
        if (devMsg != null) {
            m.put(getDeveloperMessageKey(), devMsg);
        }

        return m;
    }

    public String getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(String statusKey) {
        this.statusKey = statusKey;
    }

    public String getCodeKey() {
        return codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getDeveloperMessageKey() {
        return developerMessageKey;
    }

    public void setDeveloperMessageKey(String developerMessageKey) {
        this.developerMessageKey = developerMessageKey;
    }
}
