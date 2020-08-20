package it.traininground.badluck.util.pathfind;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class PathFindAStar<T> implements PathFind<T> {
	
	public List<T> findPath(T from, T to) {
		long start = System.currentTimeMillis();
		HashMap<T, Float> openSet = new HashMap<>();
		openSet.put(from, 0f);
		
		Map<T, T> cameFrom = new HashMap<>();
		
		Map<T, Float> gScore = new HashMap<>();
		gScore.put(from, 0f);
		
		Map<T, Float> fScore = new HashMap<>();
		gScore.put(from, heuristic(from, to));
		
		long getLowestFTime = 0;
		while (!openSet.isEmpty()) {
			long currTime = System.currentTimeMillis();
			T current = openSet.entrySet().stream().min((entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue())).get().getKey();
			getLowestFTime += System.currentTimeMillis() - currTime;
			if (current.equals(to)) {
				System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");
				System.out.println("Lowest F Time: " + getLowestFTime + "ms");
				return reconstructPath(cameFrom, current);
			}
			openSet.remove(current);
			for (T neighbor : neighbors(current)) {
				float currentG = gScore.get(current) + distance(current, neighbor);
				if (!gScore.containsKey(neighbor) || currentG < gScore.get(neighbor)) {
					cameFrom.put(neighbor, current);
					gScore.put(neighbor, currentG);
					fScore.put(neighbor, currentG + heuristic(neighbor, to));
					openSet.put(neighbor, fScore.get(neighbor));
				}
			}
		}
		System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");
		System.out.println("Lowest F Time: " + getLowestFTime + "ms");
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
