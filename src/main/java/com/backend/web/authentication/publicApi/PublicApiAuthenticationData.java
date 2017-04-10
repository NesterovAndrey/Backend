package com.backend.web.authentication.publicApi;

import utils.signature.ISignature;
import utils.signature.Signature;
import utils.signature.calculator.ISignatureCalculator;
import utils.signature.calculator.SignatureString;

import javax.servlet.http.HttpServletRequest;

public class PublicApiAuthenticationData {


    private static final String AUTHENTICATION_HEADER = "Authorization";

    private static final String DELIMITER = ":";

    private final String appID;
    private final ISignature signature;

    public PublicApiAuthenticationData(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHENTICATION_HEADER);
        String[] data = authorizationHeader.split(DELIMITER);

        appID = data[0];
        ISignatureCalculator signatureCalculator=new SignatureString(data[1]);
        signature = new Signature(signatureCalculator.calculate());
    }

    public String getAppID() {
        return appID;
    }

    public ISignature getSignature() {
        return signature;
    }

}
