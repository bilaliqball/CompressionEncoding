package commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorInputStream;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorOutputStream;
import org.apache.commons.compress.compressors.snappy.FramedSnappyCompressorInputStream;
import org.apache.commons.compress.compressors.snappy.FramedSnappyCompressorOutputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;
import org.apache.commons.io.IOUtils;

public class Compressor {
	
public static void main(String[] args) throws IOException {
File input = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.jpg");
File bin = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\out.bin");
File decompress = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.png");

//compressGZIOS(input,bin);decompressGZIOS(bin,decompress);
//compressBZIOS(input,bin);decompressBZIOS(bin,decompress);
//compressLZIOS(input,bin);decompressLZIOS(bin,decompress);
//compressDZIOS(input,bin);decompressDZIOS(bin,decompress);
compressSNIOS(input,bin);decompressSNIOS(bin,decompress);


}

	

//GZIP
public static void compressGZIOS(File input, File output) throws IOException {
try (GzipCompressorOutputStream out = new GzipCompressorOutputStream(new FileOutputStream(output))){
IOUtils.copy(new FileInputStream(input), out);}}
public static void decompressGZIOS(File input, File output) throws IOException {
try (GzipCompressorInputStream in = new GzipCompressorInputStream(new FileInputStream(input))){
IOUtils.copy(in, new FileOutputStream(output));}}

//BZIP
public static void compressBZIOS(File input, File output) throws IOException {
try (BZip2CompressorOutputStream out = new BZip2CompressorOutputStream(new FileOutputStream(output))){
IOUtils.copy(new FileInputStream(input), out);}}
public static void decompressBZIOS(File input, File output) throws IOException {
try (BZip2CompressorInputStream in = new BZip2CompressorInputStream(new FileInputStream(input))){
IOUtils.copy(in, new FileOutputStream(output));}}


//LZ4
public static void compressLZIOS(File input, File output) throws IOException {
try (FramedLZ4CompressorOutputStream out = new FramedLZ4CompressorOutputStream(new FileOutputStream(output))){
IOUtils.copy(new FileInputStream(input), out);}}
public static void decompressLZIOS(File input, File output) throws IOException {
try (FramedLZ4CompressorInputStream in = new FramedLZ4CompressorInputStream(new FileInputStream(input))){
IOUtils.copy(in, new FileOutputStream(output));}}



//Deflate
public static void compressDZIOS(File input, File output) throws IOException {
try (DeflateCompressorOutputStream out = new DeflateCompressorOutputStream(new FileOutputStream(output))){
IOUtils.copy(new FileInputStream(input), out);}}
public static void decompressDZIOS(File input, File output) throws IOException {
try (DeflateCompressorInputStream in = new DeflateCompressorInputStream(new FileInputStream(input))){
IOUtils.copy(in, new FileOutputStream(output));}}

//Snappy
public static void compressSNIOS(File input, File output) throws IOException {
try (FramedSnappyCompressorOutputStream out = new FramedSnappyCompressorOutputStream(new FileOutputStream(output))){
IOUtils.copy(new FileInputStream(input), out);}}
public static void decompressSNIOS(File input, File output) throws IOException {
try (FramedSnappyCompressorInputStream in = new FramedSnappyCompressorInputStream(new FileInputStream(input))){
IOUtils.copy(in, new FileOutputStream(output));}}


//XZ
public static void compressZXIOS(File input, File output) throws IOException {
try (XZCompressorOutputStream out = new XZCompressorOutputStream(new FileOutputStream(output))){
IOUtils.copy(new FileInputStream(input), out);}}
public static void decompressZXIOS(File input, File output) throws IOException {
try (XZCompressorInputStream in = new XZCompressorInputStream(new FileInputStream(input))){
IOUtils.copy(in, new FileOutputStream(output));}}
}
