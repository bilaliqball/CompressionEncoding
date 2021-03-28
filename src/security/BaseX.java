package security;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * allows you to convert a whole number into a compacted representation of that number,
 * based upon the dictionary you provide. very similar to base64 encoding, or indeed hex
 * encoding.
 */
public class BaseX {


	
public static void writeline(String content) {
String FILENAME = "C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\com.txt";
try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME,true))) {
bw.write(content);bw.newLine();
bw.close();
} catch (IOException e) {e.printStackTrace();}}

public static final char[] DICTIONARY_16 = 
new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

	
	

public static final char[] DICTIONARY_32 = 
new char[]{'1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','J','K','M','N','P','Q','R','S','T','U','V','W','X','Y','Z'};

public static final char[] DICTIONARY_64 = 
new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','+','/'};

public static final char[] DICTIONARY_89 = 
new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','+','"','@','*','#','%','&','/','|','(',')','=','?','~','[',']','{','}','$','-','_','.',':',',',';','<','>'};
	
protected char[] dictionary;
public BaseX(char[] dictionary){this.dictionary = dictionary;}
public BaseX(){this.dictionary = DICTIONARY_64;}
public static void comp() {}
	
	
	
public static void main(String[] args) {
String original = "123456789012345678901234567890";
System.out.println("Original: " + original);
BaseX bx = new BaseX(DICTIONARY_64);
String encoded = bx.encode(new BigInteger(original));
System.out.println("encoded: " + encoded);
BigInteger decoded = bx.decode(encoded);
System.out.println("decoded: " + decoded);
if(original.equals(decoded.toString())){System.out.println("Equals");}
else{System.err.println("Decoded value is NOT the same as the original!!");}
		
		

//String line;
//String FILENAME = "C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\enc.txt";
//BaseX bx = new BaseX(DICTIONARY_64);
//try (BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(FILENAME)))) {
////for(int i=0;i<100;i++) {
//while((line=br.readLine())!=null) {
//line=br.readLine();
//String encoded = bx.encode(new BigInteger(line));
//writeline(encoded);
//}}catch (IOException e) {e.printStackTrace();}
	
//BigInteger decoded = bx.decode(encoded);
//writeContent(decoded.toString());
		
System.out.println("...");}


public String encode(BigInteger value) {
List<Character> result = new ArrayList<Character>();
BigInteger base = new BigInteger("" + dictionary.length);
int exponent = 1;
BigInteger remaining = value;
while(true){
BigInteger a = base.pow(exponent); //16^1 = 16
BigInteger b = remaining.mod(a); //119 % 16 = 7 | 112 % 256 = 112
BigInteger c = base.pow(exponent - 1);
BigInteger d = b.divide(c);
result.add(dictionary[d.intValue()]);
remaining = remaining.subtract(b); //119 - 7 = 112 | 112 - 112 = 0
if(remaining.equals(BigInteger.ZERO)){break;}
exponent++;}
//need to reverse it, since the start of the list contains the least significant values
StringBuffer sb = new StringBuffer();
for(int i = result.size()-1; i >= 0; i--){sb.append(result.get(i));}
return sb.toString();}
	

public BigInteger decode(String str) {
char[] chars = new char[str.length()];
str.getChars(0, str.length(), chars, 0);
char[] chars2 = new char[str.length()];
int i = chars2.length -1;
for(char c : chars){
chars2[i--] = c;}
//for efficiency, make a map
Map<Character, BigInteger> dictMap = new HashMap<Character, BigInteger>();
int j = 0;
for(char c : dictionary){
dictMap.put(c, new BigInteger("" + j++));}
BigInteger bi = BigInteger.ZERO;
BigInteger base = new BigInteger("" + dictionary.length);
int exponent = 0;
for(char c : chars2){
BigInteger a = dictMap.get(c);
BigInteger b = base.pow(exponent).multiply(a);
bi = bi.add(new BigInteger("" + b));
exponent++;}
return bi;}
	
}