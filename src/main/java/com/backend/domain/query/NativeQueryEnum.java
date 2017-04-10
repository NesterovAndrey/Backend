package com.backend.domain.query;


import com.backend.domain.metrcis.IMetricData;
import com.backend.domain.query.mapper.*;

public enum NativeQueryEnum
{
    DAU("DAU",new DailyCountMapperBuilder()),
    MAU("MAU",new MonthlyCountMapperBuilder()),
    WAU("WAU",new WeeklyCountMapperBuilder()),
    RETENTION("RETENTION",new DailyRetentionMapperBuilder()),
    NEW_USER_RETENTION("NEW_USER_RETENTION",new DailyRetentionMapperBuilder()),
    EXISTING_USER_RETENTION("EXISTING_USER_RETENTION",new DailyRetentionMapperBuilder());

    private final String value;
    private final IQueryResultMapperBuilder builder;
    NativeQueryEnum(String value, IQueryResultMapperBuilder builder){
        this.value=value;
        this.builder=builder;
    }
    public String getValue()
    {
        return this.value;
    }
    public IQueryResultMapperBuilder getBuilder()
    {
        return this.builder;
    }
}
