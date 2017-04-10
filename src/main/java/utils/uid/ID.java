package utils.uid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ID implements IID{

    private byte[] bytes={};
    private UUID result;
    public ID(byte[] randomBytes)
    {
        this.bytes=randomBytes;
        result= generateID();
    }
    public  ID(UUID id)
    {
        result=id;
        bytes=result.toString().getBytes();
    }
    @Override
    public String toString() {
        return result.toString();
    }


    private UUID generateID()
    {
        UUID id=UUID.nameUUIDFromBytes(bytes);
        Logger logger= LoggerFactory.getLogger(ID.class);
        logger.info("ID "+id.toString());
        return id;
    }


}
