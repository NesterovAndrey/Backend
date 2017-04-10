package com.backend.domain.metrcis;


import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class MetricData implements IMetricData {
    private final List<Pair> valuesList;
    private final Map<Object,Pair> valuesMap;

    public MetricData()
    {
        valuesList=new ArrayList<>();
        valuesMap= new HashMap<>();
    }
    public <K,V> void addValue(K key,V value)
    {
        Pair<K,V> pair= new ImmutablePair<>(key, value);
        valuesList.add(pair);
        valuesMap.put(key,pair);
    }
    public <K> Pair getData(K key)
    {
        if(!valuesMap.containsKey(key))
        {
            throw new IllegalArgumentException(new Formatter().format("Key %s not found",key).toString());
        }
        return valuesMap.get(key);
    }
    public List<Pair> getDataList()
    {
        Pair[] arr=new Pair[valuesList.size()];
        return Arrays.asList(valuesList.toArray(arr));
    }
}
