package com.richard.datastructureutils.app;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.richard.datastructureutils.ListUtils;
import com.richard.datastructureutils.csv.CSVUtils;
import com.richard.datastructureutils.template.TemplateUtils;
import com.richard.datastructureutils.yaml.YamlUtils;

public class StringDiffApp {
	private static Logger logger = LoggerFactory.getLogger(StringDiffApp.class);
	
	public static void main(String[] args) {
		if (args.length < 5) {
			System.err.println("Usage: java StringDiffApp [baseline file] [test file] [yaml config] [template file] [output file]");
			System.exit(-1);
		}

		int i = 0;
		
		String baselineFile = args[i++];
		String testFile = args[i++];
		String yamlconfig = args[i++];
		String templateFile = args[i++];
		String output = args[i++];
		
		logger.info("Running StringDiffApp with baselineFile: {} testFile: {} yamlconfig: {} templateFile: {} output: {}", baselineFile, testFile, yamlconfig, templateFile, output);
		
		execute(baselineFile, testFile, yamlconfig, templateFile, output);
		
		logger.info("done successfully!");
	}

	public static void execute(String baselineFile, String testFile, String yamlconfig, String templateFile, String output) {
		Map<String,Object> config = YamlUtils.readYamlFile(yamlconfig);
		int startLine = (Integer)config.getOrDefault("startLine", -1);
		int startCol = (Integer)config.getOrDefault("startCol", -1);
		
		if (startLine == -1 || startCol == -1) {
			throw new IllegalStateException("cannot get startLine and startCol");
		}
		
		List<String> baselineItems = CSVUtils.readListOfStringFromCSV(new File(baselineFile), startLine, startCol);
		List<String> testItems = CSVUtils.readListOfStringFromCSV(new File(testFile), startLine, startCol);
		List<String> baselineOnlyItems = ListUtils.listDiff(baselineItems, testItems);
		List<String> testOnlyItems = ListUtils.listDiff(testItems, baselineItems);
		
		Map<String,Object> templateargs = new HashMap<String,Object>();
		templateargs.put("baselist", baselineOnlyItems);
		templateargs.put("testlist", testOnlyItems);
		
		String result = TemplateUtils.generateContent(templateFile, templateargs);
		try {
			FileUtils.writeStringToFile(new File(output), result, Charset.defaultCharset());
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
