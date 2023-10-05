/*
 *  Bin.java
 *
 *  @author Jose Simo. (c) ai2-DISCA-UPV Creative Commons.
 *  
 *  Rev: 2017
 */
package util;

/**
 * Binary utilities.
 * Encoding/decoding static methods for Little Endian numbers (2, 4 and 8 bytes long).
 * It also includes a method to concatenate a list of buffers.
 * See the "main" entry to get help.
 * 
 * @author Jose Simo (c) ai2-UPV 2014
 */
public final class Bin {

	 /** 
	 * Returns a integer (int) interpreted from a 2 bytes buffer considering Little Endian representation.
	 * @param bytes 
	 * @return integer
	 */
	 public static int wordToInt(byte[] bytes) {
		int value;
		value =    ( ( bytes[1] << 8  ) & 0x0000ff00 ) 
	            |  (   bytes[0]         & 0x000000ff );
	
		return value;
	 }
	
	 /**  
	  * Creates and returns a 2 bytes buffer containing the Little Endian representation of the integer
	  * @param i , the integer to convert
	  * @return A two bytes buffer. byte[]
	  */
	 public static byte[] intToWord(int i) {
		byte[] word = new byte[2];
		word[0] = (byte) ((i >>> 0) & 0x000000FF);
		word[1] = (byte) ((i >>> 8) & 0x000000FF);
		return word;
	 }
	
	 /** 
	 * Returns a integer (int) interpreted from a 4 bytes buffer considering Little Endian representation.
	 * @param bytes 
	 * @return integer
	 */
	 public static int dWordToInt(byte[] bytes) {
		int value;
		value =    ( ( bytes[3] << 24 ) & 0xff000000 ) 
	            |  ( ( bytes[2] << 16 ) & 0x00ff0000 ) 
	            |  ( ( bytes[1] << 8  ) & 0x0000ff00 ) 
	            |  (   bytes[0]         & 0x000000ff );
	
		return value;
	 }
	
	 /** 
	 * Returns a floating point (float) interpreted from a 4 bytes buffer considering Little Endian representation.
	 * @param bytes 
	 * @return float
	 */
	 public static float dWordToFloat(byte[] bytes) {
		int value;
		value =    ( ( bytes[3] << 24 ) & 0xff000000 ) 
	            |  ( ( bytes[2] << 16 ) & 0x00ff0000 ) 
	            |  ( ( bytes[1] << 8  ) & 0x0000ff00 ) 
	            |  (   bytes[0]         & 0x000000ff );
		float retval = Float.intBitsToFloat(value);
		return retval;
	 }
	 /**  
	  * Creates and returns a 4 bytes buffer containing the Little Endian representation of the float
	  * @param f , the float to convert
	  * @return A four bytes buffer. byte[]
	  */
	 public static byte[] floatToDWord(float f) {
		byte[] dword = new byte[4];		
		int i = Float.floatToIntBits(f);
		dword[0] = (byte) ((i >>> 0) & 0x000000FF);
		dword[1] = (byte) ((i >>> 8) & 0x000000FF);
		dword[2] = (byte) ((i >>> 16) & 0x000000FF);
		dword[3] = (byte) ((i >>> 24) & 0x000000FF);
		return dword;
	 }
	 
	 
	 
	 /**  
	  * Creates and returns a 4 bytes buffer containing the Little Endian representation of the integer
	  * @param i , the integer to convert
	  * @return A four bytes buffer. byte[]
	  */
	 public static byte[] intToDWord(int i) {
		byte[] dword = new byte[4];
		dword[0] = (byte) ((i >>> 0) & 0x000000FF);
		dword[1] = (byte) ((i >>> 8) & 0x000000FF);
		dword[2] = (byte) ((i >>> 16) & 0x000000FF);
		dword[3] = (byte) ((i >>> 24) & 0x000000FF);
		return dword;
	 }

	 /** 
	 * Returns a integer (long) interpreted from a 8 bytes buffer (quadWord) considering Little Endian representation.
	 * @param bytes 
	 * @return long
	 */
	 public static long qWordToLong(byte[] bytes) {
		long value;
		value =    (  ( (long) bytes[7] << 56 ) & 0xff00000000000000L ) 
	            |  (  ( (long) bytes[6] << 48 ) & 0x00ff000000000000L ) 
	            |  (  ( (long) bytes[5] << 40 ) & 0x0000ff0000000000L ) 
	            |  (  ( (long) bytes[4] << 32 ) & 0x000000ff00000000L )
				|  (  ( (long) bytes[3] << 24 ) & 0x00000000ff000000L ) 
	            |  (  ( (long) bytes[2] << 16 ) & 0x0000000000ff0000L ) 
	            |  (  ( (long) bytes[1] << 8  ) & 0x000000000000ff00L ) 
	            |  (    (long) bytes[0]         & 0x00000000000000ffL );

		return value;
	 }	
	 
	 /**  
	  * Creates and returns a 8 bytes buffer containing the Little Endian representation of the long
	  * @param i , the long to convert
	  * @return A eight bytes buffer. byte[]
	  */
	 public static byte[] longToQWord(long i) {
			byte[] qword = new byte[8];
			qword[0] = (byte) ((i >>> 0)  & 0x00000000000000FFL);
			qword[1] = (byte) ((i >>> 8)  & 0x00000000000000FFL);
			qword[2] = (byte) ((i >>> 16) & 0x00000000000000FFL);
			qword[3] = (byte) ((i >>> 24) & 0x00000000000000FFL);
			qword[4] = (byte) ((i >>> 32) & 0x00000000000000FFL);
			qword[5] = (byte) ((i >>> 40) & 0x00000000000000FFL);
			qword[6] = (byte) ((i >>> 48) & 0x00000000000000FFL);
			qword[7] = (byte) ((i >>> 56) & 0x00000000000000FFL);
			return qword;
	 }
	 


	 /**  
	  * Creates and returns a bytes buffer containing the concatenated passed buffers.
	  * @param buffers ,some byte arrays
	  * @return A bytes buffer. byte[]
	  */
	 public static byte[] concatBuffers(byte[]...buffers)
	 {
	    int totalLength = 0;
	    for (int i = 0; i < buffers.length; i++)
	    {
	        totalLength += buffers[i].length;
	    }

	    byte[] result = new byte[totalLength];

	    int currentIndex = 0;
	    for (int i = 0; i < buffers.length; i++)
	    {
	        System.arraycopy(buffers[i], 0, result, currentIndex, buffers[i].length);
	        currentIndex += buffers[i].length;
	    }
	    return result;
	 }
	
	/**
	 * Example of how to use these routines.
	 * 
	 */
	public static void main(String[] args) {
		
		long timeStamp0 = System.nanoTime();
		//long timeStamp0 = 1234567891011121314L;
		//long timeStamp0 = 0x0F00000000000000L;
		byte[] timeStampBuff = util.Bin.longToQWord(timeStamp0);
		System.out.print("Buff:");
		for (int i=7 ; i>=0; i--) {
			System.out.print(" " + timeStampBuff[i]);
		}
		System.out.print("\n");
		long timeStamp1 = util.Bin.qWordToLong(timeStampBuff);
		System.out.println("timestamp(0): " + timeStamp0);
		System.out.println("timestamp(1): " + timeStamp1);
		//
		byte[] dummyBuffer = util.Bin.concatBuffers(timeStampBuff, timeStampBuff);
		for (int i=dummyBuffer.length-1 ; i>=0; i--) {
			System.out.print(" " + dummyBuffer[i]);
		}
		//
	}
}
