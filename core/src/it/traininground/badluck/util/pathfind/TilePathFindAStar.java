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
					if (map.tile(layer, row, column) == TileType.EMPTY && map.tile(layer - 1, row, column) != TileType.EMPTY) {
						neighbors.add(new Tile(layer, row, column));
					}
				}
			}
		}
		return neighbors;
	}

	@Override
	float heuristic(Tile from, Tile to) {
		return distance(from, to);
	}

}
