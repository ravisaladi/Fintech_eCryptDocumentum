/**
 * 
 */
package com.rkvs.stBlockCh.core;

/**
 * 
 */
import java.util.*;
import com.rkvs.stBlockCh.model.Block;

/**
 * @author rsaladi
 *
 */
public class BlockHandler {

	/**
	 * 
	 */
	public BlockHandler() {
		// TODO Auto-generated constructor stub
        long range = 12345678999999L;
        long min = 9999999999999999L;
        Random r = new Random();
        subBlockSeqID = (long)(r.nextDouble()*range)+min;
	}
	
	private Block sb;
	private Block root;
	private long subBlockSeqID;
	
	public long getSubBlockSeqID() {
		return subBlockSeqID;
	}
    
    /*
     * This method will update existing data
     */
    public boolean updateData(long docID, ArrayList<String> aList) {
        boolean status = false;
        
        
        return status;
    }
    
    /*
     * This method will create new Block Chanin
     */
    public long createBlockChain(String filePath, ArrayList<String> aList) {
        long blockID = 0;
        
        
        return blockID;
    }  

}
