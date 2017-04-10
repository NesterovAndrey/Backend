package com.backend.domain.query.mapper;

/**
 * Created by yalta on 12.03.2017.
 */
public interface IQueryResultMapper<Result> {
    Result map(Object[] rawColumn);
}
