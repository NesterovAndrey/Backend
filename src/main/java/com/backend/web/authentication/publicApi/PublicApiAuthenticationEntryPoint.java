package com.backend.web.authentication.publicApi;

import com.backend.web.rest.exception.WrongRequestException;
import utils.message.MessageItemImpl;
import utils.message.MessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Qualifier("ApiKeyFilter")
public class PublicApiAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Autowired
    private MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        throw new WrongRequestException(new MessageItemImpl("exception.request.authentication.error"));
    }

    @Override
    @Value("Secure realm")
    public void setRealmName(String realmName) {
        super.setRealmName(realmName);
    }
}
