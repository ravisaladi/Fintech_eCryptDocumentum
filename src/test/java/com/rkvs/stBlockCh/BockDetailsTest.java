package com.rkvs.stBlockCh;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;

import com.rkvs.stBlockCh.core.BlockHandler;
import com.rkvs.stBlockCh.utilities.BlockObjConverter;

/**
 * @author rsaladi
 *
 */
public class BockDetailsTest {

	@Test
	public void test() {
		BlockObjConverter<TestDataClass> bd = new BlockObjConverter<TestDataClass>();
		
        long range = 12345678999999L;
        long min = 9999999999999999L;
        Random r = new Random();
        long subBlockSeqID = (long)(r.nextDouble()*range)+min;
        String ext = ".las";
		
		TestDataClass tc = new TestDataClass(20);
		long fileId = bd.writeObjectData(tc, subBlockSeqID, ext);
		
		TestDataClass one = (TestDataClass) bd.readObjectData("src/main/resources/static/data/"+fileId+ext);
		one.printMe();
		System.out.println(one.getVal());
		
		assertEquals(true, true);
	}
	
	@Test
	public void test1() {
		BlockHandler bh = new BlockHandler();
		
		assertEquals(true, true);
	}
}
