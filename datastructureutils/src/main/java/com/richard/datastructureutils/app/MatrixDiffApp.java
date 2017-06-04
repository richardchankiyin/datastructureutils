package com.richard.datastructureutils.app;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.richard.datastructureutils.ListUtils;
import com.richard.datastructureutils.csv.CSVUtils;
import com.richard.datastructureutils.yaml.YamlUtils;

public class MatrixDiffApp {
	private static Logger logger = LoggerFactory.getLogger(MatrixDiffApp.class);
	
	public static void main(String[] args) {
		if (args.length < 5) {
			System.err.println("Usage: java MatrixDiffApp [baseline file] [test file] [yaml config] [template file] [output file]");
			System.exit(-1);
		}

		int i = 0;
		
		String baselineFile = args[i++];
		String testFile = args[i++];
		String yamlconfig = args[i++];
		String templateFile = args[i++];
		String output = args[i++];
		
		logger.info("Running MatrixDiffApp with baselineFile: {} testFile: {} yamlconfig: {} templateFile: {} output: {}", baselineFile, testFile, yamlconfig, templateFile, output);
		
		execute(baselineFile, testFile, yamlconfig, templateFile, output);
		
		logger.info("done successfully!");

	}
	
	public static void execute(String baselineFile, String testFile, String yamlconfig, String templateFile, String output) {
		Map<String,Object> config = YamlUtils.readYamlFile(yamlconfig);
		int keyStartLine = (Integer)config.getOrDefault("keyStartLine", -1);
		int keyStartCol = (Integer)config.getOrDefault("keyStartCol", -1);
		int matrixStartLine = (Integer)config.getOrDefault("matrixStartLine", -1);
		int matrixStartCol = (Integer)config.getOrDefault("matrixStartCol", -1);
		
		if (keyStartLine == -1 || keyStartCol == -1 || matrixStartLine == -1 || matrixStartCol == -1) {
			throw new IllegalStateException("cannot get startLine and startCol");
		}
		
		File baselineFileLocation = new File(baselineFile);
		File testFileLocation = new File(testFile);
		
		List<String> baselineKeyList = CSVUtils.readListOfStringFromCSV(baselineFileLocation, keyStartLine, keyStartCol);
		String[][] baselineMatrix = CSVUtils.readMatrixOfStringFromCSV(baselineFileLocation, matrixStartLine, matrixStartCol, baselineKeyList.size());
		List<String> testKeyList = CSVUtils.readListOfStringFromCSV(testFileLocation, keyStartLine, keyStartCol);
		String[][] testMatrix = CSVUtils.readMatrixOfStringFromCSV(testFileLocation, matrixStartLine, matrixStartCol, testKeyList.size());
		
		List<Triple<String,String,String>> baselineKeyMatrix = ListUtils.getCombinations(baselineKeyList, baselineMatrix, false);
		List<Triple<String,String,String>> testKeyMatrix = ListUtils.getCombinations(testKeyList, testMatrix, false);
		
		List<Triple<String,String,String>> baselineOnlyItems = ListUtils.listDiff(baselineKeyMatrix, testKeyMatrix);
		List<Triple<String,String,String>> testOnlyItems = ListUtils.listDiff(testKeyMatrix, baselineKeyMatrix);
		
		//TODO
		
	}

}
