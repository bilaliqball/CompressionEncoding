package gzip;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;

import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;



public class Zip {

public static void main(String args[]) throws IOException {
compressFile(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.jpg"),new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.bin"));

uncompressFile(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.bin"));

//compressFile(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.H265"),new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.H265"));
}
	

public static void compressFile(File inputFileName,File outputFileName) throws IOException {
FileOutputStream fos = new FileOutputStream(outputFileName);
MyGZIPOutputStream gzos = null;
byte[] buffer = new byte[1024];
gzos = new MyGZIPOutputStream(fos);
gzos.setLevel(Deflater.BEST_SPEED);
long startTime = System.currentTimeMillis();              
FileInputStream fis = new FileInputStream(inputFileName);
int length;
while ((length = fis.read(buffer)) > 0) {gzos.write(buffer, 0, length);}
fis.close();gzos.close();
long endTime = System.currentTimeMillis();
System.out.println("Time taken to gzip "+ (endTime-startTime) + " miliseconds.");}

public static void uncompressFile(File file) throws IOException {
FileInputStream fin=new FileInputStream(file);
byte[] bytes=IOUtils.toByteArray(fin);
ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(bytes);
GZIPInputStream inputStream = new GZIPInputStream(arrayInputStream);
IOUtils.copy(inputStream,new FileOutputStream(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.jpg")));}


public static void zipFile(File file, File output) throws IOException {
byte[] buffer = new byte[1024];
try{
FileOutputStream fos = new FileOutputStream(output);
ZipOutputStream zos = new ZipOutputStream(fos){{def.setLevel(Deflater.BEST_SPEED);}};
ZipEntry ze= new ZipEntry(file.getName().toString());
zos.putNextEntry(ze);
FileInputStream in = new FileInputStream(file);
int len;while ((len = in.read(buffer)) > 0) {zos.write(buffer, 0, len);}
in.close();
zos.closeEntry();
zos.close();}
catch(IOException ex){ex.printStackTrace();}}
public static void unZipFile(File zipFile){
//String outputFolder=zipFile.getParent()+"\\"+zipFile.getName().substring(0, zipFile.getName().lastIndexOf('.'));
String outputFolder=zipFile.getParent();
byte[] buffer = new byte[1024];
try{
ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
ZipEntry ze = zis.getNextEntry();
while(ze!=null){
String fileName = ze.getName();
File newFile = new File(outputFolder+"\\_"+fileName);
System.out.println("file unzip : "+ newFile.getAbsoluteFile());
new File(newFile.getParent()).mkdirs();
FileOutputStream fos = new FileOutputStream(newFile);             
int len;while ((len = zis.read(buffer)) > 0) {fos.write(buffer, 0, len);}
fos.close();   
ze = zis.getNextEntry();}
zis.closeEntry();
zis.close();
System.out.println("Done");}
catch(IOException ex){ex.printStackTrace(); }}
}
