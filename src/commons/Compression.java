package commons;
import java.io.*;
import java.util.*;
import java.awt.image.*;

import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.FileUtils;

class Compression {

   public static void main(String[] args) throws IOException {
	   
	   compressimage();
   }
   
public static void compressimage() throws IOException {
File input = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\iphone.png");
BufferedImage image = ImageIO.read(input);
File compressedImageFile = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\iphone_.jpg");
OutputStream os =new FileOutputStream(compressedImageFile);
Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("jpg");
ImageWriter writer = (ImageWriter) writers.next();
ImageOutputStream ios = ImageIO.createImageOutputStream(os);
writer.setOutput(ios);
ImageWriteParam param = writer.getDefaultWriteParam();
param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
param.setCompressionQuality(0.90f);
writer.write(null, new IIOImage(image, null, null), param);
os.close();
ios.close();
writer.dispose();}
   
   
public static void copyFile() throws IOException {
File inp=new File("C:\\Users\\bilal.iqbal\\bin\\docs.exe");
File out=new File("C:\\Users\\bilal.iqbal\\docs.jar");
FileUtils.copyFile(inp, out);System.out.println("...");}
}