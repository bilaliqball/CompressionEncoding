package commons;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.codec.binary.Base32InputStream;
import org.apache.commons.codec.binary.Base32OutputStream;

import org.apache.commons.io.IOUtils;

public class Encodec {

public static void main(String[] args) throws IOException {
File input = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\apple.jpg");
File bin = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.txt");
File decompress = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.jpg");
	


//compressBTIOS(input,bin);decompressBTIOS(bin,decompress);
//compressBSIOS(input,bin);decompressBSIOS(bin,decompress);
//compressGZIOS(input,bin);decompressGZIOS(bin,decompress);
//compressBZIOS(input,bin);decompressBZIOS(bin,decompress);
//zipFiles(input);unZipFile(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\fileZip.zip"));
//inptoBsf(input, bsf);bsftoZip(bsf,bin);ziptoBsf(bin, _bsf);bsftoOut(_bsf,decompress);
}

//BTT
public static void compressBTIOS(File originalFile, File outFile) throws IOException {
FileInputStream fis = null;
FileOutputStream fos=null;
Base32OutputStream bos=null;
GZIPOutputStream zos = null;
try {
fis = new FileInputStream(originalFile);
fos = new FileOutputStream(outFile);
bos = new Base32OutputStream(fos);
zos = new GZIPOutputStream(bos);
IOUtils.copy(fis, zos);} 
finally {
IOUtils.closeQuietly(fis);
IOUtils.closeQuietly(zos);}}
public static void decompressBTIOS(File inZippedFile, File outUnzippedFile) throws IOException {
GZIPInputStream zis = null;
OutputStreamWriter osw = null;
FileInputStream fis=null;
Base32InputStream bis;
FileOutputStream fos=null;
try {
fis = new FileInputStream(inZippedFile);
fos = new FileOutputStream(outUnzippedFile);
bis = new Base32InputStream(fis);
zis = new GZIPInputStream(bis);
IOUtils.copy(zis, fos);} 
finally {
IOUtils.closeQuietly(zis);
IOUtils.closeQuietly(osw);}}


//BSF
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

//Deflate
public static void compressZLIOS(File raw, File compressed)throws IOException{
InputStream in = new FileInputStream(raw);
OutputStream out =new DeflaterOutputStream(new FileOutputStream(compressed));
shovelInToOut(in, out);in.close();out.close();}
public static void decompressZLIOS(File compressed, File raw) throws IOException{
InputStream in =new InflaterInputStream(new FileInputStream(compressed));
OutputStream out = new FileOutputStream(raw);
shovelInToOut(in, out);
in.close();
out.close();}
private static void shovelInToOut(InputStream in, OutputStream out) throws IOException{
byte[] buffer = new byte[1000];
int len;
while((len = in.read(buffer)) > 0) {out.write(buffer, 0, len);}}

//GZIP
public static void compressGZIOS(File originalFile, File outFile) throws IOException {
FileInputStream fis = null;
FileOutputStream fos=null;
GZIPOutputStream zos = null;
try {
fis = new FileInputStream(originalFile);
fos = new FileOutputStream(outFile);
zos = new GZIPOutputStream(fos);
IOUtils.copy(fis, zos);} 
finally {
IOUtils.closeQuietly(fis);
IOUtils.closeQuietly(zos);}}
public static void decompressGZIOS(File inZippedFile, File outUnzippedFile) throws IOException {
GZIPInputStream zis = null;
OutputStreamWriter osw = null;
FileInputStream fis=null;
FileOutputStream fos=null;
try {
fis = new FileInputStream(inZippedFile);
fos = new FileOutputStream(outUnzippedFile);
zis = new GZIPInputStream(fis);
IOUtils.copy(zis, fos);} 
finally {
IOUtils.closeQuietly(zis);
IOUtils.closeQuietly(osw);}}

//BZIP
public static void compressBZIOS(File input,File output) throws IOException {
File bsf=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\bsf.txt");
inptoBsf(input,bsf);bsftoZip(bsf,output);bsf.delete();}
public static void decompressBZIOS(File input,File output) throws IOException {
File con=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\bsf_.txt");
ziptoBsf(input,con);bsftoOut(con,output);con.delete();}
public static void inptoBsf(File originalFile, File outFile) throws IOException {
FileInputStream fis = null;
FileOutputStream fos=null;
Base64OutputStream bos=null;
try {
fis = new FileInputStream(originalFile);
fos = new FileOutputStream(outFile);
bos = new Base64OutputStream(fos);
IOUtils.copy(fis, bos);} 
finally {
IOUtils.closeQuietly(fis);
IOUtils.closeQuietly(bos);}}
public static void bsftoZip(File originalFile, File outFile) throws IOException {
FileInputStream fis = null;
FileOutputStream fos=null;
GZIPOutputStream zos = null;
try {
fis = new FileInputStream(originalFile);
fos = new FileOutputStream(outFile);
zos = new GZIPOutputStream(fos);
IOUtils.copy(fis, zos);} 
finally {
IOUtils.closeQuietly(fis);
IOUtils.closeQuietly(zos);}}
public static void ziptoBsf(File inZippedFile, File outUnzippedFile) throws IOException {
GZIPInputStream zis = null;
FileInputStream fis=null;
FileOutputStream fos=null;
try {
fis = new FileInputStream(inZippedFile);
fos = new FileOutputStream(outUnzippedFile);
zis = new GZIPInputStream(fis);
IOUtils.copy(zis, fos);} 
finally {
IOUtils.closeQuietly(zis);
IOUtils.closeQuietly(fos);}}
public static void bsftoOut(File inZippedFile, File outUnzippedFile) throws IOException {
Base64InputStream bis=null;
FileInputStream fis=null;
FileOutputStream fos=null;
try {
fis = new FileInputStream(inZippedFile);
fos = new FileOutputStream(outUnzippedFile);
bis = new Base64InputStream(fis);
IOUtils.copy(bis, fos);} 
finally {
IOUtils.closeQuietly(bis);
IOUtils.closeQuietly(fos);}}

//SZIP
public static void zipFiles(File dir) throws IOException {
String outputZipFile = null;
if(dir.isDirectory()) {outputZipFile=dir.toString()+"\\"+dir.getName()+"Zip.zip";zipDir(dir,outputZipFile);}
else if(dir.isFile()==true){outputZipFile=dir.getParent()+"\\"+dir.getName().substring(0, dir.getName().lastIndexOf('.'))+"Zip.zip"; zipFile(dir,outputZipFile);}}
public static void zipFile(File file, String filename) throws IOException {
String outputZipFile = filename;
byte[] buffer = new byte[1024];
try{
FileOutputStream fos = new FileOutputStream(outputZipFile);
ZipOutputStream zos = new ZipOutputStream(fos);
ZipEntry ze= new ZipEntry(file.getName().toString());
zos.putNextEntry(ze);
FileInputStream in = new FileInputStream(file);
int len;while ((len = in.read(buffer)) > 0) {zos.write(buffer, 0, len);}
in.close();
zos.closeEntry();
zos.close();}
catch(IOException ex){ex.printStackTrace();}}
public static void zipDir(File dir, String filename) throws IOException {
String outputZipFile =filename;
byte[] buffer = new byte[1024];
try{
File files[]=dir.listFiles();
FileOutputStream fos = new FileOutputStream(outputZipFile);
ZipOutputStream zos = new ZipOutputStream(fos);
for(File file:files) {
ZipEntry ze= new ZipEntry(file.getName().toString());
zos.putNextEntry(ze);
FileInputStream in = new FileInputStream(file);System.out.println(file);
int len;while ((len = in.read(buffer)) > 0) {zos.write(buffer, 0, len);}
in.close();}
zos.closeEntry();
zos.close();}
catch(IOException ex){ex.printStackTrace();}}
public static void unZipFile(File zipFile){
String outputFolder=zipFile.getParent()+"\\"+zipFile.getName().substring(0, zipFile.getName().lastIndexOf('.'));
byte[] buffer = new byte[1024];
try{
ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
ZipEntry ze = zis.getNextEntry();
while(ze!=null){
String fileName = ze.getName();
File newFile = new File(outputFolder+"\\"+fileName);
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
