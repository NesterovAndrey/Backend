package utils.hibernate;

import utils.IByteSource;
import utils.uid.ID;
import utils.uid.IID;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class IDGenerator implements IdentifierGenerator {

    IID id;

    @Override
    public Serializable generate(SessionImplementor sessionImplementor, Object o) throws HibernateException {

        if(!(o instanceof IByteSource)) throw new RuntimeException("The Object must implements IByteSource interface");
        IByteSource byteArray=(IByteSource) o;
        id=new ID(byteArray.asByteArray());
        return id.toString();
    }
}
