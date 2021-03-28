package gzip;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import org.apache.commons.io.IOUtils;

public class Zip1 {

public static void compress(File originalFile, File outFile) throws IOException {
FileInputStream fis=new FileInputStream(originalFile);
FileOutputStream fos=new FileOutputStream(outFile);
String content=IOUtils.toString(fis);
ByteArrayOutputStream baostream = new ByteArrayOutputStream();
OutputStream outStream = new GZIPOutputStream(fos);
outStream.write(content.getBytes("UTF-8"));
outStream.close();}

public static void uncompress(File inZippedFile, File outUnzippedFile) throws IOException {
FileInputStream fis=new FileInputStream(inZippedFile);
FileOutputStream fos=new FileOutputStream(outUnzippedFile);
byte[] compressedBytes=IOUtils.toByteArray(fis);
InputStream inStream = new GZIPInputStream(new ByteArrayInputStream(compressedBytes));
ByteArrayOutputStream baoStream2 = new ByteArrayOutputStream();
byte[] buffer = new byte[8192];
int len;
while ((len = inStream.read(buffer)) > 0) {baoStream2.write(buffer, 0, len);}
String uncompressedStr = baoStream2.toString("UTF-8");
fos.write(baoStream2.toByteArray());}

	
	
	
    
public static  void compressGZIP(File originalFile, File outFile) throws IOException {
FileInputStream fis=new FileInputStream(originalFile);
FileOutputStream fos=new FileOutputStream(outFile);
byte[] uncompressbytes = IOUtils.toByteArray(new FileReader(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.jpg")),StandardCharsets.UTF_8);
byte[] compressBytes= new byte[]{};
try (ByteArrayOutputStream bos = new ByteArrayOutputStream(uncompressbytes.length);
GZIPOutputStream gzipOS = new GZIPOutputStream(bos)) {
gzipOS.write(uncompressbytes );
gzipOS.close();
compressBytes = bos.toByteArray();
fos.write(compressBytes);fos.close();}}

public static  void decompressGZIP(File inZippedFile, File outUnzippedFile) throws IOException {
	FileInputStream fis=new FileInputStream(inZippedFile);
	FileOutputStream fos=new FileOutputStream(outUnzippedFile);
String content=IOUtils.toString(fis);
OutputStream os =new FileOutputStream(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.jpg"));
//	//GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(content.getBytes(StandardCharsets.ISO_8859_1)));
////InputStream is = new InputStream(new ByteArrayInputStream(content.getBytes(StandardCharsets.ISO_8859_1)));
//InputStream is = new GZIPInputStream( new BufferedInputStream(fis));
//byte[] bytes=IOUtils.toByteArray(is);
//os.write(bytes);

GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
BufferedReader bf = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8));
StringBuilder sb = null;
String line;
while ((line=bf.readLine())!=null) {sb.append(line);}
byte[] bytes=sb.toString().getBytes(StandardCharsets.UTF_8);
os.write(bytes);

}

public static void compressZIOS(File originalFile, File outFile) throws IOException {
FileInputStream fis=new FileInputStream(originalFile);
FileOutputStream fos=new FileOutputStream(outFile);
String content=IOUtils.toString(fis);
ByteArrayOutputStream out = new ByteArrayOutputStream();
GZIPOutputStream gzip = new GZIPOutputStream(out);
gzip.write(content.getBytes());gzip.close();fos.close();}
public static void decompressZIOS(File inZippedFile, File outUnzippedFile) throws IOException {
FileInputStream fis=new FileInputStream(inZippedFile);
FileOutputStream fos=new FileOutputStream(outUnzippedFile);
String content=IOUtils.toString(fis);
GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(content.getBytes()));
BufferedReader bf = new BufferedReader(new InputStreamReader(gis));
String outStr = "";
String line;
while ((line=bf.readLine())!=null) {outStr += line;}
byte[] bytes=outStr.getBytes();
fos.write(bytes);}
  
public static void compressGZIOS(File originalFile, File outFile) throws IOException {
FileInputStream fis = null;
FileOutputStream fos=null;
GZIPOutputStream zos = null;
OutputStreamWriter osw=null;
try {
fis = new FileInputStream(originalFile);
fos = new FileOutputStream(outFile);
zos = new GZIPOutputStream(fos);
osw = new OutputStreamWriter(zos,StandardCharsets.UTF_8);
IOUtils.copy(fis,osw);} 
finally {
IOUtils.closeQuietly(fis);IOUtils.closeQuietly(zos);
IOUtils.closeQuietly(osw);}}
public static void decompressGZIOS(File inZippedFile, File outUnzippedFile) throws IOException {
GZIPInputStream zis = null;
FileInputStream fis=null;
FileOutputStream fos=null;
InputStreamReader isr=null;
try {
fis = new FileInputStream(inZippedFile);
fos = new FileOutputStream(outUnzippedFile);
zis = new GZIPInputStream(fis);
isr = new InputStreamReader(zis,"UTF8");
IOUtils.copy(zis, fos);} 
finally {
IOUtils.closeQuietly(zis);IOUtils.closeQuietly(fos);
IOUtils.closeQuietly(isr);}}

public static void compressFile(File originalFile, File outFile) throws IOException {
FileInputStream fis = null;
GZIPOutputStream zos = null;
FileOutputStream fos=null;
try {
fis = new FileInputStream(originalFile);
fos = new FileOutputStream(outFile);
zos = new GZIPOutputStream(fos);
IOUtils.copy(fis, zos);} 
finally {
IOUtils.closeQuietly(fis);IOUtils.closeQuietly(zos);
IOUtils.closeQuietly(fos);}}
public static void decompressFile(File inZippedFile, File outUnzippedFile) throws IOException {
GZIPInputStream zis = null;
FileOutputStream fos =null;
try {
FileInputStream fis = new FileInputStream(inZippedFile);
zis = new GZIPInputStream(fis);
fos = new FileOutputStream(outUnzippedFile);
IOUtils.copy(zis, fos);} 
finally {
IOUtils.closeQuietly(zis);IOUtils.closeQuietly(fos);}}
    
public static void main(String[] args) throws IOException {
File input = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.jpg");
File bin = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.txt");
File decompress = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.jpg");
compressFile(input,bin);decompressFile(bin,decompress);
      }
     
public static void enc() {
try {
String inputString = reads(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.mp4"));
byte[] input = inputString.getBytes("UTF-8");
int s=input.length;
byte[] output = new byte[s];
Deflater compresser = new Deflater();//compresser.setLevel(9);
compresser.setInput(input);
int compressedDataLength = compresser.deflate(output);
compresser.end();    compresser.finish();
Inflater decompresser = new Inflater();
decompresser.setInput(output, 0, compressedDataLength);
byte[] result = new byte[s];
int resultLength = decompresser.inflate(result);
decompresser.end();
String outputString = new String(result, 0, resultLength, "UTF-8");
writes(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.mp4"),outputString);} 
catch(java.io.UnsupportedEncodingException ex) {} 
catch (java.util.zip.DataFormatException ex) {}}
     
 public static void writes(File file,String content){
 try {
 Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
 out.append(content);
 out.flush();
 out.close();} 
 catch (UnsupportedEncodingException e) {System.out.println(e.getMessage());} 
 catch (IOException e) {System.out.println(e.getMessage());}
 catch (Exception e){System.out.println(e.getMessage());}}	
     
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
    
  
}
