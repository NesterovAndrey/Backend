package model;

import org.springframework.beans.factory.annotation.Autowired;
import utils.requestBody.DataEncoder;
import utils.requestBody.MD5Encoder;
import utils.signature.ISignature;
import utils.signature.Signature;
import utils.signature.calculator.HMACSignatureCalculator;
import utils.signature.calculator.ISignatureCalculator;
import utils.signature.calculator.SignatureString;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Hex;
import java.util.Arrays;

public class SignatureTest {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    private DataEncoder dataEncoder=new MD5Encoder();
    private ISignatureCalculator hmacSignatureCalculator;
    private ISignatureCalculator signatureCalculator;
    @Before
    public void init()
    {
        hmacSignatureCalculator=new HMACSignatureCalculator("5157341f60990e87222257ef4f6dae74".getBytes(),"2acf8190-8e32-11e6-bdf4-0800200c9a66".getBytes());
        logger.info("HEX "+new String(Hex.decode("30f6fe4f66e8809e449a2d5301ef516a3b254b7fb1220694ff2fa0e7c79ff37c")));
        signatureCalculator=new SignatureString("30f6fe4f66e8809e449a2d5301ef516a3b254b7fb1220694ff2fa0e7c79ff37c");
    }
    @org.junit.Test
    public void testSelfEquality()
    {
        ISignature signatureOne=new Signature(hmacSignatureCalculator.calculate());
        ISignature signatureTwo=new Signature(hmacSignatureCalculator.calculate());
        logger.info("SIGNATURE "+signatureOne.toString());
        Assert.assertTrue(signatureOne.equals(signatureTwo));
    }
    @org.junit.Test
    public void testSelfEqualityFromStr()
    {
        ISignature signatureOne=new Signature(hmacSignatureCalculator.calculate());
        ISignature signatureTwo=new Signature(signatureCalculator.calculate());
        logger.info("SIGNATURE STRING "+signatureTwo.toString());
        Assert.assertTrue(signatureOne.equals(signatureTwo));
    }
    @org.junit.Test
    public void testSignatureEquality()
    {
        ISignature signatureOne=new Signature(hmacSignatureCalculator.calculate());
        ISignature signatureTwo=new Signature(signatureCalculator.calculate());
        Assert.assertTrue(signatureOne.equals(signatureTwo));
        ISignature signature=new Signature(new SignatureString("30f6fe4f66e8809e44").calculate());
        Assert.assertFalse(signature.equals(signatureOne));
    }

    @Test
    public void md5Test()
    {
        Assert.assertTrue(Arrays.equals(dataEncoder.encode("foo".getBytes()),"acbd18db4cc2f85cedef654fccc4a4d8".getBytes()));
    }
}
