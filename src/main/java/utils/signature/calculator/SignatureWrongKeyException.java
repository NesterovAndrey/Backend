package utils.signature.calculator;

public class SignatureWrongKeyException extends RuntimeException {
    public SignatureWrongKeyException(String message)
    {
        super(message);
    }
    public SignatureWrongKeyException()
    {
        super();
    }
}
