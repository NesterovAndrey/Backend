package com.backend.config;

import com.backend.service.app.IAppService;
import com.backend.web.rest.filter.ApiKeyFilter;
import com.backend.web.rest.filter.CORSFilter;
import com.backend.web.rest.filter.ExceptionHandlerFilter;
import com.backend.web.authentication.publicApi.PublicAPIAuthenticationProvider;
import com.backend.web.authentication.publicApi.PublicApiAuthenticationManager;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        Logger logger = LoggerFactory.logger(this.getClass());
        logger.info("CONFIGURE IN MEMORY AUTH");
        builder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }
    @Configuration
    @Order(98)
    public static class CommonSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private ExceptionHandlerFilter exceptionHandlerFilter;
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/*/**").addFilterBefore(exceptionHandlerFilter, WebAsyncManagerIntegrationFilter.class);

        }
    }
    @Configuration
    @Order(99)
    public static class PrivateSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private ApplicationContext appContext;
        @Autowired
        @Qualifier("security")
        private AuthenticationEntryPoint authenticationEntryPoint;
        @Autowired
        private LogoutSuccessHandler restLogoutSuccessHandler;
        @Autowired
        private ExceptionHandlerFilter exceptionHandlerFilter;
        @Resource
        private CORSFilter corsFilter;

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
        @Override
        public void configure(HttpSecurity http) throws Exception {


            http.httpBasic().and().authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS, "/*/**").permitAll()
                    .antMatchers("/login", "/oauth/token", "/accounts").permitAll()
                    .antMatchers("/logout", "/accounts/**", "/private/**","/public/**").authenticated()
                    .antMatchers("/private/application/**").access("@webSecurityService.checkOwner(authentication,#appID)");

            http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
            http.logout().logoutUrl("/logout").logoutSuccessHandler(restLogoutSuccessHandler);
            http.antMatcher("/*/**").addFilterBefore(exceptionHandlerFilter, WebAsyncManagerIntegrationFilter.class);
            //servlet.antMatcher("/*/**").addFilterBefore(exceptionHandlerFilter,WebAsyncManagerIntegrationFilter.class);
            http.csrf().disable();

        }

    }

    @Configuration
    @Order(97)
    public static class PublicSucurityContext extends WebSecurityConfigurerAdapter {
        @Autowired
        private ApiKeyFilter apiKeyFilter;
        @Autowired
        private ExceptionHandlerFilter exceptionHandlerFilter;
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.antMatcher("/*/**").addFilterBefore(exceptionHandlerFilter, WebAsyncManagerIntegrationFilter.class);
            http.antMatcher("/public/**").addFilterAfter(apiKeyFilter, WebAsyncManagerIntegrationFilter.class);


        }

        @Bean
        @Autowired
        public ApiKeyFilter apiKeyFilter(@Qualifier("PublicAPIAuthenticationManager") AuthenticationManager authenticationManager,
                                         @Qualifier("ApiKeyFilter") AuthenticationEntryPoint authenticationEntryPoint) {
            return new ApiKeyFilter(authenticationManager);
        }
        @Bean
        @Autowired
        @Qualifier("PublicAPIAuthenticationManager")
        public AuthenticationManager publicAuthenticationManager(IAppService appService)
        {
            PublicAPIAuthenticationProvider authenticationProvider=new PublicAPIAuthenticationProvider();
            authenticationProvider.setAppService(appService);
            List<AuthenticationProvider> providers= new ArrayList<>(1);
            providers.add(authenticationProvider);
            return new PublicApiAuthenticationManager(providers);
        }
    }


}
