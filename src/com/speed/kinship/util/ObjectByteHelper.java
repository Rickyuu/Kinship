package com.speed.kinship.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectByteHelper {

	private static ObjectByteHelper instance;
	
	private ObjectByteHelper() {
		
	}
	
	public static ObjectByteHelper getInstance() {
		if(instance == null) {
			instance = new ObjectByteHelper();
		}
		return instance;
	}
	
	/**
	 * convert an object to byte array
	 * @param object
	 * @return
	 */
	public byte[] objToBytes(Object object) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			bytes = byteArrayOutputStream.toByteArray();
			objectOutputStream.close();
			byteArrayOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	/**
	 * convert byte array to an object
	 * @param bytes
	 * @return
	 */
	public Object bytesToObj(byte[] bytes) {
		Object object = null;
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			object = objectInputStream.readObject();
			objectInputStream.close();
			byteArrayInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
}
