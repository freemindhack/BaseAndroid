package com.hkllzh.android.util.md5;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    private static final String HASH_ALGORITHM = "MD5";
    private static final int RADIX = 10 + 26; // 10 digits + 26 letters

    /**
     * 生成字符串的MD5编码
     *
     * @param text 需要编码的字符串
     * @return 编码后的字符串
     */
    public static String generate(String text) {
        if (null == text) {
            text = "";
        }
        byte[] md5 = getMD5(text.getBytes());
        BigInteger bi = new BigInteger(md5).abs();
        return bi.toString(RADIX);
    }

    private static byte[] getMD5(byte[] data) {
        byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(data);
            hash = digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // LogUtil.e(e);
        }
        return hash;
    }
}