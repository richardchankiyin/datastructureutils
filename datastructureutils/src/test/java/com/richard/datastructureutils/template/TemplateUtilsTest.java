package com.richard.datastructureutils.template;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateUtilsTest {
	private static Logger logger = LoggerFactory.getLogger(TemplateUtilsTest.class);
	@Test
	public void testGenerateContentStringDiff() {
		//String expected = ">>>>>base\nabc\ndef\n<<<<test\nxyz\nwfg";
		
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

	@Test
	public void testGenerateContentTupleDiff() {
		Map<String,String> item1 = new HashMap<String,String>();
		item1.put("field", "abc");
		item1.put("base", "0.25");
		item1.put("test", "0.23");
		item1.put("diff", "0.02");
		Map<String,String> item2 = new HashMap<String,String>();
		item2.put("field", "def");
		item2.put("base", "0.45");
		item2.put("test", "0.33");
		item2.put("diff", "0.12");
		List<Map<String,String>> itemlist = new ArrayList<Map<String,String>>();
		itemlist.add(item1);
		itemlist.add(item2);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("difflist", itemlist);
		String result = TemplateUtils.generateContent("src/test/resources/templates/tuple_diff.tpl", map);
		
		logger.debug("testGenerateContentTupleDiff result: {}", result);
		assertTrue(result.contains("abc")); 
		assertTrue(result.contains("def"));
		
		assertTrue(result.contains("0.25")); 
		assertTrue(result.contains("0.45"));
		
		assertTrue(result.contains("0.23")); 
		assertTrue(result.contains("0.33"));
		
		assertTrue(result.contains("0.02")); 
		assertTrue(result.contains("0.12"));
		
		
	}
	
	@Test
	public void testGenerateContentMatrixDiff() {
		Map<String,String> item1 = new HashMap<String,String>();
		item1.put("field1", "a");
		item1.put("field2", "b");
		item1.put("base", "0.25");
		item1.put("test", "0.23");
		item1.put("diff", "0.02");
		Map<String,String> item2 = new HashMap<String,String>();
		item2.put("field1", "a");
		item2.put("field2", "c");
		item2.put("base", "0.45");
		item2.put("test", "0.33");
		item2.put("diff", "0.12");
		List<Map<String,String>> itemlist = new ArrayList<Map<String,String>>();
		itemlist.add(item1);
		itemlist.add(item2);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("difflist", itemlist);
		
		String result = TemplateUtils.generateContent("src/test/resources/templates/matrix_diff.tpl", map);
		
		logger.debug("testGenerateContentMatrixDiff result: {}", result);
		
		assertTrue(result.contains("a"));
		assertTrue(result.contains("b"));
		assertTrue(result.contains("c"));
		
		assertTrue(result.contains("0.25")); 
		assertTrue(result.contains("0.45"));
		
		assertTrue(result.contains("0.23")); 
		assertTrue(result.contains("0.33"));
		
		assertTrue(result.contains("0.02")); 
		assertTrue(result.contains("0.12"));

	}
}
