package lzw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class LZ77C {

	private char referencePrefix;
	private int referenceIntBase;
	private int referenceIntFloorCode;
	private int referenceIntCeilCode;
	private int maxStringDistance;
	private int minStringLength;
	private int maxStringLength;
	private int defaultWindowLength;
	private int maxWindowLength;

	// CONSTRUCTOR

	public LZ77C() {

		referencePrefix = '`';
		referenceIntBase = 96;
		referenceIntFloorCode = (int) ' ';
		referenceIntCeilCode = referenceIntFloorCode + referenceIntBase;
		maxStringDistance = (int) Math.pow(referenceIntBase, 2) - 1;
		minStringLength = 5;
		maxStringLength = (int) Math.pow(referenceIntBase, 1) - 1
				+ minStringLength;
		defaultWindowLength = 144;
		maxWindowLength = maxStringDistance + minStringLength;
	}

	public static String read(File file)throws IOException{
		String line;
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		while((line = br.readLine())!=null) {
		sb.append(line);}br.close();}
		catch (IOException e) {e.printStackTrace();}
		return sb.toString();}
	
	public static void writeContent(File file, String content) throws IOException {
		FileWriter writer = new FileWriter(file);
		try(PrintWriter printWriter =new PrintWriter(writer)){
		printWriter.write(content);}}
	// MAIN METHOD
	
	public static void main(String[] args) throws IOException {
		
//		if (Objects.isNull(args)) {
//			printUsageToStdErr();
//			return;
//		}
//		
//		if (args.length < 1 || args.length > 2) {
//			printUsageToStdErr();
//			return;
//		}
//		
//		if (args.length == 2 && !args[0].equals("-d")) {
//			printUsageToStdErr();
//			return;
//		} else if (args.length == 2) { 
//			System.out.println(LZ77C.compressStr(args[1]));
//			return;
//		}
//		
//		System.out.println(LZ77C.compressStr(args[0]));		
		
		FileInputStream fis=new FileInputStream(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\hex.bin"));
		String content=read(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\hex.bin"));System.out.println(content.length());
		String comp=LZ77C.compressStr(content);
		writeContent(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\hex_.bin"),comp);
		
	}

	private static void printUsageToStdErr() {
		System.err.println("Usage: LZ77 [-d] data   (-d == decompress)");
	}

	// LAZY STATIC METHODS - ADDED BY: DAN!
	public static String compressStr(String data) {
		LZ77C lz = new LZ77C();
		return lz.compress(data);
	}
	public static String decompressStr(String data) {
		LZ77C lz = new LZ77C();
		return lz.decompress(data);
	}

	// PUBLIC METHODS

	/**
	 * Compress string data using the LZ77 algorithm.
	 * 
	 * @param data
	 *            String data to compress
	 * @return LZ77 compressed string
	 */
	public String compress(String data) {

		return compress(data, null);
	}

	/**
	 * Compress string data using the LZ77 algorithm.
	 * 
	 * @param data
	 *            String data to compress
	 * @param windowLength
	 *            Optional window length
	 * @return LZ77 compressed string
	 */
	public String compress(String data, Integer windowLength) {

		if (windowLength == null)
			windowLength = defaultWindowLength;

		if (windowLength > maxWindowLength)
			throw new IllegalArgumentException("Window length too large");

		String compressed = "";

		int pos = 0;
		int lastPos = data.length() - minStringLength;

		while (pos < lastPos) {

			int searchStart = Math.max(pos - windowLength, 0);
			int matchLength = minStringLength;
			boolean foundMatch = false;
			int bestMatchDistance = maxStringDistance;
			int bestMatchLength = 0;
			String newCompressed = null;

			while ((searchStart + matchLength) < pos) {

				int sourceWindowEnd = Math.min(searchStart + matchLength, data
						.length());

				int targetWindowEnd = Math
						.min(pos + matchLength, data.length());

				String m1 = data.substring(searchStart, sourceWindowEnd);
				String m2 = data.substring(pos, targetWindowEnd);

				boolean isValidMatch = m1.equals(m2)
						&& matchLength < maxStringLength;

				if (isValidMatch) {

					matchLength++;
					foundMatch = true;

				} else {

					int realMatchLength = matchLength - 1;

					if (foundMatch && (realMatchLength > bestMatchLength)) {
						bestMatchDistance = pos - searchStart - realMatchLength;
						bestMatchLength = realMatchLength;
					}

					matchLength = minStringLength;
					searchStart++;
					foundMatch = false;
				}
			}

			if (bestMatchLength != 0) {

				newCompressed = referencePrefix
						+ encodeReferenceInt(bestMatchDistance, 2)
						+ encodeReferenceLength(bestMatchLength);

				pos += bestMatchLength;

			} else {

				if (data.charAt(pos) != referencePrefix) {
					newCompressed = "" + data.charAt(pos);
				} else {
					newCompressed = "" + referencePrefix + referencePrefix;
				}

				pos++;
			}
			compressed += newCompressed;
		}

		return compressed + data.substring(pos).replaceAll("/`/g", "``");
	}

	public String decompress(String data) {

		String decompressed = "";
		int pos = 0;

		while (pos < data.length()) {

			char currentChar = data.charAt(pos);

			if (currentChar != referencePrefix) {

				decompressed += currentChar;
				pos++;

			} else {

				char nextChar = data.charAt(pos + 1);

				if (nextChar != referencePrefix) {

					int distance = decodeReferenceInt(data.substring(pos + 1,
							pos + 3), 2);

					int length = decodeReferenceLength(data.substring(pos + 3,
							pos + 4));

					int start = decompressed.length() - distance - length;
					int end = start + length;
					decompressed += decompressed.substring(start, end);
					pos += minStringLength - 1;

				} else {

					decompressed += referencePrefix;
					pos += 2;
				}
			}
		}

		return decompressed;
	}

	// PRIVATE METHODS

	private String encodeReferenceInt(int value, int width) {

		if ((value >= 0) && (value < (Math.pow(referenceIntBase, width) - 1))) {

			String encoded = "";

			while (value > 0) {
				char c = (char) ((value % referenceIntBase) + referenceIntFloorCode);
				encoded = "" + c + encoded;
				value = (int) Math.floor(value / referenceIntBase);
			}

			int missingLength = width - encoded.length();

			for (int i = 0; i < missingLength; i++) {
				char c = (char) referenceIntFloorCode;
				encoded = "" + c + encoded;
			}

			return encoded;

		} else {

			throw new IllegalArgumentException("Reference int out of range: "
					+ value + " (width = " + width + ")");
		}
	}

	private String encodeReferenceLength(int length) {

		return encodeReferenceInt(length - minStringLength, 1);
	}

	private int decodeReferenceInt(String data, int width) {

		int value = 0;

		for (int i = 0; i < width; i++) {

			value *= referenceIntBase;

			int charCode = (int) data.charAt(i);

			if ((charCode >= referenceIntFloorCode)
					&& (charCode <= referenceIntCeilCode)) {

				value += charCode - referenceIntFloorCode;

			} else {

				throw new RuntimeException(
						"Invalid char code in reference int: " + charCode);
			}
		}

		return value;
	}

	private int decodeReferenceLength(String data) {

		return decodeReferenceInt(data, 1) + minStringLength;
	}
}