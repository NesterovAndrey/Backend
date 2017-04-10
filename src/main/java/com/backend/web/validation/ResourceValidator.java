package com.backend.web.validation;

import com.backend.web.rest.exception.NotFoundException;
import com.backend.service.IBaseService;
import utils.message.MessageItemImpl;
import utils.precondition.IValidator;
import org.springframework.beans.factory.annotation.Autowired;

public class ResourceValidator<ResourceType,IdType> implements IResourceValidator<ResourceType,IdType> {
    @Autowired
    private IValidator validator;
    private IBaseService<ResourceType,IdType> resourceService;
    private final String message;
    public ResourceValidator(String message,IBaseService<ResourceType,IdType> service)
    {
        this.message=message;
        this.resourceService=service;
    }
    @Override
    public void validate(IdType id) throws NotFoundException {
        ResourceType app=getResource(id);
        validator.notNull(app,new NotFoundException(new MessageItemImpl(message,new Object[]{id})));
    }
    protected ResourceType getResource(IdType id)
    {
        return resourceService.findByID(id);
    }
}
