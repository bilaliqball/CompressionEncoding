package commons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.codec.binary.Base32OutputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.io.IOUtils;

public class Encoder {
public static void main(String args[]) throws Exception {
File inp=new File("C:\\Users\\bilal.iqbal\\Pictures\\InputFiles\\iceberg.bmp");
File bsf=new File("C:\\Users\\bilal.iqbal\\Pictures\\InputFiles\\iceberg.txt");toBSF(inp,bsf);

//File dec=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\dec.bin");toDEC(inp,dec);
//File hex=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\hex.bin");toHEX(inp,hex);
//File btt=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\btt.bin");toBTT(inp,btt);
//File bsf=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\bsf.bin");toBSF(inp,bsf);
//File ltn=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\ltn.bin");toLTN(inp,ltn);

//File utf8=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\utf8.bin");toLTN(inp,utf8);
//File utf16=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\utf16.bin");toLTN(inp,utf16);
//File iso=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\iso.bin");toLTN(inp,iso);
//File asc=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\asc.bin");toLTN(inp,asc);







System.out.println("...");

}







public static void toDEC(File originalFile, File outFile) throws IOException {
FileInputStream fis=new FileInputStream(originalFile);
byte[] bytes = IOUtils.toByteArray(fis);
StringBuilder sb = new StringBuilder();
for (byte b : bytes) {sb.append(Math.abs((int)b));sb.append(" ");}
try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile,true))) {
bw.write(sb.toString());
bw.close();
} catch (IOException e) {e.printStackTrace();}}
public static void toHEX(File originalFile, File outFile) throws IOException {
FileInputStream fis=new FileInputStream(originalFile);
byte[] bytes = IOUtils.toByteArray(fis);
String hex=org.apache.commons.codec.binary.Hex.encodeHexString(bytes);
try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile,true))) {
bw.write(hex);
bw.close();
} catch (IOException e) {e.printStackTrace();}}
public static void toBTT(File originalFile, File outFile) throws IOException {
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
public static void toBSF(File originalFile, File outFile) throws IOException {
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

public static void toLTN(File input,File output){
try {
FileInputStream fis=new FileInputStream(input);
byte[] bytes = IOUtils.toByteArray(fis);
String s=new String(bytes,StandardCharsets.UTF_16);
Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output),StandardCharsets.UTF_16));
out.append(s);
out.flush();
out.close();} 
catch (UnsupportedEncodingException e) {System.out.println(e.getMessage());} 
catch (IOException e) {System.out.println(e.getMessage());}
catch (Exception e){System.out.println(e.getMessage());} }


public static void toUTF8(File input,File output){
try {
FileInputStream fis=new FileInputStream(input);
String content=IOUtils.toString(fis, StandardCharsets.UTF_8);
byte[] bytes = IOUtils.toByteArray(fis);String s=new String(bytes,StandardCharsets.UTF_8);
Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output),StandardCharsets.UTF_8));
out.append(s);
out.flush();
out.close();} 
catch (UnsupportedEncodingException e) {System.out.println(e.getMessage());} 
catch (IOException e) {System.out.println(e.getMessage());}
catch (Exception e){System.out.println(e.getMessage());} }

public static void toUTF16(File input,File output){
try {
FileInputStream fis=new FileInputStream(input);
String content=IOUtils.toString(fis, StandardCharsets.UTF_16);
byte[] bytes = IOUtils.toByteArray(fis);String s=new String(bytes,StandardCharsets.UTF_16);
Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output),StandardCharsets.UTF_16));
out.append(s);
out.flush();
out.close();} 
catch (UnsupportedEncodingException e) {System.out.println(e.getMessage());} 
catch (IOException e) {System.out.println(e.getMessage());}
catch (Exception e){System.out.println(e.getMessage());} }


public static void toISO(File input,File output){
try {
FileInputStream fis=new FileInputStream(input);
String content=IOUtils.toString(fis, StandardCharsets.ISO_8859_1);
byte[] bytes = IOUtils.toByteArray(fis);String s=new String(bytes,StandardCharsets.ISO_8859_1);
Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output),StandardCharsets.ISO_8859_1));
out.append(s);
out.flush();
out.close();} 
catch (UnsupportedEncodingException e) {System.out.println(e.getMessage());} 
catch (IOException e) {System.out.println(e.getMessage());}
catch (Exception e){System.out.println(e.getMessage());} }

public static void toASC(File input,File output){
try {
FileInputStream fis=new FileInputStream(input);
String content=IOUtils.toString(fis, StandardCharsets.US_ASCII);
byte[] bytes = IOUtils.toByteArray(fis);String s=new String(bytes,StandardCharsets.US_ASCII);
Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output),StandardCharsets.US_ASCII));
out.append(s);
out.flush();
out.close();} 
catch (UnsupportedEncodingException e) {System.out.println(e.getMessage());} 
catch (IOException e) {System.out.println(e.getMessage());}
catch (Exception e){System.out.println(e.getMessage());} }


}

