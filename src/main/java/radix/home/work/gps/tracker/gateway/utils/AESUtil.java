package radix.home.work.gps.tracker.gateway.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import radix.home.work.gps.tracker.gateway.dto.AesKeyDto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AESUtil {

    private static final String ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_SUITE = "AES/CBC/PKCS5PADDING";
    private static final int[] ALLOWED_KEY_LENGTH = {128, 192, 256};
    private static final int IV_LENGTH = 16;

    /**
     * Method for decrypting data
     *
     * @param data        The data to decrypt
     * @param key         The aes-key to be used
     * @param iv          The initialisation vector to be used
     * @param cipherSuite The cipher suite to be used
     * @return The data decrypted
     */
    public static byte[] decrypt(byte[] data, byte[] key, byte[] iv, String cipherSuite)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

        return doXCrypt(data, Cipher.DECRYPT_MODE, key, iv, cipherSuite);
    }


    /**
     * Method for encrypting data
     *
     * @param data        The data to encrypt
     * @param key         The aes-key to be used
     * @param iv          The initialisation vector to be used
     * @param cipherSuite The cipher suite to be used
     * @return The data encrypted
     */
    public static byte[] encrypt(byte[] data, byte[] key, byte[] iv, String cipherSuite)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

        return doXCrypt(data, Cipher.ENCRYPT_MODE, key, iv, cipherSuite);
    }

    /**
     * Internal generic method for encrypting/decrypting {@code byte[]}
     *
     * @param data        The data to manipulate
     * @param action      {@link Cipher#ENCRYPT_MODE} or {@link Cipher#DECRYPT_MODE}
     * @param key         The aes-key to be used
     * @param iv          The initialisation vector to be used
     * @param cipherSuite The cipher suite to be used
     * @return A {@code byte[]} with the data manipulated
     */
    private static byte[] doXCrypt(byte[] data, int action, byte[] key, byte[] iv, String cipherSuite)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

        if (Arrays.stream(ALLOWED_KEY_LENGTH).noneMatch(length -> length == key.length)) {
            throw new IllegalArgumentException("Key length must be one of: " +
                    Arrays.toString(ALLOWED_KEY_LENGTH));
        } else if (iv.length != IV_LENGTH) {
            throw new IllegalArgumentException("Initialisation vector length must be : " + IV_LENGTH);
        }

        var cipher = Cipher.getInstance(cipherSuite);

        // FIXME (java:S3329):
        //  - For encryption: generate random iv and return it with the encrypted data
        //  - For decryption: use given iv to decrypt data
        @SuppressWarnings("java:S3329")
        var ivSpec = new IvParameterSpec(iv);
        cipher.init(action, new SecretKeySpec(key, ALGORITHM), ivSpec);
        return cipher.doFinal(data);
    }

    /**
     * A service for generating a random aes-key
     *
     * @param keyLength The length of the key to be generated (must be on of the {@link AESUtil#ALLOWED_KEY_LENGTH}).
     * @return An {@link AesKeyDto} filled
     */
    public static AesKeyDto generateKey(int keyLength) {
        if (Arrays.stream(ALLOWED_KEY_LENGTH).noneMatch(length -> length == keyLength)) {
            throw new IllegalArgumentException("Key length must be one of: " +
                    Arrays.toString(ALLOWED_KEY_LENGTH));
        } else {
            var key = new AesKeyDto();
            key.setLength(keyLength);
            key.setCipher(DEFAULT_CIPHER_SUITE);

            var iv = new byte[16];
            new SecureRandom().nextBytes(iv);
            key.setIv(iv);

            var aesKey = new byte[keyLength];
            new SecureRandom().nextBytes(aesKey);
            key.setKey(aesKey);

            return key;
        }
    }
}
