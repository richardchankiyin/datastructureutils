package com.richard.datastructureutils.app;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.richard.datastructureutils.ListUtils;
import com.richard.datastructureutils.csv.CSVUtils;
import com.richard.datastructureutils.template.TemplateUtils;
import com.richard.datastructureutils.yaml.YamlUtils;

public class TupleDiffApp {
	private static Logger logger = LoggerFactory.getLogger(TupleDiffApp.class);
	
	public static void main(String[] args) {
		if (args.length < 5) {
			System.err.println("Usage: java TupleDiffApp [baseline file] [test file] [yaml config] [template file] [output file]");
			System.exit(-1);
		}

		int i = 0;
		
		String baselineFile = args[i++];
		String testFile = args[i++];
		String yamlconfig = args[i++];
		String templateFile = args[i++];
		String output = args[i++];
		
		logger.info("Running TupleDiffApp with baselineFile: {} testFile: {} yamlconfig: {} templateFile: {} output: {}", baselineFile, testFile, yamlconfig, templateFile, output);
		
		execute(baselineFile, testFile, yamlconfig, templateFile, output);
		
		logger.info("done successfully!");
	}

	public static void execute(String baselineFile, String testFile, String yamlconfig, String templateFile, String output) {
		Map<String,Object> config = YamlUtils.readYamlFile(yamlconfig);
		int keyStartLine = (Integer)config.getOrDefault("keyStartLine", -1);
		int keyStartCol = (Integer)config.getOrDefault("keyStartCol", -1);
		int valueStartLine = (Integer)config.getOrDefault("valueStartLine", -1);
		int valueStartCol = (Integer)config.getOrDefault("valueStartCol", -1);
		
		if (keyStartLine == -1 || keyStartCol == -1 || valueStartLine == -1 || valueStartCol == -1) {
			throw new IllegalStateException("cannot get startLine and startCol");
		}
		
		File baselineFileLocation = new File(baselineFile);
		File testFileLocation = new File(testFile);
		List<String> baselineKeyList = CSVUtils.readListOfStringFromCSV(baselineFileLocation, keyStartLine, keyStartCol);
		List<String> baselineValueList = CSVUtils.readListOfStringFromCSV(baselineFileLocation, valueStartLine, valueStartCol);
		List<String> testKeyList = CSVUtils.readListOfStringFromCSV(testFileLocation, keyStartLine, keyStartCol);
		List<String> testValueList = CSVUtils.readListOfStringFromCSV(testFileLocation, valueStartLine, valueStartCol);
		
		List<ImmutablePair<String,String>> baselineKeyValueList = ListUtils.combineKeyListAndValueList(baselineKeyList, baselineValueList, ()->"NA", ()->"NA");
		List<ImmutablePair<String,String>> testKeyValueList = ListUtils.combineKeyListAndValueList(testKeyList, testValueList, ()->"NA", ()->"NA");
		
		List<ImmutablePair<String,String>> baselineOnlyItems = ListUtils.listDiff(baselineKeyValueList, testKeyValueList);
		List<ImmutablePair<String,String>> testOnlyItems = ListUtils.listDiff(testKeyValueList, baselineKeyValueList);
		
		List<Triple<String,String,String>> changeItems = ListUtils.listDiffSummary(baselineOnlyItems, testOnlyItems, "NA");
		
		Map<String,Object> templateargs = new HashMap<String,Object>();
		templateargs.put("difflist", triple2MapForTemplate(changeItems));
		String result = TemplateUtils.generateContent(templateFile, templateargs);
		try {
			FileUtils.writeStringToFile(new File(output), result, Charset.defaultCharset());
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static List<Map<String,String>> triple2MapForTemplate(List<Triple<String,String,String>> input) {
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		for (Triple<String,String,String> item: input) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("field", item.getLeft());
			map.put("base", item.getMiddle());
			map.put("test", item.getRight());
			map.put("diff", calculateDiff(item.getMiddle(), item.getRight()));
			result.add(map);
		}
		return result;
	}
	
	private static String calculateDiff(String base, String test) {
		try {
			double baseD = Double.parseDouble(base);
			double testD = Double.parseDouble(test);
			
			double diffD = (testD - baseD) / baseD;
			
			return String.valueOf(diffD);
		}
		catch (Exception e) {
			return "NA";
		}
	}
}
