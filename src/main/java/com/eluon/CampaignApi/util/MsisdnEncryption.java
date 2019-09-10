package com.eluon.CampaignApi.util;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MsisdnEncryption
{

    private RC4 rc4;

    public MsisdnEncryption(String secretKey){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(secretKey.getBytes());

            byte secretKeyMd5Byte[] = md.digest();
            rc4 = new RC4(secretKeyMd5Byte);

        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }

    public String encrypt(String msisdn){
        byte[] encrypted = rc4.encrypt(msisdn.getBytes());
        return DatatypeConverter.printBase64Binary(encrypted);
    }

    public String decrypt(String chiper){

        byte[] decryptedBase64 = DatatypeConverter.parseBase64Binary(chiper);
        byte[] decrypted = rc4.decrypt(decryptedBase64);
        return new String(decrypted);
    }
   /*

    public static void main(String[] args){

        if(args!=null) System.out.println("!=null:"+args);
        else System.out.println("null");

        if(args==null || (args.length >=1 && "e".equalsIgnoreCase(args[0])))
        {

            String originalMsisdn = "62";//"61423991119";
            String secretKey = "testing";

            if(args!=null && args.length >=2)
                originalMsisdn = args[1];
            if(args!=null && args.length >=3)
                secretKey = args[2];

            System.out.println("----------------------------------------------");
            System.out.println("MSISDN ENCRYPTION");
            System.out.println("----------------------------------------------");
            System.out.println("Original Msisdn: " + originalMsisdn);
            System.out.println("Secret Key: " + secretKey);

            System.out.println("----------------------------------------------");
            MsisdnEncryption me = new MsisdnEncryption(secretKey);
            String meEncrypt = me.encrypt(originalMsisdn);
            System.out.println("Encrypted: " + meEncrypt);
            System.out.println("----------------------------------------------");

        } else {

            String encryptedText = "nzbwqrDnLOwAaG8=";
            String secretKey = "testing";

            if(args.length >=2)
                encryptedText = args[1];
            if(args.length >=3)
                secretKey = args[2];

            System.out.println("----------------------------------------------");
            System.out.println("MSISDN DECRYPTION");
            System.out.println("----------------------------------------------");
            System.out.println("Encrypted Text: " + encryptedText);
            System.out.println("Secret Key: " + secretKey);

            System.out.println("----------------------------------------------");
            MsisdnEncryption me = new MsisdnEncryption(secretKey);
            String meEncrypt = me.decrypt(encryptedText);
            System.out.println("Decrypted: " + meEncrypt);
            System.out.println("----------------------------------------------");
        }
    }
   * */
}
