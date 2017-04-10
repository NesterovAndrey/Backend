package utils.requestBody;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

public class MD5Encoder implements DataEncoder {

    @Override
    public byte[] encode(byte[] data) {
        return DigestUtils.md5DigestAsHex(data).getBytes();
    }
}
