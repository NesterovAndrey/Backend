package com.backend.domain.query;

import com.backend.domain.query.mapper.IQueryResultMapper;

import java.util.Collection;

public interface IQuery {
    Collection<Object> getRawData();
    <T>Collection<T> getData(IQueryResultMapper<T> mapper);
}
