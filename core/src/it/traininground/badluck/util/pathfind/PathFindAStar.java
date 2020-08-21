package it.traininground.badluck.util.pathfind;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import it.traininground.badluck.util.InfoDrawer;
import it.traininground.badluck.util.statistics.TimeStats;


public abstract class PathFindAStar<T> implements PathFind<T> {
	
	public List<T> findPath(T from, T to) {
		TimeStats.start("findPath");
		TreeMap<Float, LinkedList<T>> openSet = new TreeMap<>();
		openSet.put(0f, new LinkedList<T>(Arrays.asList(from)));
		
		HashSet<T> closeSet = new HashSet<>();
		
		Map<T, T> cameFrom = new HashMap<>();
		
		Map<T, Float> gScore = new HashMap<>();
		gScore.put(from, 0f);
		
		Map<T, Float> fScore = new HashMap<>();
		gScore.put(from, heuristic(from, to));
		
		while (!openSet.isEmpty()) {
			LinkedList<T> lowestF = openSet.firstEntry().getValue();
			T current = lowestF.getFirst();
			if (current.equals(to)) {
				InfoDrawer.put("find path", TimeStats.stopGetAndReset("findPath"));
				return reconstructPath(cameFrom, current);
			}
			lowestF.removeFirst();
			if (lowestF.isEmpty()) {
				openSet.remove(openSet.firstKey());
			}
			Set<T> neighbors = neighbors(current); 
			for (T neighbor : neighbors) {
				if (closeSet.contains(neighbor)) {
					continue;
				}
				float currentG = gScore.get(current) + distance(current, neighbor);
				boolean flag = currentG < gScore.getOrDefault(neighbor, Float.MAX_VALUE); 
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
			}
			closeSet.add(current);
		}
		InfoDrawer.put("find path", TimeStats.stopGetAndReset("findPath"));
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
