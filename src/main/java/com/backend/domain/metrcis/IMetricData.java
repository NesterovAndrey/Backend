package com.backend.domain.metrcis;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface IMetricData {
   <K,V> void addValue(K key,V value);
   <K> Pair getData(K key) throws IllegalArgumentException;
   List<Pair> getDataList() throws IllegalArgumentException;
}
