package com.backend.web.rest.exception;

import utils.message.MessageItem;


public class ValidationException extends DeveloperMessageException {
    public ValidationException(MessageItem messageItem)
    {
        super(messageItem);
    }

}
