package com.backend.web.authentication.publicApi;

import com.backend.domain.application.Application;
import com.backend.service.app.IAppService;
import com.backend.web.rest.exception.NotFoundException;
import utils.message.MessageItemImpl;
import utils.signature.ISignature;
import utils.signature.Signature;
import utils.signature.calculator.HMACSignatureCalculator;
import utils.signature.calculator.ISignatureCalculator;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;


public class PublicAPIAuthenticationProvider implements AuthenticationProvider {
    private IAppService appService;
    private  Logger logger= org.slf4j.LoggerFactory.getLogger(this.getClass());
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String appID=(String)authentication.getPrincipal();
        logger.info("APP ID "+appID);
        Application app=retrieveApp(appID);
        authenticationCheck(authentication,app);
        return createSuccessAuthentication(app);

    }
    protected void authenticationCheck(Authentication authentication,Application app)
    {
        Logger logger= org.slf4j.LoggerFactory.getLogger(this.getClass());
        PublicApiRequestDetails details=(PublicApiRequestDetails)authentication.getDetails();
        logger.info("IS NULL "+app+" "+details+" ");
        logger.info("IS NULL "+app+" "+details.getBodyHash()+" ");
        logger.info("IS NULL "+app+" "+details.getBodyHash().getBytes()+" ");
        logger.info("IS NULL "+app+" "+app.getPrivateKey()+" ");
        logger.info("IS NULL "+app+" "+app.getPrivateKey().getBytes()+" ");
        ISignatureCalculator signatureCalculator=new HMACSignatureCalculator(details.getBodyHash().getBytes(),app.getPrivateKey().getBytes());
        ISignature calculatedSignature=new Signature(signatureCalculator.calculate());

        logger.info("AUTH "+calculatedSignature.toString()+" "+authentication.getCredentials().toString());
        if(!calculatedSignature.equals(authentication.getCredentials()))
        {
            throw new BadCredentialsException("Credentials are not equal");
        }
    }
    protected Application retrieveApp(String appID)
    {
        Application app;
        if(appService==null)
        {
            throw new RuntimeException("Application service must be not null");
        }
        app=appService.findByID(appID);
        if(app==null) throw new NotFoundException(new MessageItemImpl("exception.app.not_found_id",new Object[]{appID}));
        return app;
    }

    protected Authentication createSuccessAuthentication(Application app)
    {
        Collection<? extends GrantedAuthority> authorities= new ArrayList<>();

       return new AppAuthenticationToken(app,authorities);
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(PublicApiAuthenticationToken.class);
    }
    public void setAppService(IAppService appService)
    {
        this.appService=appService;
    }
}
