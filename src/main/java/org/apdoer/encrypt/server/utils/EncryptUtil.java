package org.apdoer.encrypt.server.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * @author apdoer
 */
@Component
public class EncryptUtil {

    /**
     * 随机密锁
     **/
    @Value("${aes.skey}")
    private String sKey;

    /**
     * AES 加密
     *
     * @param sSrc 需要加密的字符串
     * @return String
     * @throws Exception e
     */
    public String encrypt(String sSrc) throws Exception {
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        // "算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));
        // 将二进制转换成16进制
        return parseByte2HexStr(encrypted);
    }

    /**
     * AES 解密
     *
     * @param sSrc 需要解密的字符串
     * @return String
     * @throws Exception e
     */
    public String decrypt(String sSrc) throws Exception {
        byte[] raw = "hhhhhhhhhhhhhhhh".getBytes("ASCII");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        // 先用base64解密
        //byte[] encrypted1 = Base64.decodeBase64(sSrc);
        // 将16进制转换为二进制
        byte[] encrypted1 = parseHexStr2Byte(sSrc);
        byte[] original = cipher.doFinal(encrypted1);
        return new String(original);
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     * @throws
     * @method parseByte2HexStr
     * @since v1.0
     */
    private String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     * @throws
     * @method parseHexStr2Byte
     * @since v1.0
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
