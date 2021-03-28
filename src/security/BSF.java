package security;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class BSF {
	
public static void main(String args[]) throws IOException {
File inp=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.mp4");
String filename=inp.getName();String ext=filename.substring(filename.lastIndexOf('.'));
String enc=inp.getParent()+"\\"+filename.substring(0, filename.lastIndexOf('.'))+ext+".bin";encode(inp,new File(enc));
String dec=inp.getParent()+"\\"+filename.substring(0, filename.lastIndexOf('.'))+ext+ext;decode(new File(enc),new File(dec));

//encode(inp,new File(enc));
//decode(new File(enc),new File(dec));
}

//public static void encdecImage() {
//BufferedImage img = null;
//try{img = ImageIO.read(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.png"));}catch(IOException e){System.out.println(e);}
//String str=encode(img,"jpg");
//decode(str);}
//public static void writeline(String content) {
//try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.txt"),true))) {
//bw.write(content);bw.newLine();
//bw.close();} 
//catch (IOException e) {e.printStackTrace();}}
//
//public static String encode(BufferedImage image, String type) {
//String imageString = null;
//ByteArrayOutputStream bos = new ByteArrayOutputStream();
//try {
//ImageIO.write(image, type, bos);
//byte[] imageBytes = bos.toByteArray();
//BASE64Encoder encoder = new BASE64Encoder();
//imageString = encoder.encode(imageBytes);
//bos.close();} 
//catch (IOException e) { e.printStackTrace();}
//writeline(imageString);
//return imageString;}
//	
//public static void  decode(String imageString) {
//BufferedImage image = null;
//byte[] imageByte;
//try {
//BASE64Decoder decoder = new BASE64Decoder();
//imageByte = decoder.decodeBuffer(imageString);
//ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
//image = ImageIO.read(bis);
//bis.close();} 
//catch (Exception e) {e.printStackTrace();}
//try{ImageIO.write(image, "jpg", new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.png"));}
//catch(IOException e){System.out.println(e);}} 




public static void writeContent(File file,String content) {
try {
BufferedWriter bw = new BufferedWriter(new FileWriter(file));
bw.write(content);
bw.close();} catch (IOException e) {e.printStackTrace();}}


public static void encode(File inp,File out) throws IOException {
InputStream is=new FileInputStream(inp);
byte[] bytes = IOUtils.toByteArray(is);
BASE64Encoder encoder = new BASE64Encoder();
String bsfstr = encoder.encode(bytes);
//String filename=inp.getName();
//String out=inp.getParent()+"\\"+filename.substring(0, filename.lastIndexOf('.'))+".bin";
writeContent(out,bsfstr);}
	
public static void  decode(File inp, File out) throws IOException {
InputStream is=new FileInputStream(inp);
String bsfstr = IOUtils.toString(is);
try {
BASE64Decoder decoder = new BASE64Decoder();
byte[] bsfbyte = decoder.decodeBuffer(bsfstr);
ByteArrayInputStream bis = new ByteArrayInputStream(bsfbyte);
//String filename=inp.getName();
//String out=inp.getParent()+"\\"+filename.substring(0, filename.lastIndexOf('.'))+"_.mp4";
OutputStream os=new FileOutputStream(out);
os.write(bsfbyte);
os.close();bis.close();} 
catch (Exception e) {e.printStackTrace();}} 


public static void encodeString(File inp,File out) throws IOException {
InputStream is=new FileInputStream(inp);
byte[] bytes = IOUtils.toByteArray(is);
BASE64Encoder encoder = new BASE64Encoder();
String bsfstr = encoder.encode(bytes);
//String filename=inp.getName();
//String out=inp.getParent()+"\\"+filename.substring(0, filename.lastIndexOf('.'))+".bin";
writeContent(out,bsfstr);}
	
public static void  decodeString(File inp, File out) throws IOException {
InputStream is=new FileInputStream(inp);
String bsfstr = IOUtils.toString(is);
try {
BASE64Decoder decoder = new BASE64Decoder();
byte[] bsfbyte = decoder.decodeBuffer(bsfstr);
ByteArrayInputStream bis = new ByteArrayInputStream(bsfbyte);
//String filename=inp.getName();
//String out=inp.getParent()+"\\"+filename.substring(0, filename.lastIndexOf('.'))+"_.mp4";
OutputStream os=new FileOutputStream(out);
os.write(bsfbyte);
os.close();bis.close();} 
catch (Exception e) {e.printStackTrace();}} 
    
    

}
