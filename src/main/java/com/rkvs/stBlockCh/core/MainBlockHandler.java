package com.rkvs.stBlockCh.core;
/**
 * 
 */
//import java.util.Date;
import java.util.*;
import java.util.Map.Entry;

import com.bessapps.stBlockCh.model.UserDetails;
import com.bessapps.stBlockCh.wrappers.DataStoreWrapper;

/**
 * @author rsaladi
 *
 */
public class MainBlockHandler {
	
	public static HashMap<Long, BlockHandler> hmap = new HashMap<Long, BlockHandler>();

	/**
	 * 1) Initializes the Application
     * 2) Create private and public Key, Validate, route it
     * 3) Route the tasks to underling objects and methods based on the conditions
	 */
	public MainBlockHandler() {
		// TODO Auto-generated constructor stub
        
	}
    
    /*
     * Initialize all necessary blocks and variables
     */
    public void initialize() {
        
    }
    
    /*
     * @RaviSaladi
     */
    public long createBlockHandler(UserDetails user) {
    	Long blockID = blockHandlerSeqID++;
    	BlockHandler bH = new BlockHandler(user, blockID);
    	hmap.put(blockID, bH);   	
    	return blockID;
    	
    }
    
    public long getBlockHandlerSeqID() {
    	return blockHandlerSeqID;
    }
 	
	private static long blockHandlerSeqID = 10000; /* default value */
	
	public void printBlockMap() {
		/* Display content using Iterator*/
		Set<Entry<Long, BlockHandler>> set = hmap.entrySet();
		Iterator<Entry<Long, BlockHandler>> iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry)iterator.next();
			System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
			System.out.println(((BlockHandler) mentry.getValue()).getBlockID());
		}
		System.out.println("Printing done!");
		
	}
}
