package utils.mapping;

import java.util.Collection;

public interface IMapperUtil {
    <T,S> T map(S source,Class<T> targetClass);
    <T,S> Collection<T> map(Collection<S> sourceTypeList,Class<T> targetClass);
}
