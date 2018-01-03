package com.mz;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by mingzhu7 on 2017/12/7.
 */
public class AesSecurate {
    private Cipher decryptCipher;
    private Cipher encryptCipher;
    private static int getB(char s){
        if(s>='a')
            return s-'a'+10;
        else return s-'0';
    }
    private static byte[] hexTobyte(String s){
        byte[] sx=new byte[s.length()/2];
        for(int i=0;i<s.length();i=i+2){
            sx[i/2]=(byte)(getB(s.charAt(i))*16+getB(s.charAt(i+1)));
        }
        return sx;
    }
    private static String byteToHex(byte[] b) {
        String hs = "";
        String stmp = "";

        for(int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if(stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }

        return hs.toUpperCase();
    }
    private static byte[] process(final byte[] input, final Cipher cipher) {

        byte[] res = null;
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        CipherOutputStream cOut = new CipherOutputStream(bOut, cipher);

        try {
            cOut.write(input);
            cOut.flush();
            cOut.close();
            res = bOut.toByteArray();
        } catch (IOException e) {
        }
        return res;
    }
    private String decrypt(String plaintext){
        byte[] plainbytes=hexTobyte(plaintext);
        byte[] password=process(plainbytes,decryptCipher);
        return new String(password);
    }
    public String encrypt(String password) throws Exception{
        byte[] bytes=encryptCipher.doFinal(password.getBytes("utf-8"));
        return byteToHex(bytes).toLowerCase();
    }

    public Cipher getEncryptCipher() {
        return encryptCipher;
    }

    public void setEncryptCipher(Cipher encryptCipher) {
        this.encryptCipher = encryptCipher;
    }

    public Cipher getDecryptCipher() {
        return decryptCipher;
    }

    public void setDecryptCipher(Cipher decryptCipher) {
        this.decryptCipher = decryptCipher;
    }

    public static void main(String[] args) throws Exception{
        Stack<Integer> stack=new Stack<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        Iterator<Integer> iter=stack.iterator();
        while(iter.hasNext()){
            Integer i=iter.next();
            if(i>3)
                iter.remove();
        }
        String path="push.html";
        String regex="push.*";
        boolean ismatch=path.matches(regex);
        String password="admin123456";
        String result="9b71344edfcbe7781701f91e9aa2ee45";
        //--------------------------------------------------------------------------
        AesSecurate aesSecurate=new AesSecurate();
        byte[] e = "0123456789ABCDEF".getBytes("ASCII");
        SecretKeySpec spec = new SecretKeySpec(e, "AES");
        Cipher cipher2 = Cipher.getInstance("AES");
        cipher2.init(Cipher.DECRYPT_MODE, spec);
        aesSecurate.setDecryptCipher(cipher2);
        Cipher cipher=Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,spec);
        aesSecurate.setEncryptCipher(cipher);
        //--------------------------------------------------------------------------
        String newPassword=aesSecurate.decrypt(result);
        String plain=aesSecurate.encrypt(password);
        System.out.println(aesSecurate.encrypt("admin123456"));
        System.out.println(aesSecurate.encrypt("admin666"));
        System.out.println(aesSecurate.decrypt("5fe1d1267e9987b96fbe3021625520af"));
        System.out.println(aesSecurate.decrypt("e77f4c613c772e3450297317faa30bde"));
        for(int i=0;i<1024;i++){
            System.out.println(aesSecurate.decrypt("e77f4c613c772e3450297317faa30bde"));
        }
        //even though cipher is not thread safe, but we test that it can be use multiply times;
    }
}
