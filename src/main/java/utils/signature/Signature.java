package utils.signature;

import org.springframework.security.crypto.codec.Hex;

import java.util.Arrays;
import java.util.Objects;

public final class Signature implements ISignature{
    protected final byte[] signature;

    private String signatureString;

    public Signature(byte[] signature)
    {
        this.signature=signature;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(!(obj instanceof ISignature)) return  false;
        if(obj==this) return true;
        ISignature signature=(ISignature) obj;
        if(signature.getBytes()==null || this.getBytes()==null) return  false;
        return Arrays.equals(signature.getBytes(),this.getBytes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.signature);
    }

    @Override
    public byte[] getBytes() {
        return this.signature;
    }

    @Override
    public String toString() {
        return signatureString=signatureString==null?new String(Hex.encode(signature)):signatureString;
    }
}
