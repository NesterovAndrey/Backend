package com.backend.service;

public interface IBaseService<Result,Id> {
    Result findByID(Id id);
}
