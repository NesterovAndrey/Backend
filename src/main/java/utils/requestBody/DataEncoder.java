package utils.requestBody;

/**
 *
 * This interface for wrapping instantiations of hashing algorithms
 */
public interface DataEncoder {
    byte[] encode(byte[] data);
}
