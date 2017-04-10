package com.backend.web.authentication.publicApi;

import com.backend.service.app.IAppService;
import com.backend.web.rest.filter.ApiKeyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class PublicApiTestConfig {

    @Bean
    @Autowired
    public ApiKeyFilter apiKeyFilter(IAppService appService)
    {
        return new ApiKeyFilter(publicApiAuthenticationManager(appService));
    }
    @Bean
    @Autowired
    public PublicApiAuthenticationManager publicApiAuthenticationManager(IAppService appService)
    {
        return new PublicApiAuthenticationManager(Collections.singletonList(publicAPIAuthenticationProvider(appService)));
    }
    @Bean
    @Autowired
    public PublicAPIAuthenticationProvider publicAPIAuthenticationProvider(IAppService appService)
    {
        PublicAPIAuthenticationProvider provider= new PublicAPIAuthenticationProvider();
        provider.setAppService(appService);
        return provider;
    }
}
