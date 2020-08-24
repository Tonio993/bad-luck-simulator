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
		for (int layer = minLayer; layer <= maxLayer; layer++) {
			if (layer > current.getLayer() && map.tile(layer, current.getRow(), current.getColumn()) != TileType.EMPTY) {
				continue;
			}
			boolean north = false, west = false, south = false, east = false;
			if (!firstRow && isAccessible(layer, current.getRow() - 1, current.getColumn())) {
				neighbors.add(new Tile(layer, current.getRow() - 1, current.getColumn()));
				west = true;
			}
			if (!lastRow && isAccessible(layer, current.getRow() + 1, current.getColumn())) {
				neighbors.add(new Tile(layer, current.getRow() + 1, current.getColumn()));
				east = true;
			}
			if (!firstColumn && isAccessible(layer, current.getRow(), current.getColumn() - 1)) {
				neighbors.add(new Tile(layer, current.getRow(), current.getColumn() - 1));
				north = true;
			}
			if (!lastColumn && isAccessible(layer, current.getRow(), current.getColumn() + 1)) {
				neighbors.add(new Tile(layer, current.getRow(), current.getColumn() + 1));
				south = true;
			}
			if (north && west && isAccessible(layer, current.getRow() - 1, current.getColumn() - 1)) {
				neighbors.add(new Tile(layer, current.getRow() - 1, current.getColumn() - 1));
			}
			if (south && west && isAccessible(layer, current.getRow() - 1, current.getColumn() + 1)) {
				neighbors.add(new Tile(layer, current.getRow() - 1, current.getColumn() + 1));
			}
			if (south && east && isAccessible(layer, current.getRow() + 1, current.getColumn() + 1)) {
				neighbors.add(new Tile(layer, current.getRow() + 1, current.getColumn() + 1));
			}
			if (north && east && isAccessible(layer, current.getRow() + 1, current.getColumn() - 1)) {
				neighbors.add(new Tile(layer, current.getRow() + 1, current.getColumn() - 1));
			}
		}
		return neighbors;
	}
	
	private boolean isAccessible(int layer, int row, int column) {
		return map.tile(layer, row, column) == TileType.EMPTY && map.tile(layer - 1, row, column) != TileType.EMPTY;
	}

	@Override
	float heuristic(Tile from, Tile to) {
		return distance(from, to);
	}

}
