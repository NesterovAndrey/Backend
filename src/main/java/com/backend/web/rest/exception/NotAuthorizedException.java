package com.backend.web.rest.exception;


import utils.message.MessageItem;

public class NotAuthorizedException extends DeveloperMessageException {
    public NotAuthorizedException(MessageItem messageItem)
    {
        super(messageItem);
    }
}
