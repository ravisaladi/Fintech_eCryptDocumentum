package com.rkvs.stBlockCh.core;

import com.bessapps.stBlockCh.model.UserDetails;
import com.bessapps.stBlockCh.wrappers.DataStoreWrapper;

public interface ECryptServices {
	void initECryptServices();
	public long iniateBlock(UserDetails user);
	public void insertDocument(DataStoreWrapper dsw);
	public void updateBlock(String userid, String passcode, String desc);

}
