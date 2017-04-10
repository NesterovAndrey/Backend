package utils.signature.calculator;

public final class HexStringSignatureCalculator implements ISignatureCalculator {

    private final byte[] signatureBytes;
    public HexStringSignatureCalculator(byte[] signature)
    {
        this.signatureBytes=signature;
    }

    @Override
    public byte[] calculate() {
        return this.signatureBytes;
    }
}
