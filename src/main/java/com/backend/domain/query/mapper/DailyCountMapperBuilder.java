package com.backend.domain.query.mapper;


import com.backend.domain.metrcis.IMetricData;
import utils.calendarService.ICalendarWrapper;

public class DailyCountMapperBuilder implements IQueryResultMapperBuilder {
    @Override
    public IQueryResultMapper<IMetricData> build(ICalendarWrapper calendarWrapper) {
        return new ObjectArrToDailyCountDataMapper(calendarWrapper);
    }
}
