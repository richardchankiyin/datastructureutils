package com.richard.datastructureutils.template;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class TemplateUtilsTest {
	private static Logger logger = LoggerFactory.getLogger(TemplateUtilsTest.class);
	@Test
	public void testGenerateContentStringDiff() {
		String expected = ">>>>>base\nabc\ndef\n<<<<test\nxyz\nwfg";
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("baselist", Arrays.asList("abc","def"));
		map.put("testlist", Arrays.asList("xyz","wfg"));
		String result = TemplateUtils.generateContent("src/test/resources/templates/string_diff.tpl", map);
		logger.debug("testGenerateContentStringDiff result: {}", result);
		assertTrue(result.contains("abc"));
		assertTrue(result.contains("def"));
		assertTrue(result.contains("xyz"));
		assertTrue(result.contains("wfg"));
	}

}
