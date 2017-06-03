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

public class CSVUtils {

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
	
}
