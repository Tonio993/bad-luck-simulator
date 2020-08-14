package it.traininground.badluck.test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import it.traininground.badluck.util.LinkedHashMultiKeyMap;

public class LinkedHashMultiKeyMapTest {
	
	public static void main(String[] args) {
		Map<List<Integer>, String> map = new LinkedHashMultiKeyMap<>();
		map.put(Arrays.asList(1, 2, 3), "A");
		map.put(Arrays.asList(4, 5, 6), "B");
		map.put(Arrays.asList(1, 2, 6), "C");
		map.put(Arrays.asList(1, 2, 4), "A");
		map.put(Arrays.asList(1, 2, 3), "D");
		map.put(Arrays.asList(1, 3, 3), "D");
		map.remove(Arrays.asList(1, 3, 3));
		System.out.println(map);
		System.out.println(map.keySet());
 		System.out.println(map.values());
 		System.out.println(map.size());
	}

}
