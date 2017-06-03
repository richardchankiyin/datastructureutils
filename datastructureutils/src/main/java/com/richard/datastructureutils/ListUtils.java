package com.richard.datastructureutils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Triple;

public class ListUtils {

	public static <E> List<E> listDiff(List<E> collection, List<E> remove) {
		final List<E> list = new ArrayList<E>();
		// in order to make the comparison faster, will change to HashSet
		// hashset contains -> O(1) and arraylist contains -> O(n)
		
		Set<E> collectionSet = new HashSet<E>(collection);
		Set<E> removeSet = new HashSet<E>(remove);
		
        for (final E obj : collectionSet) {
            if (!removeSet.contains(obj)) {
                list.add(obj);
            }
        }
        
        return list;
	}
	
	public static <K,C> List<Triple<K,C,C>> listDiffSummary(List<ImmutablePair<K,C>> change1, List<ImmutablePair<K,C>> change2, C defaultValue) {
		List<ImmutablePair<K,C>> change1Sorted = new ArrayList<ImmutablePair<K,C>>(change1);
		List<ImmutablePair<K,C>> change2Sorted = new ArrayList<ImmutablePair<K,C>>(change2);
		Collections.sort(change1Sorted);
		Collections.sort(change2Sorted);
		Queue<ImmutablePair<K,C>> change1Queue = new LinkedList<ImmutablePair<K,C>>(change1Sorted);
		Queue<ImmutablePair<K,C>> change2Queue = new LinkedList<ImmutablePair<K,C>>(change2Sorted);
		
		List<Triple<K,C,C>> result = new ArrayList<Triple<K,C,C>>();
		
		
		while (change1Queue.size() > 0 || change2Queue.size() > 0) {
			ImmutablePair<K,C> item1 = change1Queue.peek();
			ImmutablePair<K,C> item2 = change2Queue.peek();
			
			if (ImmutablePairUtils.isKeyEqualsInImmutablePairs(item1, item2)) {
				result.add(Triple.of(item1.getLeft(), item1.getRight(), item2.getRight()));
				change1Queue.poll();
				change2Queue.poll();
			} else {
				if (change1Queue.size() >= change2Queue.size()) {
					result.add(Triple.of(item1.getLeft(), item1.getRight(), defaultValue));
					change1Queue.poll();
				} else {
					result.add(Triple.of(item2.getLeft(), defaultValue, item2.getRight()));
					change2Queue.poll();
				}
			}
		}
		
		return result;
	}

}
