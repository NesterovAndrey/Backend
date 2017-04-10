package com.backend.domain;

public interface IEntity<IdType> {
    IdType getId();
    void setId(IdType id);
}
