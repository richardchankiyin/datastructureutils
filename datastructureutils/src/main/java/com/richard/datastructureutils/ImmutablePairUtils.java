package com.richard.datastructureutils;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class ImmutablePairUtils {
	
	public static <K,C> boolean isKeyEqualsInImmutablePairs(ImmutablePair<K,C> item1, ImmutablePair<K,C> item2) {
		if (item1 != null && item2 != null) {
			return item1.getLeft().equals(item2.getLeft());
		} else {
			return false;
		}
	}
}
