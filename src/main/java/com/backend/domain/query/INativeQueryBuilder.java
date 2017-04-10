package com.backend.domain.query;


public interface INativeQueryBuilder {
    INativeQueryBuilder setSqlQueryString(String sqlString);
    INativeQueryBuilder setSqlQueryFromRegisteredString(NativeQueryEnum query);
    void registerQueryString(NativeQueryEnum query,String queryString);
    INativeQueryBuilder addParam(String key,Object value);
    IQuery build();
}
