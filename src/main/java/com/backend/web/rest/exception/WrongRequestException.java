package com.backend.web.rest.exception;

import utils.message.MessageItem;

public class WrongRequestException extends DeveloperMessageException {
    public WrongRequestException(MessageItem messageItem)
    {
        super(messageItem);
    }
}
