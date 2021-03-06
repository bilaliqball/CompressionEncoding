package lzw;
public class LZ77x {
	// Returns output length.
	public static int encode( byte[] input, byte[] output, int inputLen ) {	
		int[] index = new int[ 65536 ];
		int[] chain = new int[ 65536 ];
		int inputIdx = 0, outputIdx = 0, literals = 0;
		while( inputIdx < inputLen ) {
			int matchOffset = 0, matchLength = 1;
			// Indexed search. Requires 512k of memory.
			if( inputIdx + 3 < inputLen ) {
				int key = ( input[ inputIdx ] & 0xFF ) * 33 + ( input[ inputIdx + 1 ] & 0xFF );
				key = key * 33 + ( input[ inputIdx + 2 ] & 0xFF );
				key = key * 33 + ( input[ inputIdx + 3 ] & 0xFF );
				int searchIdx = index[ key & 0xFFFF ] - 1;
				while( ( inputIdx - searchIdx ) < 65536 && searchIdx >= 0 ) {
					if( inputIdx + matchLength < inputLen && input[ inputIdx + matchLength ] == input[ searchIdx + matchLength ] ) {
						int len = 0;
						while( inputIdx + len < inputLen && len < 127 && input[ searchIdx + len ] == input[ inputIdx + len ] ) {
							len++;
						}
						if( len > matchLength ) {
							matchOffset = inputIdx - searchIdx;
							matchLength = len;
							if( len >= 127 ) {
								break;
							}
						}
					}
					searchIdx = chain[ searchIdx & 0xFFFF ] - 1;
				}
				if( matchLength < 4 ) {
					matchOffset = 0;
					matchLength = 1;
				}
				int idx = inputIdx;
				int end = inputIdx + matchLength;
				if( end + 3 > inputLen ) {
					end = inputLen - 3;
				}
				while( idx < end ) {
					// Update the index for each byte of the input to be encoded.
					key = ( input[ idx ] & 0xFF ) * 33 + ( input[ idx + 1 ] & 0xFF );
					key = key * 33 + ( input[ idx + 2 ] & 0xFF );
					key = key * 33 + ( input[ idx + 3 ] & 0xFF );
					chain[ idx & 0xFFFF ] = index[ key & 0xFFFF ];
					index[ key & 0xFFFF ] = idx + 1;
					idx++;
				}
			}
			/*
			// Brute-force search, simple but very slow.
			final int MAX_OFFSET = 65535;
			int searchIdx = inputIdx <= MAX_OFFSET ? 0 : inputIdx - MAX_OFFSET;
			while( searchIdx < inputIdx ) {
				int len = 0;
				while( inputIdx + len < inputLen && input[ searchIdx + len ] == input[ inputIdx + len ] ) {
					len++;
				}
				if( len > matchLength && len > 3 ) {
					matchOffset = inputIdx - searchIdx;
					matchLength = len < 128 ? len : 127;
				}
				searchIdx++;
			}
			*/
			if( matchOffset == 0 ) {
				literals += matchLength;
				inputIdx += matchLength;
			}
			if( literals > 0 ) {
				// Flush literals if match found, end of input, or longest encodable run.
if( matchOffset > 0 || inputIdx == inputLen || literals == 127 ) {
output[ outputIdx++ ] = ( byte ) literals;
int literalIdx = inputIdx - literals;
while( literalIdx < inputIdx ) {
	output[ outputIdx++ ] = input[ literalIdx++ ];
					}
					literals = 0;
				}
			}
			if( matchOffset > 0 ) {
//System.out.println( "Offset " + matchOffset + " Length " + matchLength );
output[ outputIdx++ ] = ( byte ) ( 0x80 | matchLength );
output[ outputIdx++ ] = ( byte ) ( matchOffset >> 8 );
output[ outputIdx++ ] = ( byte ) matchOffset;
inputIdx += matchLength;
			}
		}
		return outputIdx;
	}
	
	// Output may be null to calculate uncompressed length.
public static int decode( byte[] input, byte[] output, int inputLen ) {
int inputIdx = 0, outputIdx = 0;
while( inputIdx < inputLen ) {
int matchOffset = 0;
int matchLength = input[ inputIdx++ ] & 0xFF;
if( matchLength > 127 ) {
matchLength = matchLength & 0x7F;
matchOffset = input[ inputIdx++ ] & 0xFF;
matchOffset = ( matchOffset << 8 ) | ( input[ inputIdx++ ] & 0xFF );}
if( output == null ) {
outputIdx += matchLength;
if( matchOffset == 0 ) {
inputIdx += matchLength;}} 
else {
int outputEnd = outputIdx + matchLength;
if( matchOffset == 0 ) {
while( outputIdx < outputEnd ) {
output[ outputIdx++ ] = input[ inputIdx++ ];}} 
else {
while( outputIdx < outputEnd ) {
output[ outputIdx ] = output[ outputIdx - matchOffset ];
outputIdx++;
			}
		}
	}
}
		return outputIdx;
	}
	
	public static void main( String[] args ) throws Exception {

java.io.InputStream is = new java.io.FileInputStream("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.jpg");
byte[] input = new byte[ 65536 ];
int length = 0, count = 0;
while( count >= 0 ) {
// Read the entire InputStream into an array.
count = is.read( input, length, input.length - length );
if( count > 0 ) {
length += count;
if( length >= input.length ) {
byte[] buf = new byte[ input.length * 2 ];
System.arraycopy( input, 0, buf, 0, input.length );
input = buf;}}}

System.out.println( "Input length: " + length );
byte[] encoded = new byte[ length * 2 ];
int encLen = encode( input, encoded, length );
System.out.println( "Encoded length: " + encLen );
byte[] decoded = new byte[ length ];
if( length != decode( encoded, null, encLen ) || length != decode( encoded, decoded, encLen ) ) {throw new Exception( "Decoded length differs from original." );}
for( int idx = 0; idx < length; idx++ ) {if( decoded[ idx ] != input[ idx ] ) {throw new Exception( "Decoded data corrupt at index " + idx );}

}
		
	}
}