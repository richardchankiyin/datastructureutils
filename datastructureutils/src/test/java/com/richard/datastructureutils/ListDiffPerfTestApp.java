package com.richard.datastructureutils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListDiffPerfTestApp {
	private static Logger logger = LoggerFactory.getLogger(ListDiffPerfTestApp.class);
	public static void main(String[] args) {
		int noOfItems = 100;
		if (args.length >= 1) {
			try {
				noOfItems = Integer.valueOf(args[0]);
			}
			catch (NumberFormatException ne) {
				logger.warn("cannot extract no of items", ne);
			}
		}
		logger.debug("no of items: {}", noOfItems);

		List<Integer> list1 = getItems(noOfItems, 1);
		List<Integer> list2 = getItems(noOfItems, 2);
		
		assess(list1,list2);
	}


	public static void assess(List<Integer> list1, List<Integer> list2) {
		long t11 = System.currentTimeMillis();
		List<Integer> t1result = ListUtils.listDiff(list1, list2);
		long t12 = System.currentTimeMillis();
		long timediff1 = t12 - t11;
		logger.debug("testListDiffPerfItems ListUtils.listDiff -- start time: {} end time: {} time diff: {}", t11, t12, timediff1);
		//logger.debug("testListDiffPerfItems t1result: {}", t1result);
		
		long t21 = System.currentTimeMillis();
		List<Integer> t2result = listDiff(list1, list2);
		long t22 = System.currentTimeMillis();
		
		long timediff2 = t22 - t21;
		logger.debug("testListDiffPerfItems listDiff -- start time: {} end time: {} time diff: {}", t21, t22, timediff2);
		//logger.debug("testListDiffPerfItemstestListDiffPerfItems t2result: {}", t2result);

	}
	
	
	private static List<Integer> getItems(int noOfItems, int multiplier) {
		int ceiling = noOfItems * multiplier;
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < ceiling; i+=multiplier) {
			list.add(i);
		}
		return list;
	}
	
	public static <E> List<E> listDiff(List<E> collection, List<E> remove) {
		final List<E> list = new ArrayList<E>();
		
	    for (final E obj : collection) {
            if (!remove.contains(obj)) {
                list.add(obj);
            }
        }
        
        return list;
	}

}
