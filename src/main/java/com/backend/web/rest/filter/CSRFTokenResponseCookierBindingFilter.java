package com.backend.web.rest.filter;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Binds a {@link org.springframework.security.web.csrf.CsrfToken} to the {@link HttpServletResponse}
 * headers if the Spring {@link org.springframework.security.web.csrf.CsrfFilter} has placed one in the {@link HttpServletRequest}.
 *
 * Based on the work found in: <a href="http://stackoverflow.com/questions/20862299/with-spring-security-3-2-0-release-how-can-i-get-the-csrf-token-in-a-page-that">Stack Overflow</a>
 *
 * @author Allan Ditzel
 * @since 1.0
 */
public class CSRFTokenResponseCookierBindingFilter extends OncePerRequestFilter {
    protected static final String REQUEST_ATTRIBUTE_NAME = "_csrf";
    protected static final String RESPONSE_HEADER_NAME = "X-CSRF-HEADER";
    protected static final String RESPONSE_PARAM_NAME = "X-CSRF-PARAM";
    protected static final String RESPONSE_TOKEN_NAME = "X-CSRF-TOKEN";
    protected static final String RESPONSE_COOKIE_NAME = "CSRF-TOKEN";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, javax.servlet.FilterChain filterChain) throws ServletException, IOException {
        CsrfToken token = (CsrfToken) request.getAttribute(REQUEST_ATTRIBUTE_NAME);
        Cookie cookie = new Cookie(Headers.RESPONSE_COOKIE_NAME, token.getToken());
        cookie.setDomain(request.getHeader("Origin"));
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        response.addCookie(cookie);

        filterChain.doFilter(request, response);
    }
}