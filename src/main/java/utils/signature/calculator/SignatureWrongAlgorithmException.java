package utils.signature.calculator;

public class SignatureWrongAlgorithmException extends RuntimeException {
    public SignatureWrongAlgorithmException(String message)
    {
        super(message);
    }
    public SignatureWrongAlgorithmException()
    {
        super();
    }
}
