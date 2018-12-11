package com.rkvs.stBlockCh.core;
/**
 * 
 */
//import java.util.Date;
import java.util.*;

/**
 * @author rsaladi
 *
 */
public class MainBlockHandler {

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
    private void initialize() {
        
    }
    
    /*
     * This will updateData or Create new Block chain
     */
    private long updateData(long docID, ArrayList<String> aList) {
        boolean status = false;
        
        if(docID == 0) {
            return 0;
        }
        
        /* Check if file path is null, then it must be existing document*/
        if(checkDocumentIDValid(docID) != false) {
            status = sbh.updateData(docID, aList);
            if(status == false) {
                //Handle the error
                return 0;
            }
        } else {
            // This document id is not available inform the requestor
            return 0;
        }
        return docID;        
    }
    
    private boolean checkDocumentIDValid(long docID) {
        boolean status = false;
        
        
        return status;
    }
    
    /*
     * function overloading with file name
     */
    private long updateData(String fNANDP, ArrayList<String> aList) {
        if(fNANDP == null) {
            return 0;
        }
        
        long docID = sbh.createBlockChain(fNANDP, aList);
        if(docID == 0) {
            // Handle error
            return 0;
        }
        
        return docID;
    }
	
	private BlockHandler sbh;
	Date timeStamp;
	String owner;
	String privateKey;
	String publicKey;

}
