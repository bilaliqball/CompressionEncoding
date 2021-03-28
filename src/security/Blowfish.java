package security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Blowfish {
	
public static void main(String args[]) throws Exception {
String text="AAAAGGZ0eXBpc29tAAAAAG1wNDFhdmMxAAA0mm1vb3YAAABsbXZoZAAAAADYT0lQ2E9JUAABX5AA";
String enc=encrypt(text,"Blowfish");
String dec=decrypt(enc,"Blowfish");
System.out.println("enc:" +enc);
System.out.println("dec:"+dec);
if(text.equals(dec)) {System.out.println("Equals");}
}

public static String encrypt(String strClearText,String strKey) throws Exception{
String strData="";
try {
SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes(),"Blowfish");
Cipher cipher=Cipher.getInstance("Blowfish");
cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
byte[] encrypted=cipher.doFinal(strClearText.getBytes());
strData=new String(encrypted);} 
catch (Exception e) {e.printStackTrace();throw new Exception(e);}
return strData;}
	 
public static String decrypt(String strEncrypted,String strKey) throws Exception{
String strData="";
try {
SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes(),"Blowfish");
Cipher cipher=Cipher.getInstance("Blowfish");
cipher.init(Cipher.DECRYPT_MODE, skeyspec);
byte[] decrypted=cipher.doFinal(strEncrypted.getBytes());
strData=new String(decrypted);} 
catch (Exception e) {e.printStackTrace();throw new Exception(e); }
return strData;}
}
