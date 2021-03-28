package lzw;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;



//FIXME - Remove all printStackTrace with proper System.out.
public class LZ77A {

	// 12 bits to store maximum offset distance.
	public static final int MAX_WINDOW_SIZE = (1 << 12) - 1;

	// 4 bits to store length of the match.
	public static final int LOOK_AHEAD_BUFFER_SIZE = (1 << 4) - 1;

	// sliding window size
	private int windowSize = MAX_WINDOW_SIZE;

	public LZ77A(int windowSize) {
		this.windowSize = Math.min(windowSize, MAX_WINDOW_SIZE);
	}

	/**
	 * Compress given input file as follows
	 * 
	 * A 0 bit followed by eight bits means just copy the eight bits to the output directly.
	 * A 1 bit is followed by a pointer of 12 bits followed by a length encoded in 4 bits. This is to be interpreted as "copy the <length> bytes from <pointer> bytes ago in the output to the current location" .
	 * 
	 * @param inputFileName name of the input File name to be compressed
	 * @param outputFileName compressed input file file will be written to
	 */


	private void compress(String inputFileName, String outputFileName) throws IOException {
		BitOutputStream out = null;
		try {
			byte[] data = Files.readAllBytes(Paths.get(inputFileName));
			out = new BitOutputStream(new BufferedOutputStream(new FileOutputStream(outputFileName)));
			for (int i = 0; i < data.length;) {
				Match match = findMatchInSlidingWindow(data, i);
				if (match != null) {
					out.write(Boolean.TRUE);
					out.write((byte) (match.getDistance() >> 4));
					out.write((byte) (((match.getDistance() & 0x0F) << 4) | match.getLength()));
					//System.out.println("<1," + match.getDistance() + ", " + match.getLength() + ">");
					i = i + match.getLength();
				} else {
					out.write(Boolean.FALSE);
					out.write(data[i]);
					i = i + 1;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * decompress input file and writes to output file
	 * 
	 * @param inputFileName compressed input file
	 * @param outputFileName decompressed output file
	 * @throws IOException
	 */
	private void decompress(String inputFileName, String outputFileName) throws IOException {
		BitInputStream inputFileStream = null;
		FileChannel outputChannel = null;
		RandomAccessFile outputFileStream = null;
		try {
			inputFileStream = new BitInputStream(new BufferedInputStream(new FileInputStream(inputFileName)));
			outputFileStream = new RandomAccessFile(outputFileName, "rw");
			outputChannel = outputFileStream.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1);
			try {
				while (true) {// when end of file reached, inputStream throws End Of file Exception
					int flag = inputFileStream.read();
					if (flag == 0) {
						buffer.clear();
						buffer.put(inputFileStream.readByte());
						buffer.flip();
						outputChannel.write(buffer, outputChannel.size());
						outputChannel.position(outputChannel.size());
					} else {
						int byte1 = inputFileStream.read(8);
						int byte2 = inputFileStream.read(8);
						int distance = (byte1 << 4) | (byte2 >> 4);
						int length = (byte2 & 0x0f);
						for (int i = 0; i < length; i++) {
							buffer.clear();
							outputChannel.read(buffer, outputChannel.position() - distance);
							buffer.flip();
							outputChannel.write(buffer, outputChannel.size());
							outputChannel.position(outputChannel.size());
						}
					}
				}
			} catch (EOFException e) {
				// ignore. means we reached the end of the file. and we are done.
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			outputFileStream.close();
			outputChannel.close();
			inputFileStream.close();
		}
	}

	private Match findMatchInSlidingWindow(byte[] data, int currentIndex) {
		Match match = new Match();
		int end = Math.min(currentIndex + LOOK_AHEAD_BUFFER_SIZE, data.length + 1);
		for (int j = currentIndex + 2; j < end; j++) {
			int startIndex = Math.max(0, currentIndex - windowSize);
			byte[] bytesToMatch = Arrays.copyOfRange(data, currentIndex, j);
			for (int i = startIndex; i < currentIndex; i++) {
				int repeat = bytesToMatch.length / (currentIndex - i);
				int remaining = bytesToMatch.length % (currentIndex - i);

				byte[] tempArray = new byte[(currentIndex - i) * repeat + (i + remaining - i)];
				int m = 0;
				for (; m < repeat; m++) {
					int destPos = m * (currentIndex - i);
					System.arraycopy(data, i, tempArray, destPos, currentIndex - i);
				}
				int destPos = m * (currentIndex - i);
				System.arraycopy(data, i, tempArray, destPos, remaining);
				if (Arrays.equals(tempArray, bytesToMatch) && bytesToMatch.length > match.getLength()) {
					match.setLength(bytesToMatch.length);
					match.setDistance(currentIndex - i);
				}
			}
		}
		if (match.getLength() > 0 && match.getDistance() > 0)
			return match;
		return null;
	}

	/**
	 * @param args
	 */
	
	public void compressIMG(File file, File dest,float fact) throws IOException {
		BufferedImage image = ImageIO.read(file);
		OutputStream os =new FileOutputStream(dest);
		Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("JPEG");
		ImageWriter writer = (ImageWriter) writers.next();
		ImageOutputStream ios = ImageIO.createImageOutputStream(os);
		writer.setOutput(ios);
		ImageWriteParam param = writer.getDefaultWriteParam();
		//JPEGImageWriteParam param = new JPEGImageWriteParam(null);
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(fact);
		writer.write(null, new IIOImage(image, null, null), param);
		os.close();ios.close();writer.dispose();}
	
	
	public static void main(String[] args) throws IOException {
		
		File input=new File("C:\\Users\\bilal.iqbal\\Pictures\\sample\\rabit.jpg");
		File output=new File("C:\\Users\\bilal.iqbal\\Pictures\\sample\\rabit.bmp");
		
		
//
//		int windowSize = 100;
//
//		if (args.length < 1) {
//			System.out.println("Usage : java com.lz77.LZ77 inputfile windowSize");
//			System.out.println("windowsize is optional. default size is :" + windowSize+".if Window size gets bigger, then compression time increases.");
//			System.out.println("Compressed file will be written into inputfilename-compressed.extension");
//			System.out.println("Decompressed file will be written into inputfilename-decompressed.extension");
//			return;
//		}
//
//		if (args.length > 1) {
//			try {
//				windowSize = Integer.valueOf(args[1]);
//			} catch (NumberFormatException e) {
//				System.out.println("Please enter valid number for window size less 4095. Or default size of " + windowSize + " will be used.");
//				return;
//			}
//		}
//		String inputFileName = args[0];
//		
//		if(!Files.exists(Paths.get(inputFileName))){
//			System.out.println("File doesnt exists");
//		}
//		
//		StringBuilder compressedFileNameBuilder = new StringBuilder();
//		String compressedFileName = new String();
//		String decompressedFileName = new String();
//		int extension = inputFileName.lastIndexOf(".");
//		if (extension > -1) {
//			compressedFileNameBuilder.append(inputFileName.substring(0, extension));
//			compressedFileNameBuilder.append("-compressed");
//			compressedFileNameBuilder.append(inputFileName.substring(extension));
//		} else {
//			compressedFileNameBuilder.append(inputFileName);
//			compressedFileNameBuilder.append("-compressed");
//		}
//		compressedFileName = compressedFileNameBuilder.toString();
//		decompressedFileName = compressedFileName.toString().replace("-compressed", "-decompressed");
//
//		if(Files.exists(Paths.get(compressedFileName))){
//			Files.delete(Paths.get(compressedFileName));
//		}
//		if(Files.exists(Paths.get(decompressedFileName))){
//			Files.delete(Paths.get(decompressedFileName));
//		}
//		
//		LZ77 lz77 = new LZ77(windowSize);
//		// lz77.compress("/Users/bkoppaka/workspaces/300/LZ77/LZ77/src/com/lz77/input.bmp", "/Users/bkoppaka/workspaces/300/LZ77/LZ77/src/com/lz77/compressed.bmp");
//		// FIXME read the input and output file from command prompt.
//		System.out.println("Compression started...");
//		
//		long startTime = System.currentTimeMillis();
//		lz77.compress(inputFileName, compressedFileName);
//		long endTime = System.currentTimeMillis();
//		System.out.println("Compression Done in : " + (endTime - startTime) + " ms");
//		
//		startTime = System.currentTimeMillis();
//		System.out.println("\nDecompression started...");
//		lz77.decompress(compressedFileName, decompressedFileName);
//		endTime = System.currentTimeMillis();
//		System.out.println("Decompression Done in: " + (endTime - startTime) + " ms");
		
		LZ77A lz77 = new LZ77A(4);//lz77.compressIMG(input,output,.9f); 
		//lz77.compress("C:\\Users\\bilal.iqbal\\Pictures\\sample\\rabit.bmp","C:\\Users\\bilal.iqbal\\Pictures\\sample\\rabit_.bmp");
		lz77.decompress("C:\\Users\\bilal.iqbal\\Pictures\\sample\\rabit_.bmp","C:\\Users\\bilal.iqbal\\Pictures\\sample\\rabit__.bmp");
		
	}

}