package com.richard.datastructureutils;

import static org.junit.Assert.*;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Test;

public class ImmutablePairUtilsTest {

	@Test
	public void testIsKeyEqualsInImmutablePairs() {
		ImmutablePair<String,String> item11 = ImmutablePair.of("a", "a");
		ImmutablePair<String,String> item12 = ImmutablePair.of("a", "a");
		assertTrue(ImmutablePairUtils.isKeyEqualsInImmutablePairs(item11, item12));
		
		ImmutablePair<String,String> item21 = ImmutablePair.of("a", "a");
		ImmutablePair<String,String> item22 = ImmutablePair.of("b", "a");
		assertFalse(ImmutablePairUtils.isKeyEqualsInImmutablePairs(item21, item22));
		
		assertFalse(ImmutablePairUtils.isKeyEqualsInImmutablePairs(item11, null));
		assertFalse(ImmutablePairUtils.isKeyEqualsInImmutablePairs(null, item11));
		assertFalse(ImmutablePairUtils.isKeyEqualsInImmutablePairs(null, null));
	}

}
