package utils.signature.calculator;

import org.springframework.security.crypto.codec.Hex;

public final class SignatureString implements ISignatureCalculator {
    private final ISignatureCalculator hexSignatureCalculator;
    public SignatureString(String signatureString)
    {
        hexSignatureCalculator=new HexStringSignatureCalculator(Hex.decode(signatureString));
    }
    @Override
    public byte[] calculate() {
        return hexSignatureCalculator.calculate();
    }
}
