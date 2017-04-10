package com.backend.web.rest.exception;

import utils.message.MessageItem;

public class NotFoundException extends DeveloperMessageException {
    public NotFoundException(MessageItem messageItem)
    {
        super(messageItem);
    }
}
