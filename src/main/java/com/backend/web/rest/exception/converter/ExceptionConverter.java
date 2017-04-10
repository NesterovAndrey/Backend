package com.backend.web.rest.exception.converter;

public interface ExceptionConverter<Result,Param> {
    Result convert(Param exception);
}
