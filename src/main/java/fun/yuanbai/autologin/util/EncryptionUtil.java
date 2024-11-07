package fun.yuanbai.autologin.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncryptionUtil {

    private static final byte[] SECRET_KEY_BYTES = "a8L2wVEpWyvPnMFz".getBytes(StandardCharsets.UTF_8);
    private static final SecretKeySpec SECRET_KEY = new SecretKeySpec(SECRET_KEY_BYTES, "AES");

    // 密钥算法、模式和填充
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    private static final byte[] IV = "888L1GO6taQ63tNS".getBytes(StandardCharsets.UTF_8);

    // 加密方法
    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY, new javax.crypto.spec.IvParameterSpec(IV));
            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt data", e);
        }
    }

    // 解密方法
    public static String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY, new javax.crypto.spec.IvParameterSpec(IV));
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt data", e);
        }
    }
}