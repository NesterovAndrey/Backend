package com.backend.domain.query;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

public class NativeQueryBuilder implements INativeQueryBuilder {
    private final EntityManager entityManager;
    private String sqlString;
    private Map<NativeQueryEnum,String> queries=new HashMap<>();
    private Map<String,Object> params=new HashMap<>();

    public NativeQueryBuilder(EntityManager entityManager)
    {
        this.entityManager=entityManager;
    }
    public INativeQueryBuilder setSqlQueryString(String sqlString)
    {
        this.sqlString=sqlString;
        return this;
    }

    @Override
    public INativeQueryBuilder setSqlQueryFromRegisteredString(NativeQueryEnum query) {
        this.sqlString=queries.get(query);
        return this;
    }

    @Override
    public void registerQueryString(NativeQueryEnum query, String queryString) {
        queries.put(query,queryString);
    }

    public INativeQueryBuilder addParam(String key,Object value)
    {
        params.put(key,value);
        return this;
    }
    public IQuery build()
    {
        return new NativeQuery(entityManager,sqlString,params);
    }
}
