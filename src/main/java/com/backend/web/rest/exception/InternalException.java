package com.backend.web.rest.exception;


import utils.message.MessageItem;

public class InternalException extends DeveloperMessageException {
    public InternalException(MessageItem messageItem)
    {
        super(messageItem);
    }
}
