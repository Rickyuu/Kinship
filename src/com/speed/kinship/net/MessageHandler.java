package com.speed.kinship.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessageHandler {
	
	public Object handleMessage(MethodMessage methodMessage) {
		try {
			Socket socket = new Socket(Constants.HOST_NAME, Constants.PORT_NUM);
			OutputStream outputStream = socket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			byte[] methodMessageBytes = ObjectByteHelper.getInstance().objToBytes(methodMessage);
			int writeSize = methodMessageBytes.length;
			dataOutputStream.writeInt(writeSize);
			dataOutputStream.write(methodMessageBytes);
			
			InputStream inputStream = socket.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			int resultSize = dataInputStream.readInt();
			byte[] resultBytes = new byte[resultSize];
			int currentLength = 0;
			while(currentLength < resultSize) {
				currentLength += dataInputStream.read(resultBytes, currentLength, resultSize-currentLength);
			}
			Object resultObject = ObjectByteHelper.getInstance().bytesToObj(resultBytes);
			
			dataOutputStream.close();
			outputStream.close();
			dataInputStream.close();
			inputStream.close();
			socket.close();
			return resultObject;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
