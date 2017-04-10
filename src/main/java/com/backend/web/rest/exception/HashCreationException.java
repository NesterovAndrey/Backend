package com.backend.web.rest.exception;

import utils.message.MessageItem;
public class HashCreationException extends DeveloperMessageException {
    public HashCreationException(MessageItem messageItem) {
        super(messageItem);
    }
}
