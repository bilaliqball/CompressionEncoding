package lzwi;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.io.IOUtils;
 
public class LZW1 {
/** Compress a string to a list of output symbols. */
public static List<Integer> compress(String uncompressed) {
// Build the dictionary.
int dictSize = 256;
Map<String,Integer> dictionary = new HashMap<String,Integer>();
for (int i = 0; i < 256; i++)
dictionary.put("" + (char)i, i);
 
String w = "";
List<Integer> result = new ArrayList<Integer>();
for (char c : uncompressed.toCharArray()) {
String wc = w + c;
if (dictionary.containsKey(wc))
w = wc;
else {
result.add(dictionary.get(w));
// Add wc to the dictionary.
dictionary.put(wc, dictSize++);
w = "" + c;}}
 
// Output the code for w.
if (!w.equals(""))
result.add(dictionary.get(w));
return result;}
 
/** Decompress a list of output ks to a string. */
public static String decompress(List<Integer> compressed) {
// Build the dictionary.
int dictSize = 256;
Map<Integer,String> dictionary = new HashMap<Integer,String>();
for (int i = 0; i < 256; i++)
dictionary.put(i, "" + (char)i);
String w = "" + (char)(int)compressed.remove(0);
StringBuffer result = new StringBuffer(w);
for (int k : compressed) {
String entry;
if (dictionary.containsKey(k))
entry = dictionary.get(k);
else if (k == dictSize)
entry = w + w.charAt(0);
else
throw new IllegalArgumentException("Bad compressed k: " + k);
result.append(entry);
 
// Add w+entry[0] to the dictionary.
dictionary.put(dictSize++, w + entry.charAt(0));
w = entry;}
return result.toString();}
 
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


	

	
public static String toHex(int i){
String hex = Integer.toHexString(i);
     if(hex.length()==0) {hex="000";}
else if(hex.length()==1) {hex="00"+hex;}
else if(hex.length()==2) {hex="0"+hex;}
System.out.println("Hex value: "+hex +" "+hex.length());
return hex;}
	
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

public static void main(String[] args) throws IOException {
//String content=read(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\bsf.bin"));
//List<Integer> compressed = compress(content);
//StringBuilder sb=new StringBuilder();
//for(int i=0;i<compressed.size();i++) {
//		sb.append(compressed.get(i));sb.append(" ");
//	
//}
//System.out.println(compressed);write(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\comp1.txt"),sb.toString());
//System.out.println(compressed);write(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\comp.txt"),compressed.toString());
//String decompressed = decompress(compressed);
//System.out.println(decompressed);write(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\decomp.txt"),decompressed);
    
	
//decompressBSIOS(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\decomp.txt"),new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\decomp.jpg"));
//InputStream fis=new FileInputStream(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\decomp.txt"));
//OutputStream fos=new FileOutputStream(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\decomp.jpg"));
//Base64InputStream bis = new Base64InputStream(fis);
//IOUtils.copy(bis, fos);
	
	
	
	
	

	
	
File input = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.jpg");
File bin = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.bin");
File decompress = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.jpg");
compressBSIOS(input,bin);
    	
    	
    	
    	
String content=read(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.bin"));
List<Integer> compressed = compress(content);
StringBuilder sb = new StringBuilder();
//System.out.println(compressed);
 //write(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\compressed.bin"),compressed.toString());
       
    	
for(int i=0;i<compressed.size();i++) {
int code=compressed.get(i);
String hex=Integer.toHexString(code);
sb.append(hex);sb.append(' ');}
    	
String compressedString=sb.toString();
List<Integer> compressedlist=new ArrayList<Integer>();
write(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\compressed.bin"),compressedString);    	


//String hex="";int start=0;int end=3;
//for(int x=0;x<compressedString.length()/3;x++) {
//hex=compressedString.substring(start,end);
//int dec=Integer.parseInt(hex,16);
//compressedlist.add(dec);
//start=end;end=end+3; }

String hex="";int dec=0;
String tokens[]=compressedString.split(" ");
for(int x=0;x<tokens.length;x++) {
hex=tokens[x];
dec=Integer.parseInt(hex,16);
compressedlist.add(dec);}
    	
    	
//String decompressed = decompress(compressed);
String decompressed = decompress(compressedlist);
write(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\decompressed.bin"),decompressed);
System.out.println("..");
decompressBSIOS(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\decompressed.bin"),new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.jpg"));
    }
}