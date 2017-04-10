package com.backend.service.metrics;

import com.backend.domain.metrcis.IMetricData;
import com.backend.domain.query.NativeQueryEnum;

import java.util.Collection;
import java.util.Date;

public interface IMetricsService {
    Collection<IMetricData> getActiveUsers(NativeQueryEnum query, Date from, Date to);
    Collection<IMetricData> getRetention(NativeQueryEnum query,int day);
}
