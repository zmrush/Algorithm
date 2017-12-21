package com.mz;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by mingzhu7 on 2017/12/7.
 */
public class DesSecurate {
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
    public static void main(String[] args) throws Exception{
        String password="123456";
        byte[] e = "0123456789ABCDEF".getBytes("ASCII");
        SecretKeySpec spec = new SecretKeySpec(e, "AES");
        String result="9b71344edfcbe7781701f91e9aa2ee45";
//--------------------------------------------------------------------------
        Cipher cipher2 = Cipher.getInstance("AES");
        cipher2.init(Cipher.DECRYPT_MODE, spec);
        byte[] resultInv=hexTobyte(result);
        byte[] passwordinv=process(resultInv,cipher2);
        String newPassword=new String(passwordinv);
        System.out.println(newPassword);
    }
}
