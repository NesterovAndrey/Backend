package com.backend.web.rest.exception;


import utils.message.MessageItemImpl;

public class AuthenticationFailed extends DeveloperMessageException {

    public AuthenticationFailed(MessageItemImpl messageItem) {
        super(messageItem);
    }
}
