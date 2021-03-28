package lzwi;
import java.io.*;
import java.util.*;

public class LZW2{

public static void main(String[] args) {
LZW2 l=new LZW2();
//Compress c=l.new Compress("C:\\Users\\bilal.iqbal\\Pictures\\sample\\sparrow.png");
Decompress d=l.new Decompress("C:\\Users\\bilal.iqbal\\Pictures\\sample\\image.bin");
}	





class Compress{
public HashMap<String, Integer> dictionary = new HashMap<>();
public int dictSize = 256;
public String P = "",filename="",BP="";
public  byte inputByte;
public  byte[] buffer = new byte[3];
public  boolean isLeft = true;
public Compress(String fn) {
filename=fn;
try {
compress();
System.out.println("Compression complete! Check file image.bin.");   }
catch(IOException ie) {System.out.println("File "+filename+" not found!");}
}
public void compress() throws IOException {
int i,byteToInt;
char C;
for(i=0;i<256;i++) {
dictionary.put(Character.toString((char)i),i);}
RandomAccessFile inputFile = new RandomAccessFile(filename,"r");String path=new File(filename).getParent();
RandomAccessFile outputFile=new RandomAccessFile(path+"\\image.bin","rw");
try {
inputByte = inputFile.readByte();
byteToInt = new Byte(inputByte).intValue();
if(byteToInt < 0) byteToInt += 256;
C = (char) byteToInt;
P = ""+C;
while(true) {
inputByte = inputFile.readByte();
byteToInt = new Byte(inputByte).intValue();

if(byteToInt < 0) byteToInt += 256;
C = (char) byteToInt;
if(dictionary.containsKey(P+C)) {
P = P+C;}
/*if P+C is not in dictionary, we obtain the longest string in the dictionary 
so far which is stored in P. The value of this string is converted in binary. 
This binary number is then padded to make it 12 bits long (for standardization
and avoing overflow or underflow caused using 8 bits). This is then converted 
into bytes and stored. We write in the file every 2 characters.*/
else {
BP = convertTo12Bit(dictionary.get(P));
if(isLeft) {
buffer[0] = (byte) Integer.parseInt(BP.substring(0,8),2);  
buffer[1] = (byte) Integer.parseInt(BP.substring(8,12)+"0000",2);                   }
else {
buffer[1] += (byte) Integer.parseInt(BP.substring(0,4),2); 
buffer[2] = (byte) Integer.parseInt(BP.substring(4,12),2);
for(i=0;i<buffer.length;i++) {
outputFile.writeByte(buffer[i]);
buffer[i]=0;}}
isLeft = !isLeft;
if(dictSize < 4096) dictionary.put(P+C,dictSize++);
P=""+C;}}}

/*If isLeft is true, we store the current data in BP to buffer[0] and buffer[1]. Then these 
buffers are written in the output file.If isLeft is false, we already have data in the first 
and half of seccond byte of the buffer. Hence, we store the current value of BP and write 
all the 3 bytes to the outputFile. When the file input is complete, the while loop will 
still execute due to the condition. This ensures that the file is read completely but it 
might throw an error if there is no input left in the inputFile. So, when an error is thrown, 
we store the remaining contentsof the buffer.*/
catch(IOException ie) {
BP = convertTo12Bit(dictionary.get(P));
if(isLeft) {
buffer[0] = (byte) Integer.parseInt(BP.substring(0,8),2);  
buffer[1] = (byte) Integer.parseInt(BP.substring(8,12)+"0000",2);
outputFile.writeByte(buffer[0]);  
outputFile.writeByte(buffer[1]);                }
else {
buffer[1] += (byte) Integer.parseInt(BP.substring(0,4),2); 
buffer[2] = (byte) Integer.parseInt(BP.substring(4,12),2);
for(i=0;i<buffer.length;i++) {
outputFile.writeByte(buffer[i]);
buffer[i]=0;}}
inputFile.close();
outputFile.close();}}

public String convertTo12Bit(int i) {
String to12Bit = Integer.toBinaryString(i);
while (to12Bit.length() < 12) to12Bit = "0" + to12Bit;/////////////////////////////////////////////////
return to12Bit;}}


class Decompress{
public String[] arrayOfChar;
public String filename="",extension="";
public int dictSize = 256, currentword, previousword;
public byte[] buffer = new byte[3];
public boolean isLeft = true;

public Decompress(String cf) {
try {
filename=cf;
extension ="jpg";
decompress();
System.out.println("Decompression complete! Check file image.jpg.");   }
catch(IOException ie) {System.out.println("File "+filename+" not found!");}
}
public void decompress() throws IOException {
arrayOfChar = new String[4096];
int i;
for (i=0;i<256;i++) arrayOfChar[i] = Character.toString((char)i);
RandomAccessFile inputFile = new RandomAccessFile(filename,"r");String path=new File(filename).getParent();
RandomAccessFile outputFile = new RandomAccessFile(path+"\\decompress.".concat(extension),"rw");
try {
buffer[0] = inputFile.readByte();
buffer[1] = inputFile.readByte();
previousword = getIntValue(buffer[0], buffer[1], isLeft);
isLeft = !isLeft;
outputFile.writeBytes(arrayOfChar[previousword]);
while (true) {
if (isLeft) {
buffer[0] = inputFile.readByte();
buffer[1] = inputFile.readByte();
currentword = getIntValue(buffer[0], buffer[1], isLeft);} 
else {
buffer[2] = inputFile.readByte();
currentword = getIntValue(buffer[1], buffer[2], isLeft);}
isLeft = !isLeft;
if (currentword >= dictSize) {
if (dictSize < 4096) {
arrayOfChar[dictSize] = arrayOfChar[previousword] + arrayOfChar[previousword].charAt(0);}
dictSize++;
outputFile.writeBytes(arrayOfChar[previousword] + arrayOfChar[previousword].charAt(0));} 
//If word is present, we form a word with the previous word and the first character of the current word and add it in a new entry
else {
if (dictSize < 4096) {
arrayOfChar[dictSize] = arrayOfChar[previousword] + arrayOfChar[currentword].charAt(0);}
dictSize++;
outputFile.writeBytes(arrayOfChar[currentword]);}
previousword = currentword;}} 
catch (EOFException e) {inputFile.close();outputFile.close();}}

//Converting 2 bytes to 12-bit code. Converting 12-bit code to integer value.
public  int getIntValue(byte b1, byte b2, boolean isLeft) {
String t1 = Integer.toBinaryString(b1);
String t2 = Integer.toBinaryString(b2);
while (t1.length() < 8) t1 = "0" + t1;
if (t1.length() == 32) t1 = t1.substring(24, 32);
while (t2.length() < 8) t2 = "0" + t2;
if (t2.length() == 32) t2 = t2.substring(24, 32);
if (isLeft) return Integer.parseInt(t1 + t2.substring(0, 4), 2);
else return Integer.parseInt(t1.substring(4, 8) + t2, 2);}}



}


