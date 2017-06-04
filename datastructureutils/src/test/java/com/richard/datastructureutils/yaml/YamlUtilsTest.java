package com.richard.datastructureutils.yaml;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class YamlUtilsTest {

	@Test
	public void testReadYamlFile() {
		Map<String,Object> result = YamlUtils.readYamlFile("src/test/resources/ymlfolder/string_diff.yml");
		assertEquals(1,result.get("startLine"));
		assertEquals(2,result.get("startCol"));
	}

}
