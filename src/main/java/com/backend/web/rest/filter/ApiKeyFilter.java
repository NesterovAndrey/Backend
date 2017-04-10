package com.backend.web.rest.filter;

import utils.uid.MD5Hash;
import com.backend.web.authentication.publicApi.PublicApiAuthenticationToken;
import com.backend.web.authentication.publicApi.PublicApiRequestDetails;
import com.backend.web.authentication.publicApi.PublicApiAuthenticationData;
import utils.servlet.CachedBodyHTTPServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Check request signature = HMAC256(key = application private key, body = Md5(request body))
 */
@Component
public class ApiKeyFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    public ApiKeyFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        CachedBodyHTTPServletRequest cachedBodyRequest=new  CachedBodyHTTPServletRequest(httpServletRequest);
        Authentication authentication;
        byte[] bytes;
        try {
            logger.info("BYTEST ");
        bytes=cachedBodyRequest.getCachedContent();
        logger.info("BYTEST "+bytes);
        PublicApiAuthenticationData publicApiAuthenticationData = new PublicApiAuthenticationData(httpServletRequest);

        if (publicApiAuthenticationData.getAppID() == null
                || publicApiAuthenticationData.getSignature().toString() == null) {
            filterChain.doFilter(cachedBodyRequest, httpServletResponse);
            return;
        }

            PublicApiAuthenticationToken token=new PublicApiAuthenticationToken(publicApiAuthenticationData);
            token.setDetails(new PublicApiRequestDetails(new MD5Hash(bytes)));
            authentication=authenticationManager.authenticate(token);
        }
        catch (AuthenticationException e)
        {
            throw e;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(cachedBodyRequest, httpServletResponse);
    }

}
