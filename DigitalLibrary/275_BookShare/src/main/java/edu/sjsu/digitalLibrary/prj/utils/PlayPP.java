package edu.sjsu.digitalLibrary.prj.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 

public class PlayPP {

	public  static String  sha1(String sp) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(sp.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         System.out.println(sb);
        return sb.toString();
    }
	
}