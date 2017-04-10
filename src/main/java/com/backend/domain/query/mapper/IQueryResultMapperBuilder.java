package com.backend.domain.query.mapper;


import com.backend.domain.metrcis.IMetricData;
import utils.calendarService.ICalendarWrapper;

public interface IQueryResultMapperBuilder {
    public IQueryResultMapper<IMetricData> build(ICalendarWrapper calendarWrapper);
}
