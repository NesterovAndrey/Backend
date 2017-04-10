package utils.signature.calculator;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public final class HMACSignatureCalculator implements ISignatureCalculator {
    private static final String ALGORITHM="HmacSHA256";

    private final byte[] privateKey;
    private final byte[] bodyHash;
    private byte[] result=null;

    public HMACSignatureCalculator(byte[] bodyHash,byte[] privateKey)
    {
        this.bodyHash=bodyHash;
        this.privateKey=privateKey;
    }

    @Override
    public byte[] calculate() {
        if(this.result!=null)
            return this.result;

            SecretKey key = new SecretKeySpec(this.privateKey, ALGORITHM);
            try {
                Mac mac = Mac.getInstance(key.getAlgorithm());
                mac.init(key);
                this.result = mac.doFinal(bodyHash);
            } catch (NoSuchAlgorithmException e) {
                throw new SignatureWrongAlgorithmException(e.getMessage());
            } catch (InvalidKeyException e) {
                throw new SignatureWrongKeyException(e.getMessage());
            }
            return result;
    }
}
