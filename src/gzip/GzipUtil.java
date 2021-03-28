package gzip;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;
 
public class GzipUtil {
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
 
    public static byte[] zip(final String str) {
        if ((str == null) || (str.length() == 0)) {
            throw new IllegalArgumentException("Cannot zip null or empty string");
        }
 
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
                gzipOutputStream.write(str.getBytes(StandardCharsets.UTF_8));
            }
            return byteArrayOutputStream.toByteArray();
        } catch(IOException e) {
            throw new RuntimeException("Failed to zip content", e);
        }
    }
 
    public static String unzip(final byte[] compressed) {
        if ((compressed == null) || (compressed.length == 0)) {
            throw new IllegalArgumentException("Cannot unzip null or empty bytes");
        }
        if (!isZipped(compressed)) {
            return new String(compressed);
        }
 
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressed)) {
            try (GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {
                try (InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8)) {
                    try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                        StringBuilder output = new StringBuilder();
                        String line;
                        while((line = bufferedReader.readLine()) != null){
                            output.append(line);
                        }
                        return output.toString();
                    }
                }
            }
        } catch(IOException e) {
            throw new RuntimeException("Failed to unzip content", e);
        }
    }
 
    public static boolean isZipped(final byte[] compressed) {
        return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC)) && (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
    }
    
    public static void main(String args[]) throws IOException {
    	FileInputStream fis=new FileInputStream(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.jpg"));
    	String content=IOUtils.toString(fis);
    	File hex=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\hex.txt");
    	byte[] bytes=zip(content);
    	StringBuilder sb=new StringBuilder();
    	for(byte b:bytes) {sb.append((char)b);}
    	write(hex,sb.toString());
    }
}