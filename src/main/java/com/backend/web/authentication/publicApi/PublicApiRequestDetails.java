package com.backend.web.authentication.publicApi;

import utils.uid.IHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublicApiRequestDetails implements IPublicApiRequestData {

    private final IHash hash;
    public PublicApiRequestDetails(IHash hash)
    {
        this.hash=hash;
        Logger log= LoggerFactory.getLogger(this.getClass());
        log.info("HASH "+hash.toString());
    }

    @Override
    public String getBodyHash() {
        return hash.toString();
    }
}
