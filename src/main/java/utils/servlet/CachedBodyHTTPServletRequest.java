package utils.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

public class CachedBodyHTTPServletRequest extends HttpServletRequestWrapper {

    private byte[] body;
    private final Logger log= LoggerFactory.getLogger(this.getClass());
    public CachedBodyHTTPServletRequest(HttpServletRequest request) {
        super(request);
        log.info("CONTENT LENGTH "+request.getContentLength());
    }
    public byte[] getCachedContent() throws IOException
    {
        log.info("BODY "+(body==null));
        if(body==null) {
            body = new byte[getRequest().getContentLength()];
            getRequest().getInputStream().read(body);
            //dlog.info("CONTENT LENGTH "+body[1]);
        }
        return body.clone();
    }
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if(this.body==null)
        {
            body=new byte[getRequest().getContentLength()];
            getInputStream().read(body);
        }
        return new ContentCachingInputStream();
    }

    private class  ContentCachingInputStream extends ServletInputStream
    {

        private int lastIndexRetrieved = -1;
        private ReadListener readListener = null;

        public boolean isFinished() {
            return (lastIndexRetrieved == body.length-1);
        }

        public boolean isReady() {
            return isFinished();
        }

        public void setReadListener(ReadListener readListener) {
            this.readListener = readListener;
            if (!isFinished()) {
                try {
                    readListener.onDataAvailable();
                } catch (IOException e) {
                    readListener.onError(e);
                }
            } else {
                try {
                    readListener.onAllDataRead();
                } catch (IOException e) {
                    readListener.onError(e);
                }
            }
        }

        @Override
        public int read() throws IOException {
            int i;
            if (!isFinished()) {
                i = body[lastIndexRetrieved+1];
                lastIndexRetrieved++;
                if (isFinished() && (readListener != null)) {
                    try {
                        readListener.onAllDataRead();
                    } catch (IOException ex) {
                        readListener.onError(ex);
                        throw ex;
                    }
                }
                return i;
            } else {
                return -1;
            }
        }
    }

}
