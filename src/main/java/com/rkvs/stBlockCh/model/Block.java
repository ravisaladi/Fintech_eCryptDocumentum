package com.rkvs.stBlockCh.model;
/**
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author rsaladi
 *
 */
public class Block {
	//Block Header Information
	private Block next;
	private Block prev;
	long blockSeqId;
	long blockIdentifier;
	
	//Block Data information
	String filePath;
	DataStore dsData;
	String publicKey;
	
	/* Defult constructor */
	public Block() {}

	/**
	 * 
	 */
	public Block(String fp, String pbKey, long bId, long bSeqId) {
		filePath = fp;
		publicKey = pbKey;
		blockIdentifier = bId;
		blockSeqId = bSeqId;
		dsData = new DataStore(filePath, publicKey);
	}
	
	/*
	 * Encrypt the data
	 */
	private InputStream encryptData() {
		InputStream tHash = null;

		return tHash;
	}
	
	/**
	 * @return the next
	 */
	public Block getNext() {
		return next;
	}

	/**
	 * @param next the next to set
	 */
	public void setNext(Block next) {
		this.next = next;
	}

	/**
	 * @return the prev
	 */
	public Block getPrev() {
		return prev;
	}

	/**
	 * @param prev the prev to set
	 */
	public void setPrev(Block prev) {
		this.prev = prev;
	}

	/**
	 * @return the dsData
	 */
	public DataStore getDsData() {
		return dsData;
	}
	
    /**
     * @param fis
     * @param fos
     * @param key
     */
    public void doEncrypt(FileInputStream fis, FileOutputStream fos, String key){
        encrypt(fis, fos, key);
    }

    /**
     * @param fis
     * @param fos
     * @param key
     */
    public void doDecrypt(FileInputStream fis, FileOutputStream fos, String key){
        decrypt(fis, fos, key);
    }


    /**
     * @author codebrat
     * @version 1.0 04/12/2018
     * @param inputFile     a Stream of input file to be encrypted
     * @param outputFile    a Stream for encrypted output file
     * @param password      password used to encrypt the document
     */
    private void encrypt(FileInputStream inputFile, FileOutputStream outputFile, String password){
        try {
            // password, iv and salt should be transferred to the other end
            // in a secure manner

            // salt is used for encoding
            // writing it to a file
            // salt should be transferred to the recipient securely
            // for decryption
            byte[] salt = new byte[8];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(salt);
            FileOutputStream saltOutFile = new FileOutputStream("salt.enc");
            saltOutFile.write(salt);
            saltOutFile.close();

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
            SecretKey secretKey = factory.generateSecret(keySpec);
            SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

            //
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();

            // iv adds randomness to the text and just makes the mechanism more
            // secure
            // used while initializing the cipher
            // file to store the iv
            FileOutputStream ivOutFile = new FileOutputStream("iv.enc");
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
            ivOutFile.write(iv);
            ivOutFile.close();

            //file encryption
            byte[] input = new byte[64];
            int bytesRead;

            while ((bytesRead = inputFile.read(input)) != -1) {
                byte[] output = cipher.update(input, 0, bytesRead);
                if (output != null)
                    outputFile.write(output);
            }

            byte[] output = cipher.doFinal();
            if (output != null)
                outputFile.write(output);

            inputFile.close();
            outputFile.flush();
            outputFile.close();

            System.out.println("File Encrypted.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author codebrat
     * @version 1.0 12/04/2018
     * @param inputStream   FileInputStream of input file
     * @param outputStream  FileOutputStream for output file
     * @param password      String input of password for description
     */
    private void decrypt(FileInputStream inputStream, FileOutputStream outputStream, String password){
        try{

            // reading the salt
            // user should have secure mechanism to transfer the
            // salt, iv and password to the recipient
            FileInputStream saltFis = new FileInputStream("salt.enc");
            byte[] salt = new byte[8];
            saltFis.read(salt);
            saltFis.close();

            // reading the iv
            FileInputStream ivFis = new FileInputStream("iv.enc");
            byte[] iv = new byte[16];
            ivFis.read(iv);
            ivFis.close();

            SecretKeyFactory factory = SecretKeyFactory
                    .getInstance("PBKDF2WithHmacSHA1");
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,
                    256);
            SecretKey tmp = factory.generateSecret(keySpec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

            // file decryption
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
            byte[] in = new byte[64];
            int read;
            while ((read = inputStream.read(in)) != -1) {
                byte[] output = cipher.update(in, 0, read);
                if (output != null)
                    outputStream.write(output);
            }

            byte[] output = cipher.doFinal();
            if (output != null)
                outputStream.write(output);
            inputStream.close();
            outputStream.flush();
            outputStream.close();
            System.out.println("File Decrypted.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }
}
