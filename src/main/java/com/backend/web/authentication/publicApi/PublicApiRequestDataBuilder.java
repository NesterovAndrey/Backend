package com.backend.web.authentication.publicApi;

import utils.uid.MD5Hash;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;


@Component
public class PublicApiRequestDataBuilder {
    public IPublicApiRequestData build(HttpServletRequest request)
    {
        ContentCachingRequestWrapper req=new ContentCachingRequestWrapper(request);
        return new PublicApiRequestDetails(new MD5Hash(req.getContentAsByteArray()));
    }
}
