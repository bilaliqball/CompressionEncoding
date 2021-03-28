package security;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.io.IOUtils;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
 
public class SHA {
	
public static void main(String args[]) throws NoSuchAlgorithmException, IOException {

String originalString = "password";
//String originalString = reads(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\bsf.bin"));

//InputStream inputStream = new FileInputStream(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.png"));
//String originalString = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());

	
System.out.println("Original String to encrypt - " + originalString);
String encryptedString = encrypt(originalString);
System.out.println("Encrypted String - " + encryptedString);
String decryptedString = decrypt(encryptedString);
System.out.println("After decryption - " + decryptedString);
//write(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\dec.bin"),decryptedString);
//decompressBSIOS(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\dec.bin"),new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.mp4"));

//FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.png"));
//OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
//IOUtils.copy(zis, osw);
//fos.write(decryptedString.getBytes("UTF-8"));
}

public static String readContent(FileInputStream fis) throws IOException{
try( BufferedReader br =new BufferedReader( new InputStreamReader(fis))){
StringBuilder sb = new StringBuilder();String line;
while(( line = br.readLine()) != null ) {
sb.append( line );}
return sb.toString();}}



public static String reads(File file){
 String str="";StringBuilder sb = new StringBuilder();
 try {
 BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
 while ((str = in.readLine()) != null) {System.out.println(str);sb.append(str);}
 in.close();} 
 catch (UnsupportedEncodingException e) {System.out.println(e.getMessage());} 
 catch (IOException e) {System.out.println(e.getMessage());}
 catch (Exception e){System.out.println(e.getMessage());}
 return sb.toString();}

public static void write(File file,String content) {
try (BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))) {
bw.write(content);
bw.close();
} catch (IOException e) {e.printStackTrace();}}

	
public static void compressBSIOS(File originalFile, File outFile) throws IOException {
FileInputStream fis = null;
FileOutputStream fos=null;
Base64OutputStream bos=null;
GZIPOutputStream zos = null;
try {
fis = new FileInputStream(originalFile);
fos = new FileOutputStream(outFile);
bos = new Base64OutputStream(fos);
zos = new GZIPOutputStream(bos);
IOUtils.copy(fis, zos);} 
finally {
IOUtils.closeQuietly(fis);
IOUtils.closeQuietly(zos);}}
	
public static void decompressBSIOS(File inZippedFile, File outUnzippedFile) throws IOException {
GZIPInputStream zis = null;
OutputStreamWriter osw = null;
FileInputStream fis=null;
Base64InputStream bis;
FileOutputStream fos=null;
try {
fis = new FileInputStream(inZippedFile);
fos = new FileOutputStream(outUnzippedFile);
bis = new Base64InputStream(fis);
zis = new GZIPInputStream(bis);
IOUtils.copy(bis, fos);} 
finally {
IOUtils.closeQuietly(zis);
IOUtils.closeQuietly(osw);}}
	

//public static String hash256(String data) throws NoSuchAlgorithmException {
//MessageDigest digest = MessageDigest.getInstance("SHA-256");
//byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
//StringBuffer result = new StringBuffer();
//for (byte byt : hash) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
//return result.toString();}
//public static void hash256() throws NoSuchAlgorithmException {
//String sha;
//String line;
//String FILENAME = "C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\bsf.txt";
//try (BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(FILENAME)))) {
//while((line=br.readLine())!=null) {
//sha=hash256(line);
//writeline(sha);}}
//catch (IOException e) {e.printStackTrace();}
//}
//public static void writeline(String content) {
//String FILENAME = "C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\sha.txt";
//try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME,true))) {
//bw.write(content);bw.newLine();
//bw.close();
//} catch (IOException e) {e.printStackTrace();}}


private static final String key = "aesEncryptionKey";
private static final String initVector = "encryptionIntVec";
 
public static String encrypt(String value) {
try {
IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
 Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
 byte[] encrypted = cipher.doFinal(value.getBytes());
//return Base64.encodeBase64String(encrypted);
BASE64Encoder encoder = new BASE64Encoder();
String bsfString = encoder.encode(encrypted);
return bsfString;} 
catch (Exception ex) {ex.printStackTrace();}
return null;}

public static String decrypt(String encrypted) {
try {
IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
BASE64Decoder decoder = new BASE64Decoder();
byte[] imageByte = decoder.decodeBuffer(encrypted);
//byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
byte[] original = cipher.doFinal(imageByte);
return new String(original);} 
catch (Exception ex) {ex.printStackTrace();}
return null;}

}
