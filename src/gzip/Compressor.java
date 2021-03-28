package gzip;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

import org.apache.commons.io.IOUtils;



public class Compressor {
	
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
		 
		 public static void main(String args[]) throws Exception {
			 FileInputStream fis=new FileInputStream(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.jpg"));
			 FileOutputStream fos=new FileOutputStream(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.jpg"));
			 String content=IOUtils.toString(fis);
			 byte[] bytes=gzip(content);
			 String dec=ungzip(bytes);
			 byte[] decbytes=dec.getBytes();
			 fos.write(decbytes);
		 }
		 
		 
	     private static String ungzip(byte[] bytes) throws Exception{
	            InputStreamReader isr = new InputStreamReader(new GZIPInputStream(new ByteArrayInputStream(bytes)), StandardCharsets.UTF_8);
	            StringWriter sw = new StringWriter();
	            char[] chars = new char[1024];
	            for (int len; (len = isr.read(chars)) > 0; ) {
	                sw.write(chars, 0, len);
	            }
	            return sw.toString();
	        }

	        private static byte[] gzip(String s) throws Exception{
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            GZIPOutputStream gzip = new GZIPOutputStream(bos);
	            OutputStreamWriter osw = new OutputStreamWriter(gzip, StandardCharsets.UTF_8);
	            osw.write(s);
	            osw.close();
	            return bos.toByteArray();
	        }
		 
	  public static byte[] compress(String text) {
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          try {
              OutputStream out = new DeflaterOutputStream(baos);
              out.write(text.getBytes("UTF-8"));
              out.close();
          } catch (IOException e) {
              throw new AssertionError(e);
          }
          return baos.toByteArray();
      }

      public static String decompress(byte[] bytes) {
          InputStream in = new InflaterInputStream(new ByteArrayInputStream(bytes));
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          try {
              byte[] buffer = new byte[8192];
              int len;
              while((len = in.read(buffer))>0)
                  baos.write(buffer, 0, len);
              return new String(baos.toByteArray(), "UTF-8");
          } catch (IOException e) {
              throw new AssertionError(e);
          }
      }
}
