package com.richard.datastructureutils.csv;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class CSVUtilsTest {

	@Test
	public void testReadListOfStringFromCSV() {
		List<String> result = CSVUtils.readListOfStringFromCSV(new File("src\\test\\resources\\csvfolder\\file1.csv"), 1, 2);
		List<String> expected = Arrays.asList("abc","def","xfbv","abcw");
		assertEquals(expected,result);
	}

}
