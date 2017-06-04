package com.richard.datastructureutils.csv;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSVUtils {
	private static Logger logger = LoggerFactory.getLogger(CSVUtils.class);
	/**
	 * f - file
	 * startLine - start from 0
	 * startCol - start from 0
	 * 
	 * @param f
	 * @param startLine
	 * @param startCol
	 * @return
	 */
	public static List<String> readListOfStringFromCSV(File f, int startLine, int startCol) {
		try {
			CSVParser parser = CSVParser.parse(f, Charset.defaultCharset(), CSVFormat.DEFAULT);
			Iterator<CSVRecord> it = parser.iterator();
			CSVRecord record = null;
			for (int i = 0; i <= startLine; ++i) {
				record = it.next();
			}
			Iterator<String> itStr = record.iterator();
			int count = 0;
			List<String> result = new ArrayList<String>();
			while (itStr.hasNext()) {
				String val = itStr.next();
				if (startCol <= count) {
					result.add(val);
				}
				++count;
			}
			
			return result;
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
	
	/**
	 * 
	 * f - file
	 * startLine - start from 0
	 * startCol - start from 0
	 * noOfRow - no of row in the result
	 * 
	 * @param f
	 * @param startLine
	 * @param startCol
	 * @param noOfRow
	 * @return
	 */
	public static String[][] readMatrixOfStringFromCSV(File f, int startLine, int startCol, int noOfRow) {
		try {
			CSVParser parser = CSVParser.parse(f, Charset.defaultCharset(), CSVFormat.DEFAULT);
			Iterator<CSVRecord> it = parser.iterator();
			CSVRecord record = null;
			for (int i = 0; i <= startLine; ++i) {
				record = it.next();
			}
			
			List<String[]> listOfStringArray = new ArrayList<String[]>();
			for (int i = 0; i < noOfRow; ++i) {
				Iterator<String> itStr = record.iterator();
				int count = 0;
				List<String> result = new ArrayList<String>();
				while (itStr.hasNext()) {
					String val = itStr.next();
					if (startCol <= count) {
						result.add(val);
					}
					++count;
				}
				logger.debug("result: {}", result);
				
				listOfStringArray.add(result.toArray(new String[result.size()]));
				if (it.hasNext())
					record = it.next();
			}
			
			return listOfStringArray.toArray(new String[listOfStringArray.size()][]);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
	
}
