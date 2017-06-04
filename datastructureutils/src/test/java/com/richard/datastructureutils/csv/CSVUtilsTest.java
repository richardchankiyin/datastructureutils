package com.richard.datastructureutils.csv;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSVUtilsTest {
	private static Logger logger = LoggerFactory.getLogger(CSVUtilsTest.class);
	@Test
	public void testReadListOfStringFromCSV() {
		List<String> result = CSVUtils.readListOfStringFromCSV(new File("src\\test\\resources\\csvfolder\\file1.csv"), 1, 2);
		List<String> expected = Arrays.asList("abc","def","xfbv","abcw");
		assertEquals(expected,result);
	}

	@Test
	public void testReadMatrixOfStringFromCSV() {
		String[][] result = CSVUtils.readMatrixOfStringFromCSV(new File("src\\test\\resources\\csvfolder\\file5.csv"), 4, 2, 3);
		
		for (int i = 0; i < result.length; ++i) {
			String[] resultitem = result[i];
			for (int j = 0; j < resultitem.length; ++j) {
				logger.debug("i: {} j: {} resultitem: {}", i, j, resultitem[j]);
			}
		}
		
		
		assertEquals("1",result[0][0]);
		assertEquals("0.5",result[0][1]);
		assertEquals("0.3",result[0][2]);
		
		assertEquals("0.5",result[1][0]);
		assertEquals("1",result[1][1]);
		assertEquals("0.4",result[1][2]);
		
		assertEquals("0.4",result[2][0]);
		assertEquals("0.5",result[2][1]);
		assertEquals("1",result[2][2]);
	}
}
