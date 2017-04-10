package com.backend.config.authentication;

import com.backend.web.authentication.CustomUserAuthenticationConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.*;

import java.util.Formatter;

@Configuration
@EnableResourceServer
public class ResourceServer  extends ResourceServerConfigurerAdapter {

    @Value("${host.url}")
    private String hostUrl;
    @Value("${host.port}")
    private String hostPort;
    @Value("${host.path}")
    private String hostPath;
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(customResourceRemoteTokenServices());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/login", "/oauth/token", "/accounts", "/public/**").permitAll()
                .antMatchers("/logout", "/accounts/**", "/private/**").authenticated().and().exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
    @Bean
    @Primary
    public RemoteTokenServices customResourceRemoteTokenServices() {
        Logger log= LoggerFactory.getLogger(this.getClass());
        log.info("_---------------------------------- HOOOOOOOSTTTT  "+hostUrl+" "+hostPath);
        RemoteTokenServices defaultTokenServices = new RemoteTokenServices();
        defaultTokenServices.setCheckTokenEndpointUrl(new Formatter().format("%s:%s/%s/oauth/check_token",hostUrl,hostPort,hostPath).toString());
                //hostUrl+":"+hostPort+"/"+hostPath+"/oauth/check_token");
        defaultTokenServices.setClientId("my_client");
        defaultTokenServices.setClientSecret("secret");
        defaultTokenServices.setAccessTokenConverter(customConverter());
        return defaultTokenServices;
    }
    @Bean
    public DefaultAccessTokenConverter customConverter()
    {
        DefaultAccessTokenConverter converter=new DefaultAccessTokenConverter();
        converter.setUserTokenConverter(authenticationConverter());
        return converter;
    }
    @Bean
    public CustomUserAuthenticationConverter authenticationConverter()
    {
        return new CustomUserAuthenticationConverter();
    }

}
