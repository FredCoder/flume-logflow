package com.logflow.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MysqlSinkTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public MysqlSinkTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(MysqlSinkTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testLogSubstring() {
		List<String> logArr = new ArrayList<String>();
		
		try {
			for (String string : logArr) {
				proess(string);
				System.out.println();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			assertTrue(false);
		}
		assertTrue(true);
		
	}
	
	public void testHeader() {
		//  headers:{file=/root/logs/error.log, lineNumber=282}
		try {
			String pro = "/root/logs/error.log";
			System.out.println(pro.substring(pro.lastIndexOf("/") + 1, pro.lastIndexOf(".")));
		}catch (Exception ex) {
			ex.printStackTrace();
			assertTrue(false);
		}
		assertTrue(true);
	}
	
	public void proess(String log) throws Exception{
		try {
			long startTime = System.currentTimeMillis();
			String after = log.substring(0, log.indexOf("["));
			List<String> logs = new LinkedList<String>(Arrays.asList(after.split(" ")));
			ListIterator<String> iterator = logs.listIterator();
			while(iterator.hasNext()) {
				String str = iterator.next();
				if (str.trim().length() == 0) {
					iterator.remove();
				} else {
					iterator.set(str.trim());
				}
			}
			long endTime = System.currentTimeMillis();
			String date = logs.get(0);
			System.out.println(date);
			String time = logs.get(1);
			System.out.println(time);
			String type = logs.get(2);
			System.out.println(type);
			String thread = log.substring(log.indexOf("[") + 1, log.indexOf("]")).trim();
			System.out.println(thread);
			log = log.substring(log.indexOf("]") + 1);
			String pack = log.substring(0, log.indexOf(":")).trim();
			System.out.println(pack);
			String content = log.substring(log.indexOf(":") + 1, log.length()).trim();
			System.out.println(content);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
