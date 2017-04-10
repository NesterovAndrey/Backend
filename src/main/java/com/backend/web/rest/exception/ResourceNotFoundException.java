package com.backend.web.rest.exception;

import utils.message.MessageItem;

public class ResourceNotFoundException extends DeveloperMessageException {
    public ResourceNotFoundException(MessageItem messageItem)
    {
        super(messageItem);
    }
}
