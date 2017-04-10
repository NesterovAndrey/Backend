package config;

import utils.mapping.IMapperUtil;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperTestConfig {

    @Bean
    public IMapperUtil mapperUtil()
    {
        return Mockito.mock(IMapperUtil.class);
    }


}
