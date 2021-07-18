package com.example.example.component.aes.business;

import com.example.example.component.aes.AesProperties;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Security;

/**
 * AES加密
 * <p>
 * AES/CBC/PKCS7Padding和AES/CBC/PKCS5Padding加密结果是一样的
 * 使用AES/CBC/PKCS7Padding,还要引入第三方包bcprov-jdk16-1.46.jar支持
 *
 * @author jiacheng.chen
 */
@Slf4j
public class AesService {

    private AesProperties aesProperties;

    private static final String KEY_ALGORITHM = "AES";

    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";

    private static final String CHARSET_NAME = "utf-8";

    public AesService(AesProperties aesProperties) {
        this.aesProperties = aesProperties;
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @return byte[] 加密后的数
     */
    public byte[] encrypt(byte[] data) throws Exception {
        //密钥
        SecretKey secretKey = new SecretKeySpec(getKey(), KEY_ALGORITHM);
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, new BouncyCastleProvider());
        //初始化
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(getIv()));
        //执行操作
        return cipher.doFinal(data);
    }


    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @return byte[] 解密后的数据
     */
    public byte[] decrypt(byte[] data) throws Exception {
        // 密钥
        SecretKey secretKey = new SecretKeySpec(getKey(), KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, new BouncyCastleProvider());
        //初始化
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(getIv()));
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 获取KEY
     *
     * @return
     */
    private byte[] getKey() {
        byte[] key;
        try {
            key = aesProperties.getKey().getBytes(CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("aes key transform to byte error ....");
        }
        return key;
    }

    /**
     * 获取IV
     *
     * @return
     */
    private byte[] getIv() {
        byte[] iv;
        try {
            iv = aesProperties.getIv().getBytes(CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("aes iv transform to byte error ....");
        }
        return iv;
    }

    /**
     * 将十六进制字符串转换成 byte[]
     *
     * @param hex
     * @return
     */
    public byte[] hexToByte(String hex) {
        if ((hex == null) || (hex.length() == 0)) {
            return null;
        }
        if (hex.length() % 2 != 0) {
            return null;
        }
        int i = 0;
        int j = 0;
        int len = hex.length();
        byte[] out = new byte[len / 2];

        while (i < len) {
            i += 2;
            String s = hex.substring(i - 2, i);
            int intB = Integer.parseInt(s, 16);
            if (intB > 127) {
                intB = -((intB ^ 0xFFFFFFFF) + 1);
            }
            byte b = (byte) intB;
            out[(j++)] = b;
        }
        return out;
    }

    /**
     * 将byte[]转换成十六进制字符串
     *
     * @param bs
     * @return
     */
    public String byteToHex(byte[] bs) {
        StringBuffer s = new StringBuffer(100);
        String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

        for (int i = 0; i < bs.length; ++i) {
            int n = bs[i];
            if (n < 0) {
                n = 256 + n;
            }
            int d1 = n / 16;
            int d2 = n % 16;

            s.append(hexDigits[d1] + hexDigits[d2]);
        }
        return s.toString();
    }
}

