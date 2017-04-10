package utils.requestBody;

import org.springframework.web.util.ContentCachingRequestWrapper;

public class RequestBodyImpl implements RequestBody {

    private final String body;
    private final byte[] bodyBytes;
    public RequestBodyImpl(ContentCachingRequestWrapper request)
    {
        bodyBytes =request.getContentAsByteArray();
        body=new String(bodyBytes);
    }
    @Override
    public String getBody() {
        return body;
    }

    @Override
    public byte[] getBytes() {
        return bodyBytes;
    }

}
