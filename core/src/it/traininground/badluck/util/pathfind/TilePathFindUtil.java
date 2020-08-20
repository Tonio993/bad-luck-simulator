package it.traininground.badluck.util.pathfind;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.math.Vector3;

import it.traininground.badluck.tiles.Tile;
import it.traininground.badluck.tiles.TileType;
import it.traininground.badluck.tiles.TilesMap;

public class TilePathFindUtil {
	
	public static List<Tile> findPath(TilesMap map, Tile from, Tile to) {
		long start = System.currentTimeMillis();
		HashMap<Tile, Float> openSet = new HashMap<>();
		openSet.put(from, 0f);
		
		Map<Tile, Tile> cameFrom = new HashMap<>();
		
		Map<Tile, Float> gScore = new HashMap<>();
		gScore.put(from, 0f);
		
		Map<Tile, Float> fScore = new HashMap<>();
		gScore.put(from, heuristic(from, to));
		
		while (!openSet.isEmpty()) {
			Tile current = openSet.entrySet().stream().min((entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue())).get().getKey();
			if (current.equals(to)) {
				System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");
				return reconstructPath(cameFrom, current);
			}
			openSet.remove(current);
			for (Tile neighbor : neighbors(map, current)) {
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
		return null;
	}

	private static float distance(Tile current, Tile neighbor) {
		return Vector3.len(current.getLayer() - neighbor.getLayer(), current.getRow() - neighbor.getRow(), current.getColumn() - neighbor.getColumn());
	}

	private static Set<Tile> neighbors(TilesMap map, Tile current) {
		Set<Tile> neighbors = new HashSet<>();
		int minLayer = Math.max(1, current.getLayer() - 1);
		int maxLayer = Math.min(map.getLayers() - 1, current.getLayer() + 1);
		for (int layer = minLayer; layer <= maxLayer; layer++) {
			int minRow = Math.max(0, current.getRow() - 1);
			int maxRow = Math.min(map.getRows() - 1, current.getRow() + 1);
			for (int row = minRow; row <= maxRow; row++) {
				int minColumn = Math.max(0,  current.getColumn() - 1);
				int maxColumn = Math.min(map.getColumns() - 1, current.getColumn() + 1);
				for (int column = minColumn; column <= maxColumn; column++) {
					if (row == current.getRow() && column == current.getColumn()) {
						continue;
					}
					if (map.tile(layer, row, column) == TileType.EMPTY) {
						neighbors.add(new Tile(layer, row, column));
					}
				}
			}
		}
		return neighbors;
	}

	private static float heuristic(Tile from, Tile to) {
		return Vector3.len(from.getLayer() - to.getLayer(), from.getRow() - to.getRow(), from.getColumn() - to.getColumn());
	}

	private static List<Tile> reconstructPath(Map<Tile, Tile> cameFrom, Tile current) {
		LinkedList<Tile> path = new LinkedList<>();
		path.add(current);
	    while (cameFrom.containsKey(current)) {
	    	current = cameFrom.get(current);
	    	path.addFirst(current);
	    }
	    return path;
	}

}
