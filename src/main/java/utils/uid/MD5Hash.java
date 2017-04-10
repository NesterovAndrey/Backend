package utils.uid;

import com.backend.web.rest.exception.HashCreationException;
import utils.message.MessageItemImpl;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash implements IHash {

    private final String ALGORITHM="MD5";

    private final byte[] data;
    private byte[] result;
    public MD5Hash(byte[] data)
    {
        this.data=data;
    }
    @Override
    public String toString() {
        return DatatypeConverter.printHexBinary(asByteArray());
    }

    @Override
    public byte[] asByteArray() {
        return getMD5();
    }

    private byte[] getMD5()
    {
        if(result==null)
        {
            result=calculateMD5();
        }
        return result;
    }
    private byte[] calculateMD5()
    {
        MessageDigest messageDigest=getDigest();
        messageDigest.reset();
        messageDigest.update(data);
        return messageDigest.digest();
    }
    private  MessageDigest getDigest()
    {
        try {
            return MessageDigest.getInstance(ALGORITHM);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new HashCreationException(new MessageItemImpl("exception.internal"));
        }
    }
}
