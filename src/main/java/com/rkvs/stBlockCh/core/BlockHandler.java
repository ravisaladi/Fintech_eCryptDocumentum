/**
 * 
 */
package com.rkvs.stBlockCh.core;

/**
 * 
 */
import java.util.*;
import com.bessapps.stBlockCh.model.Block;
import com.bessapps.stBlockCh.model.UserDetails;

/**
 * @author rsaladi
 *
 */
public class BlockHandler {
	
	private final long BlockIdentifier;
	private long numOfBlocks = 0;
	private String ownerName;
	private String onwerSecret;
	private Block blk;
	private UserDetails owner;
	
	/**
	 * @author rsaladi
	 */
	public BlockHandler(UserDetails user, long id) {
		BlockIdentifier = id;
		owner = user;
		System.out.println("Praise the Lord jesus!");
		System.out.println(owner.getUser() + " " + owner.getSecrete());
	}
	
	public long getBlockID() {
		return BlockIdentifier;
	}
	
	public UserDetails getBlockOwner() {
		return owner;
	}

}
