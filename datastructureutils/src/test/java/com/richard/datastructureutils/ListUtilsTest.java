package com.richard.datastructureutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListUtilsTest {
	private static Logger logger = LoggerFactory.getLogger(ListUtilsTest.class);

	@Test
	public void testListDiffIdenticalList() {
		logger.debug("testListDiffIdenticalList");
		List<String> list = Arrays.asList("abc","def");
		List<String> diff = ListUtils.listDiff(list, list);
		assertTrue(diff.isEmpty());
	}
	
	@Test
	public void testListDiffTwoItemsExtra() {
		List<String> list = Arrays.asList("abc","def");
		List<String> list2 = Arrays.asList("abc", "c","def", "d");
		List<String> diff = ListUtils.listDiff(list2,list);
		assertEquals(2,diff.size());
		assertTrue(diff.contains("c"));
		assertTrue(diff.contains("d"));
	}
	
	@Test(expected=NullPointerException.class)
	public void testListDiffBothNull() {
		ListUtils.listDiff(null, null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testListDiffCollectionNull() {
		ListUtils.listDiff(null, Arrays.asList("abc","def"));
	}
	
	@Test(expected=NullPointerException.class)
	public void testListDiffRemoveNull() {
		ListUtils.listDiff(Arrays.asList("abc","def"), null);
	}

	@Test
	public void testListOfTupleIdentical() {
		List<ImmutablePair<ImmutablePair<String,String>,String>> list = Arrays.asList(ImmutablePair.of(ImmutablePair.of("a", "b"), "1"));
		assertTrue(ListUtils.listDiff(list, list).isEmpty());
	}
	
	@Test
	public void testListOfTupleDiff() {
		List<ImmutablePair<ImmutablePair<String,String>,String>> list1 = Arrays.asList(ImmutablePair.of(ImmutablePair.of("a", "b"), "1"), ImmutablePair.of(ImmutablePair.of("a", "c"), "2"));
		List<ImmutablePair<ImmutablePair<String,String>,String>> list2 = Arrays.asList(ImmutablePair.of(ImmutablePair.of("a", "b"), "1"), ImmutablePair.of(ImmutablePair.of("a", "c"), "2.5"));
		List<ImmutablePair<ImmutablePair<String,String>,String>> listdiff = ListUtils.listDiff(list1, list2);
		logger.debug("testListOfTupleDiff listdiff: {}", listdiff);
		assertEquals(1, listdiff.size());
		assertEquals(ImmutablePair.of(ImmutablePair.of("a", "c"), "2"), listdiff.get(0));
		
	}
	
	@Test
	public void testListOfTripleIdentical() {
		List<Triple<String,String,String>> list = Arrays.asList(Triple.of("a","b","1"),Triple.of("a","c","2"));
		assertTrue(ListUtils.listDiff(list, list).isEmpty());
	}
	
	@Test
	public void testListOfTripleDiff() {
		List<Triple<String,String,String>> list1 = Arrays.asList(Triple.of("a","b","1"),Triple.of("a","c","2"));
		List<Triple<String,String,String>> list2 = Arrays.asList(Triple.of("a","b","1"),Triple.of("a","c","1.5"));
		List<Triple<String,String,String>> listdiff = ListUtils.listDiff(list1, list2);
		logger.debug("testListOfTripleDiff listdiff: {}", listdiff);
		assertEquals(1, listdiff.size());
		assertEquals(Triple.of("a", "c", "2"), listdiff.get(0));
	}
	
	@Test
	public void testListDiffSummaryNoDiff() {
		List<ImmutablePair<ImmutablePair<String,String>,String>> list = Arrays.asList(ImmutablePair.of(ImmutablePair.of("a", "b"), "1"));
		List<Triple<ImmutablePair<String,String>,String,String>> result = ListUtils.listDiffSummary(list, list, "NA");
		assertEquals(1, result.size());
		assertEquals(ImmutablePair.of("a", "b"), result.get(0).getLeft());
		assertEquals("1", result.get(0).getMiddle());
		assertEquals("1", result.get(0).getRight());
	}
	
	@Test
	public void testListDiffSummarySizeSameItemDiff() {
		List<ImmutablePair<ImmutablePair<String,String>,String>> list1 = Arrays.asList(ImmutablePair.of(ImmutablePair.of("a", "b"), "1"), ImmutablePair.of(ImmutablePair.of("a", "c"), "1"));
		List<ImmutablePair<ImmutablePair<String,String>,String>> list2 = Arrays.asList(ImmutablePair.of(ImmutablePair.of("a", "b"), "2"), ImmutablePair.of(ImmutablePair.of("a", "c"), "1"));
		List<Triple<ImmutablePair<String,String>,String,String>> result = ListUtils.listDiffSummary(list1, list2, "NA");
		assertEquals(2, result.size());
		assertEquals(ImmutablePair.of("a", "b"), result.get(0).getLeft());
		assertEquals("1", result.get(0).getMiddle());
		assertEquals("2", result.get(0).getRight());
		assertEquals(ImmutablePair.of("a", "c"), result.get(1).getLeft());
		assertEquals("1", result.get(1).getMiddle());
		assertEquals("1", result.get(1).getRight());
	}
	
	@Test
	public void testListDiffSummarySizeDiffItemDiff() {
		List<ImmutablePair<ImmutablePair<String,String>,String>> list1 = Arrays.asList(ImmutablePair.of(ImmutablePair.of("a", "b"), "1"), ImmutablePair.of(ImmutablePair.of("a", "c"), "1"));
		List<ImmutablePair<ImmutablePair<String,String>,String>> list2 = Arrays.asList(ImmutablePair.of(ImmutablePair.of("a", "b"), "1"));
		List<Triple<ImmutablePair<String,String>,String,String>> result = ListUtils.listDiffSummary(list1, list2, "NA");
		
		assertEquals(2, result.size());
		assertEquals(ImmutablePair.of("a", "b"), result.get(0).getLeft());
		assertEquals("1", result.get(0).getMiddle());
		assertEquals("1", result.get(0).getRight());
		
		assertEquals(ImmutablePair.of("a", "c"), result.get(1).getLeft());
		assertEquals("1", result.get(1).getMiddle());
		assertEquals("NA", result.get(1).getRight());
		
		List<Triple<ImmutablePair<String,String>,String,String>> result2 = ListUtils.listDiffSummary(list2, list1, "NA");
		
		assertEquals(2, result2.size());
		assertEquals(ImmutablePair.of("a", "b"), result2.get(0).getLeft());
		assertEquals("1", result2.get(0).getMiddle());
		assertEquals("1", result2.get(0).getRight());
		
		assertEquals(ImmutablePair.of("a", "c"), result2.get(1).getLeft());
		assertEquals("NA", result2.get(1).getMiddle());
		assertEquals("1", result2.get(1).getRight());
	}
	
	@Test
	public void testListDiffSummarySizeSameItemDiffMoreItems() {
		List<ImmutablePair<ImmutablePair<String,String>,String>> list1 = Arrays.asList(ImmutablePair.of(ImmutablePair.of("a", "b"), "1"), ImmutablePair.of(ImmutablePair.of("a", "c"), "1"));
		List<ImmutablePair<ImmutablePair<String,String>,String>> list2 = Arrays.asList(ImmutablePair.of(ImmutablePair.of("a", "b"), "1"), ImmutablePair.of(ImmutablePair.of("b", "c"), "1"));
		List<Triple<ImmutablePair<String,String>,String,String>> result = ListUtils.listDiffSummary(list1, list2, "NA");
		
		assertEquals(3, result.size());
		assertEquals(ImmutablePair.of("a", "b"), result.get(0).getLeft());
		assertEquals("1", result.get(0).getMiddle());
		assertEquals("1", result.get(0).getRight());
		
		assertEquals(ImmutablePair.of("a", "c"), result.get(1).getLeft());
		assertEquals("1", result.get(1).getMiddle());
		assertEquals("NA", result.get(1).getRight());
		
		assertEquals(ImmutablePair.of("b", "c"), result.get(2).getLeft());
		assertEquals("NA", result.get(2).getMiddle());
		assertEquals("1", result.get(2).getRight());
	}
	
	@Ignore
	@Test
	public void testListDiffPerf100Items() {
		List<Integer> list1 = new ArrayList<Integer>();
		for (int i = 0; i < 100; i++) {
			list1.add(i);
		}
		List<Integer> list2 = new ArrayList<Integer>();
		for (int i = 0; i < 200; i+=2) {
			list2.add(i);
		}
		
		long t11 = System.currentTimeMillis();
		List<Integer> t1result = ListUtils.listDiff(list1, list2);
		long t12 = System.currentTimeMillis();
		long timediff1 = t12 - t11;
		logger.debug("testListDiffPerf100Items ListUtils.listDiff -- start time: {} end time: {} time diff: {}", t11, t12, timediff1);
		logger.debug("testListDiffPerf100Items t1result: {}", t1result);
		
		long t21 = System.currentTimeMillis();
		List<Integer> t2result = listDiff(list1, list2);
		long t22 = System.currentTimeMillis();
		
		long timediff2 = t22 - t21;
		logger.debug("testListDiffPerf100Items listDiff -- start time: {} end time: {} time diff: {}", t21, t22, timediff2);
		logger.debug("testListDiffPerf100Items t2result: {}", t2result);
		
	}
	
	@Ignore
	@Test
	public void testListDiffPerf10000Items() {
		List<Integer> list1 = new ArrayList<Integer>();
		for (int i = 0; i < 10000; i++) {
			list1.add(i);
		}
		List<Integer> list2 = new ArrayList<Integer>();
		for (int i = 0; i < 20000; i+=2) {
			list2.add(i);
		}
		
		long t11 = System.currentTimeMillis();
		List<Integer> t1result = ListUtils.listDiff(list1, list2);
		long t12 = System.currentTimeMillis();
		long timediff1 = t12 - t11;
		logger.debug("testListDiffPerf10000Items ListUtils.listDiff -- start time: {} end time: {} time diff: {}", t11, t12, timediff1);
		logger.debug("testListDiffPerf10000Items t1result: {}", t1result);
		
		long t21 = System.currentTimeMillis();
		List<Integer> t2result = listDiff(list1, list2);
		long t22 = System.currentTimeMillis();
		
		long timediff2 = t22 - t21;
		logger.debug("testListDiffPerf10000Items listDiff -- start time: {} end time: {} time diff: {}", t21, t22, timediff2);
		logger.debug("testListDiffPerf10000ItemstestListDiffPerf10000Items t2result: {}", t2result);
		
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
 