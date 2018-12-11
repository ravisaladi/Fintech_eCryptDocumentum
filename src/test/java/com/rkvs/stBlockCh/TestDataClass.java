package com.rkvs.stBlockCh;

import java.io.Serializable;

public class TestDataClass implements Serializable {
	public int val;
	TestDataClass(int v) { val = v; }
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestDataClass [val=" + val + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	public void printMe() {
		System.out.println("I am in the TestDataClass printMe");
		System.out.println(toString());
	}
	
	public int getVal() {
		return val;
	}
}