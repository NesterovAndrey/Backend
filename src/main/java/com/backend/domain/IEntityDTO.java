package com.backend.domain;

public interface IEntityDTO <IdType> {
    IdType getId();
    void setId(IdType id);
}
