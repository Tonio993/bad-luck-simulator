package it.traininground.badluck.util.pathfind;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import it.traininground.badluck.util.statistics.TimeStats;

public abstract class PathFindAStar<T> implements PathFind<T> {
	
	public List<T> findPath(T from, T to) {
		TimeStats.start("Total");
		TreeMap<Float, LinkedList<T>> openSet = new TreeMap<>();
		openSet.put(0f, new LinkedList<T>(Arrays.asList(from)));
		
		HashSet<T> closeSet = new HashSet<>();
		
		Map<T, T> cameFrom = new HashMap<>();
		
		Map<T, Float> gScore = new HashMap<>();
		gScore.put(from, 0f);
		
		Map<T, Float> fScore = new HashMap<>();
		gScore.put(from, heuristic(from, to));
		
		while (!openSet.isEmpty()) {
			TimeStats.start("Lowest F");
			LinkedList<T> lowestF = openSet.firstEntry().getValue();
			T current = lowestF.getFirst();
			TimeStats.stop("Lowest F");
			if (current.equals(to)) {
				System.out.println("Total Time: " + TimeStats.stopAndGet("Total") + "ms");
				System.out.println("Lowest F Time: " + TimeStats.get("Lowest F") + "ms");
				System.out.println("Score Time: " + TimeStats.get("Score") + "ms");
				System.out.println("Score Step 1 Time: " + TimeStats.get("Score Step 1") + "ms");
				System.out.println("Score Step 2 Time: " + TimeStats.get("Score Step 2") + "ms");
				System.out.println("Score Step 3 Time: " + TimeStats.get("Score Step 3") + "ms");
				System.out.println("Neighbors Time: " + TimeStats.get("Neighbors") + "ms");
				return reconstructPath(cameFrom, current);
			}
			lowestF.removeFirst();
			if (lowestF.isEmpty()) {
				openSet.remove(openSet.firstKey());
			}
			TimeStats.start("Neighbors");
			Set<T> neighbors = neighbors(current); 
			TimeStats.stop("Neighbors");
			for (T neighbor : neighbors) {
				if (closeSet.contains(neighbor)) {
					continue;
				}
				TimeStats.start("Score");
				TimeStats.start("Score Step 1");
				float currentG = gScore.get(current) + distance(current, neighbor);
				TimeStats.stop("Score Step 1");
				TimeStats.start("Score Step 2");
				boolean flag = currentG < gScore.getOrDefault(neighbor, Float.MAX_VALUE); 
				TimeStats.stop("Score Step 2");
				TimeStats.start("Score Step 3");
				if (flag) {
					cameFrom.put(neighbor, current);
					gScore.put(neighbor, currentG);
					fScore.put(neighbor, currentG + heuristic(neighbor, to));
					LinkedList<T> openSetPut = openSet.get(fScore.get(neighbor));
					if (openSetPut == null) {
						openSet.put(fScore.get(neighbor), (openSetPut = new LinkedList<T>()));
					}
					openSetPut.add(neighbor);
				}
				TimeStats.stop("Score Step 3");
				TimeStats.stop("Score");
			}
			closeSet.add(current);
		}
		System.out.println("Total Time: " + TimeStats.stopAndGet("Total") + "ms");
		System.out.println("Lowest F Time: " + TimeStats.get("Lowest F") + "ms");
		System.out.println("Score Time: " + TimeStats.get("Score") + "ms");
		System.out.println("Score Step 1 Time: " + TimeStats.get("Score Step 1") + "ms");
		System.out.println("Score Step 2 Time: " + TimeStats.get("Score Step 2") + "ms");
		System.out.println("Neighbors Time: " + TimeStats.get("Neighbors") + "ms");
		return null;
	}
	
	private List<T> reconstructPath(Map<T, T> cameFrom, T current) {
		LinkedList<T> path = new LinkedList<>();
		path.add(current);
		while (cameFrom.containsKey(current)) {
			current = cameFrom.get(current);
			path.addFirst(current);
		}
		return path;
	}
	
	abstract float distance(T from, T to);

	abstract Set<T> neighbors(T current);

	abstract float heuristic(T from, T to);

}
