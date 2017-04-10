package com.backend.domain.query;

import com.backend.domain.query.mapper.IQueryResultMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;


public class NativeQuery implements IQuery{
    private final EntityManager entityManager;
    private final String sqlString;
    private Map<String,Object> params;
    private Collection<Object> rawData=null;
    public NativeQuery(EntityManager entityManager, String sqlStrings)
    {
        this(entityManager,sqlStrings,new HashMap<>());
    }
    public NativeQuery(EntityManager entityManager, String sqlString,Map<String,Object> params)
    {
        this.entityManager=entityManager;
        this.sqlString=sqlString;
        this.params=params;
    }
    protected final EntityManager getEntityManager()
    {
       return this.entityManager;
    }
    protected final void registerParam(String key,Object value)
    {
        params.put(key,value);
    }
    private Query createQuery()
    {
        Query query=getEntityManager().createNativeQuery(this.sqlString);
        addParams(query);
        return query;
    }
    private void addParams(Query query)
    {
        for (Object o : this.params.entrySet()) {
            Map.Entry<String, Object> pair = (Map.Entry) o;
            query.setParameter(pair.getKey(), pair.getValue());
        }
    }
    @Transactional
    @Override
    public Collection<Object> getRawData() {
        if(rawData==null) {

            rawData=createQuery().getResultList();
        }
        return rawData;
    }
    @Override
    public <T>Collection<T> getData(IQueryResultMapper<T> mapper) {

        Collection<T> result= new ArrayList<>();
        Collection<Object> rawData=getRawData();
        for(Object raw:rawData)
        {
            result.add(mapper.map((Object[])raw));
        }
        return result;
    }
}
