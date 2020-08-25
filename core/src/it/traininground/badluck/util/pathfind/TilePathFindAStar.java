package it.traininground.badluck.util.pathfind;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.math.Vector3;

import it.traininground.badluck.tiles.Tile;
import it.traininground.badluck.tiles.TileType;
import it.traininground.badluck.tiles.TilesMap;

public class TilePathFindAStar extends PathFindAStar<Tile> {
	
	private TilesMap map;

	public TilePathFindAStar(TilesMap map) {
		this.map = map;
	}

	@Override
	float distance(Tile from, Tile to) {
		return Vector3.len(from.layer - to.layer, from.row - to.row, from.column - to.column);
	}

	@Override
	public Set<Tile> neighbors(Tile current) {
		Set<Tile> neighbors = new HashSet<>();
		boolean firstRow = current.row == 0;
		boolean lastRow = current.row == map.getRows() - 1;
		boolean firstColumn = current.column == 0;
		boolean lastColumn = current.column == map.getColumns() - 1;
		int minLayer = Math.max(1, current.layer - 1);
		int maxLayer = Math.min(map.getLayers() - 1, current.layer + 1);
		Tile currentTile = null;
		for (int layer = minLayer; layer <= maxLayer; layer++) {
			currentTile = current.toLayer(layer);
			if (layer > current.layer && currentTile.getType() != TileType.EMPTY) {
				continue;
			}
			boolean north = false, west = false, south = false, east = false;
			if (!firstRow) {
				currentTile = map.get(layer, current.row - 1, current.column);
				if (isAccessible(currentTile)) {
					neighbors.add(currentTile);
				}
				if (currentTile.getType() == TileType.EMPTY) {
					west = true;
				}
			}
			if (!lastRow) {
				currentTile = map.get(layer, current.row + 1, current.column);
				if (isAccessible(currentTile)) {
					neighbors.add(currentTile);
				}
				if (currentTile.getType() == TileType.EMPTY) {
					east = true;
				}
			}
			if (!firstColumn) {
				currentTile = map.get(layer, current.row, current.column - 1);
				if (isAccessible(currentTile)) {
					neighbors.add(currentTile);
				}
				if (currentTile.getType() == TileType.EMPTY) {
					north = true;
				}
			}
			if (!lastColumn) {
				currentTile = map.get(layer, current.row, current.column + 1);
				if (isAccessible(currentTile)) {
					neighbors.add(currentTile);
				}
				if (currentTile.getType() == TileType.EMPTY) {
					south = true;
				}
			}
			if (north && west) {
				currentTile = map.get(layer, current.row - 1, current.column - 1);
				if (isAccessible(currentTile)) {
					neighbors.add(currentTile);
				}
			}
			if (south && west) {
				currentTile = map.get(layer, current.row - 1, current.column + 1);
				if (isAccessible(currentTile)) {
					neighbors.add(currentTile);
				}				
			}
			if (south && east) {
				currentTile = map.get(layer, current.row + 1, current.column + 1);
				if (isAccessible(currentTile)) {
					neighbors.add(currentTile);
				}
			}
			if (north && east) {
				currentTile = map.get(layer, current.row + 1, current.column - 1);
				if (isAccessible(currentTile)) {
					neighbors.add(currentTile);
				}
			}
		}
		return neighbors;
	}
	
	private boolean isAccessible(Tile tile) {
		return tile.getType() == TileType.EMPTY && tile.toLayer(tile.layer - 1).getType() != TileType.EMPTY;
	}

	@Override
	float heuristic(Tile from, Tile to) {
		return distance(from, to);
	}

}
