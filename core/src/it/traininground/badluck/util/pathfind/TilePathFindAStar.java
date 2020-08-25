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
		return Vector3.len(from.getLayer() - to.getLayer(), from.getRow() - to.getRow(), from.getColumn() - to.getColumn());
	}

	@Override
	public Set<Tile> neighbors(Tile current) {
		Set<Tile> neighbors = new HashSet<>();
		boolean firstRow = current.getRow() == 0;
		boolean lastRow = current.getRow() == map.getRows() - 1;
		boolean firstColumn = current.getColumn() == 0;
		boolean lastColumn = current.getColumn() == map.getColumns() - 1;
		int minLayer = Math.max(1, current.getLayer() - 1);
		int maxLayer = Math.min(map.getLayers() - 1, current.getLayer() + 1);
		Tile currentTile = new Tile(0, 0, 0);
		for (int layer = minLayer; layer <= maxLayer; layer++) {
			currentTile.set(layer, current.getRow(), current.getColumn());
			if (layer > current.getLayer() && map.tile(currentTile) != TileType.EMPTY) {
				continue;
			}
			boolean north = false, west = false, south = false, east = false;
			currentTile.set(layer, current.getRow() - 1, current.getColumn());
			if (!firstRow) {
				if (isAccessible(currentTile)) {
					neighbors.add(new Tile(currentTile));
				}
				if (map.tile(currentTile) == TileType.EMPTY) {
					west = true;
				}
			}
			currentTile.set(layer, current.getRow() + 1, current.getColumn());
			if (!lastRow) {
				if (isAccessible(currentTile)) {
					neighbors.add(new Tile(currentTile));
				}
				if (map.tile(currentTile) == TileType.EMPTY) {
					east = true;
				}
			}
			currentTile.set(layer, current.getRow(), current.getColumn() - 1);
			if (!firstColumn) {
				if (isAccessible(currentTile)) {
					neighbors.add(new Tile(currentTile));
				}
				if (map.tile(currentTile) == TileType.EMPTY) {
					north = true;
				}
			}
			currentTile.set(layer, current.getRow(), current.getColumn() + 1);
			if (!lastColumn) {
				if (isAccessible(currentTile)) {
					neighbors.add(new Tile(currentTile));
				}
				if (map.tile(currentTile) == TileType.EMPTY) {
					south = true;
				}
			}
			currentTile.set(layer, current.getRow() - 1, current.getColumn() - 1);
			if (north && west && isAccessible(currentTile)) {
				neighbors.add(new Tile(currentTile));
			}
			currentTile.set(layer, current.getRow() - 1, current.getColumn() + 1);
			if (south && west && isAccessible(currentTile)) {
				neighbors.add(new Tile(currentTile));
			}
			currentTile.set(layer, current.getRow() + 1, current.getColumn() + 1);
			if (south && east && isAccessible(currentTile)) {
				neighbors.add(new Tile(currentTile));
			}
			currentTile.set(layer, current.getRow() + 1, current.getColumn() - 1);
			if (north && east && isAccessible(currentTile)) {
				neighbors.add(new Tile(currentTile));
			}
		}
		return neighbors;
	}
	
	private boolean isAccessible(Tile tile) {
		return map.tile(tile.getLayer(), tile.getRow(), tile.getColumn()) == TileType.EMPTY && map.tile(tile.getLayer() - 1, tile.getRow(), tile.getColumn()) != TileType.EMPTY;
	}

	@Override
	float heuristic(Tile from, Tile to) {
		return distance(from, to);
	}

}
