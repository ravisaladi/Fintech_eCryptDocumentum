package com.rkvs.stBlockCh.core;
/**
 * 
 */
import java.util.ArrayList;
import java.util.HashMap;

import com.bessapps.stBlockCh.model.UserDetails;
import com.bessapps.stBlockCh.wrappers.DataStoreWrapper;

/**
 * @author rsaladi
 *
 */
public class MasterECryptEngine implements ECryptServices {
	
	private MainBlockHandler mBH;
	
	/**
	 * 
	 */
	public MasterECryptEngine() {
		// TODO Auto-generated constructor stub
		mBH = new MainBlockHandler();
	}
	
	/*
	 * @RaviSaladi
	 */
	@Override
	public void initECryptServices() {
		System.out.println("In initECryptServices");
	}
	
	/*
	 * @RaviSaladi
	 */
	@Override
	public long iniateBlock(UserDetails user) {
		long bID = mBH.createBlockHandler(user);
		//mBH.printBlockMap();
		System.out.println("In iniateBlock");
		return bID;
	}
	
	@Override
	public void insertDocument(DataStoreWrapper dsw) {
		//mBH.insertBlock()
		System.out.println("In insertDocument");
		mBH.printBlockMap();
	}
	
	@Override
	public void updateBlock(String userid, String passcode, String desc) {
		long docID = 0;
		//mBH.updateData(docID, desc);
		System.out.println("In updateBlock");
	}
	
}
