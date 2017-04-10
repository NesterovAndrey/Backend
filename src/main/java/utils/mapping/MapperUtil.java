package utils.mapping;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collection;

public class MapperUtil implements IMapperUtil
{

   private final ModelMapper modelMapper;

   public MapperUtil(ModelMapper modelMapper)
   {
       this.modelMapper=modelMapper;
   }

    @Override
    public <T,S> T map(S source,Class<T> targetClass) {
        return mapSource(source,targetClass);
    }

    @Override
    public <T,S> Collection<T> map(Collection<S> sourceTypeList,Class<T> targetClass) {
        Collection<T> result= new ArrayList<>();
        for(S sourceType : sourceTypeList)
        {
            result.add(map(sourceType,targetClass));
        }
        return result;
    }
     protected <T,S> T mapSource(S sourceType,Class<T> targetClass) {
        return getModelMapper().map(sourceType,targetClass);
    }
    protected ModelMapper getModelMapper()
    {
        return this.modelMapper;
    }
}
