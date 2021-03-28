package security;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.io.IOUtils;

public class Encription {
public static void main(String args[]) throws IOException {
File inp=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.png");
File bsf=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\bsf.bin");compressBSIOS(inp,bsf);
File dec=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.png");decompressBSIOS(bsf,dec);
System.out.println("...");}
	

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





	
}
