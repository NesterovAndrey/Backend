package com.backend.service.metrics;

import com.backend.domain.metrcis.IMetricData;
import com.backend.domain.query.*;
import utils.calendarService.ICalendarWrapper;
import utils.precondition.IValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MetricsService implements IMetricsService {

    @Autowired
    private IValidator validator;
    @Autowired
    private ICalendarWrapper calendarService;
    @Autowired
    private INativeQueryBuilder nativeQueryBuilder;

    @Override
    public Collection<IMetricData> getActiveUsers(NativeQueryEnum query, Date from, Date to) {
       validateDates(from,to);

        return createAUQuery(query,from,to)
                .getData(query.getBuilder().build(calendarService));
    }

    @Override
    public Collection<IMetricData> getRetention(NativeQueryEnum query,int day) {
        validator.notNull(query,"Query must be not null");
        validateDay(day);
        return getRetentionQuery(query,day);
    }
    private void validateDates(Date from,Date to)
    {
        validator.notNull(from,"Date from must be not null");
        validator.notNull(to,"Date to must be not null");
    }
    private void validateDay(int day)
    {
        validator.isTrue(day>0,"Day must be greater then 0");
    }
    private IQuery createAUQuery(NativeQueryEnum query,Date from,Date to)
    {
        return createQuery(query)
                .addParam("from",from)
                .addParam("to",to)
                .build();
    }
    private IQuery createRetentionQuery(NativeQueryEnum query, int day)
    {
        return createQuery(query).addParam("day",day).build();
    }
    private INativeQueryBuilder createQuery(NativeQueryEnum query)
    {
        return nativeQueryBuilder.setSqlQueryFromRegisteredString(query);
    }
    private Collection<IMetricData> getRetentionQuery(NativeQueryEnum retentionQuery,int day)
    {
        return createRetentionQuery(retentionQuery,day).getData(retentionQuery.getBuilder().build(calendarService));
    }
}
