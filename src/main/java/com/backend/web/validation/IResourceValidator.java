package com.backend.web.validation;

import com.backend.web.rest.exception.NotFoundException;

public interface IResourceValidator<ResourceType,IdType> {
    public void validate(IdType id) throws NotFoundException;
}
