package com.rkvs.stBlockCh.model;
/**
 * 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author rsaladi
 * This Class deals with the data how to store and how to retrieve
 *
 */
public class DataStore {
	private InputStream data;
	String timeStamp;
	String publicKey;
	
	public DataStore(String filePath, String pubKey){
		try {
			data = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/YY-hh:mm:ss");
        timeStamp = simpleDateFormat.format(new Date());
        publicKey = pubKey;
	}
	
	/**
	 * @return the data
	 */
	public InputStream getData() {
		return data;
	}

	/**
	 * @return the timeStamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @return the publicKey
	 */
	public String getPublicKey() {
		return publicKey;
	}

}
