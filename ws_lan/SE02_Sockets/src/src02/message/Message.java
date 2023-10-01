/*
 *  Message.java
 *
 *  @author Jose Simo. (c) ai2-DISCA-UPV Creative Commons.
 *  
 *  Rev: 2017
 */
package src02.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import src02.util.Bin;
import src02.util.Net;

public class Message {

	public static byte magicNumber = (byte) 0xF1;
	public byte[] payload;
	
	public Message() {
		payload = null;
	}
	
	public Message(byte[] pPayload) {
		payload = pPayload;
	}
	
	public boolean readFromInputStream(InputStream is) throws IOException {		
		//Allocate Message information buffers
		byte[] magicNumerBuff = new byte[1];
		byte[] payloadSizeBuff = new byte[4];
		
		//Read the message magic number and check it.
		do {
			Net.read(is,magicNumerBuff, 1);	
		} while (magicNumerBuff[0] != Message.magicNumber);

		//Read the "payload size" buffer
	    Net.read(is,payloadSizeBuff, 4);	
	    int size = Bin.dWordToInt(payloadSizeBuff);
	    //Read payload
	    this.payload = new byte[size];
	    Net.read(is,this.payload, this.payload.length);
	    //
		return true;
	}
	
	public boolean writeToOutputStream(OutputStream os) throws IOException {
		
		//Write magic number
		byte[] magicBuffer = new byte[1];
		magicBuffer[0] = Message.magicNumber;
		os.write(magicBuffer, 0, 1);
		//write payload length
		int size = (this.payload != null) ? this.payload.length : 0;
		byte[] sizeBuff = Bin.intToDWord(size);
		os.write(sizeBuff,0,sizeBuff.length);
		//write payload
		if (this.payload != null) {
			os.write(this.payload,0,this.payload.length);	
		}	
		return true;
	}
		
}
