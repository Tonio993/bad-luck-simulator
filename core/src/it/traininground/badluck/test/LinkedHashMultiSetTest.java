package it.traininground.badluck.test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import it.traininground.badluck.util.LinkedHashMultiSet;

public class LinkedHashMultiSetTest {
	
	public static void main(String[] args) {
		LinkedHashMultiSet<Integer> set = new LinkedHashMultiSet<>();
		set.add(Arrays.asList(1));
 		set.add(Arrays.asList(1, 2));
 		set.add(Arrays.asList(1, 3));
 		set.add(Arrays.asList(1, 4));
 		set.add(Arrays.asList(2, 2));
 		set.add(Arrays.asList(2, 2, 1));
 		set.add(Arrays.asList(3, 4, 1));
 		set.add(Arrays.asList(3, 4, 5));
 		set.remove(Arrays.asList(2));
		Iterator<List<Integer>> it = set.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println(set.containsSequence(2));

	}

}
