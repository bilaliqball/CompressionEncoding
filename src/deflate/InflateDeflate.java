package deflate;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;  
 import java.io.File;  
 import java.io.FileInputStream;  
 import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;  
 import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;  
 import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;  
 import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.io.IOUtils;  
 public class InflateDeflate {  

	 public static void main(String args[]) throws IOException, DataFormatException {
 File input = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.jpg");
 File bin = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.bin");
 File decompress = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.jpg");

     	
     	
     	
     	
 String content=read(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.bin"));
 byte[] data = content.getBytes("UTF-8");
 byte[] comp=compress(data);
 OutputStream combytes = new FileOutputStream(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\com_.bin")); 
 OutputStream decbytes = new FileOutputStream(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\dec_.bin")); 
 combytes .write(comp); 
	 
 byte[] dec=decompress(comp);
 decbytes .write(dec); 
 decompressBSIOS(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\dec_.bin"),new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\dec_.jpg"));
	 }
	 
	 
 public static String read(File file)throws IOException{
 String line;
 StringBuilder sb = new StringBuilder();
 try (BufferedReader br = new BufferedReader(new FileReader(file))) {
 while((line = br.readLine())!=null) {
 sb.append(line);}br.close();}
 catch (IOException e) {e.printStackTrace();}
 return sb.toString();}
 public static String readContent(FileInputStream fis) throws IOException{
 try( BufferedReader br =new BufferedReader( new InputStreamReader(fis))){
 StringBuilder sb = new StringBuilder();String line;
 while(( line = br.readLine()) != null ) {
 sb.append( line );}
 return sb.toString();}}
 public static void write(File file,String content) {
 try (BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))) {
 bw.write(content);
 bw.close();
 } catch (IOException e) {e.printStackTrace();}}
 public static void writeCOntent(File file, String content) throws IOException {
 FileWriter writer = new FileWriter(file);
 try(PrintWriter printWriter =new PrintWriter(writer)){
 printWriter.write(content);}}
	 
 public static void infDef() throws DataFormatException, UnsupportedEncodingException {
 String message = "Welcome to TutorialsPoint.com;";
 System.out.println("Original Message length : " + message.length());
 byte[] input = message.getBytes("UTF-8");
 // Compress the bytes
 byte[] output = new byte[1024];
 Deflater deflater = new Deflater();
 deflater.setInput(input);
 deflater.finish();
 int compressedDataLength = deflater.deflate(output);
 deflater.end();
 System.out.println("Compressed Message length : " + compressedDataLength);
 String com=new String(output, 0, compressedDataLength, "UTF-8");
 System.out.println(com);
 // Decompress the bytes
 Inflater inflater = new Inflater();
 inflater.setInput(output, 0, compressedDataLength);
 byte[] result = new byte[1024];
 int resultLength = inflater.inflate(result);
 inflater.end();
 message = new String(result, 0, resultLength, "UTF-8");
 System.out.println("UnCompressed Message length : " + message.length());}

 public static void infDef2() throws Exception {
 String content=read(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.bin"));
 File com = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\com_.bin"); 
 File dec = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\dec_.bin"); 

 ByteArrayOutputStream baos = new ByteArrayOutputStream();
 DeflaterOutputStream dos = new DeflaterOutputStream(baos);
 dos.write(content.getBytes());
 dos.flush();
 dos.close();

 ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
 InflaterInputStream iis = new InflaterInputStream(bais);

 String result = "";
 byte[] buf = new byte[1024];
 int rlen = -1;
 while ((rlen = iis.read(buf)) != -1) {
 result += new String(Arrays.copyOf(buf, rlen));}
 System.out.println("Decompress result: " + result);}
 
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
 IOUtils.copy(zis, fos);} 
 finally {
 IOUtils.closeQuietly(zis);
 IOUtils.closeQuietly(osw);}}
 
public static byte[] compress(byte[] data) throws IOException {  
Deflater deflater = new Deflater();  
deflater.setInput(data);  
ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);   
deflater.finish();  
byte[] buffer = new byte[1024];   
while (!deflater.finished()) {  
int count = deflater.deflate(buffer); // returns the generated code... index  
outputStream.write(buffer, 0, count);}  
outputStream.close();  
byte[] output = outputStream.toByteArray();  
System.out.println("Original: " + data.length / 1024 + " Kb");  
System.out.println("Compressed: " + output.length / 1024 + " Kb");  
return output;}  
  
public static byte[] decompress(byte[] data) throws IOException, DataFormatException {  
Inflater inflater = new Inflater();   
inflater.setInput(data);  
ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);  
byte[] buffer = new byte[1024];  
while (!inflater.finished()) {  
int count = inflater.inflate(buffer);  
outputStream.write(buffer, 0, count);  }  
outputStream.close();  
byte[] output = outputStream.toByteArray();  
System.out.println("Original: " + data.length);  
System.out.println("Compressed: " + output.length);  
return output;  } 
  
  
 }