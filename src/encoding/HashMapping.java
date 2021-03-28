package encoding;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.IOUtils;



public class HashMapping {

Map<String, Integer> map;
HashMapping(){map = new HashMap<String, Integer>();}
	
public  void writetotable(String word) {
int count = map.containsKey(word) ? map.get(word) : 0;
map.put(word, count + 1);}


public  void init()throws IOException{
File file = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.txt");
BufferedImage img = null;
long size=(long)file.length();
int thp=(int)(file.length()/2);//total hex pairs
int dim=(int)Math.sqrt((double)thp);
int width =dim;
int height =(int)((thp/dim)+0.4);
System.out.println("siz:"+size+" dim:"+dim+" thp:"+thp+" wid:"+width);
String line;int p;String word="";
int start=0;int end=2;
try (BufferedReader br = new BufferedReader(new FileReader(file))) {
line = br.readLine();
if(line!=null) {
for(int y=0;y<height;y++){
for(int x=0;x<width;x++) {
word=line.substring(start,end);start=end;end=end+2;
writetotable(word);}}}}
catch (IOException e) {e.printStackTrace();}
Map<String, Integer> sorted=sortByValue(map);
printMap(sorted);}

public  void init2()throws IOException{
File file = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\hex.txt");
String content=IOUtils.toString(new FileInputStream(file));
String word="";int start=0;int chunk=2;int end=chunk;;
for(int x=0;x<content.length()/chunk;x++) {
word=content.substring(start,end);start=end;end=end+chunk;
writetotable(word);}

Map<String, Integer> sorted=sortByValue(map);
printMap(sorted);}

private Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {
List<Map.Entry<String, Integer>> list =new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());
Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
public int compare(Map.Entry<String, Integer> o1,Map.Entry<String, Integer> o2) {
return (o2.getValue()).compareTo(o1.getValue());}});

Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
for (Map.Entry<String, Integer> entry : list) {sortedMap.put(entry.getKey(), entry.getValue());}

//for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
//Map.Entry<String, Integer> entry = it.next();sortedMap.put(entry.getKey(), entry.getValue());}
return sortedMap;}

public  <K, V> void printAllMap(Map<K, V> map) {
for (Map.Entry<K, V> entry : map.entrySet()) {
int count=(int) entry.getValue();
if(count>1) {System.out.println("Key : " + entry.getKey()+ " Value : " + entry.getValue());}}}


public  <K, V> void printMap(Map<K, V> map) {
int counter=0;
for (Map.Entry<K, V> entry : map.entrySet()) {
int count=(int) entry.getValue();
if(count>1 && counter<160) {counter++;
System.out.println("Key : " + entry.getKey()+ " Value : " + entry.getValue());}}}

public static void main(String args[]) throws IOException {
HashMapping h=new HashMapping();
h.init2();
}

}