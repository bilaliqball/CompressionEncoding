package encoding;

import java.util.*; 
  
class LRS  { 

public static String lcp(String s, String t) {
int n = Math.min(s.length(), t.length());
for (int i = 0; i < n; i++) {
if (s.charAt(i) != t.charAt(i))
return s.substring(0, i);}
return s.substring(0, n);}
public static String lrs(String s) {
// form the N suffixes
// long n  = s.length();
String[] suffixes = new String[s.length()];
for (int i = 0; i < s.length(); i++) {suffixes[i] = s.substring(i, s.length());}
Arrays.sort(suffixes);
// find longest repeated substring by comparing adjacent sorted suffixes
String lrs = "";
for (int i = 0; i < s.length()-1; i++) {String x = lcp(suffixes[i], suffixes[i+1]);
if (x.length() > lrs.length())
lrs = x;}
return lrs;}	
	
static int findLongestRepeatingSubSeq(String str) { 
int n = str.length(); 
int[][] dp = new int[n+1][n+1]; 
for (int i=1; i<=n; i++) { 
for (int j=1; j<=n; j++) { 
if (str.charAt(i-1) == str.charAt(j-1) && i!=j) {dp[i][j] =  1 + dp[i-1][j-1];}           
else {dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]); }} } 
return dp[n][n]; } 



public static void hashMap() {
Map<String, Integer> map = new HashMap<>();
map.put("apple", 1);
map.put("orange", 2);
map.put("banana", 3);
map.put("watermelon", null);

System.out.println(map.toString());
//int count=fruits.get("apple");count++;
//fruits.put("apple", count);
//System.out.println(fruits.toString());

int count = map.containsKey("apple") ? map.get("apple") : 0;
map.put("apple", count + 1);
System.out.println(map.toString());

}



public static void main (String[] args)  { 
//String str = "aabb"; 
//System.out.println("The length of the largest subsequence that repeats itself is : "+findLongestRepeatingSubSeq(str)); 
//hashMap();    

	System.out.println(Math.pow(32, 4));

	System.out.println(Integer.MAX_VALUE);
} 
} 
  
// This code is contributed by Pramod Kumar 