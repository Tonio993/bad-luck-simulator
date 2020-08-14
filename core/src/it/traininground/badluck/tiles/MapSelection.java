package it.traininground.badluck.tiles;

import it.traininground.badluck.util.LinkedHashMultiSet;

public class MapSelection {
	
	protected LinkedHashMultiSet<Integer> active;
	
	public MapSelection() {
		this.active = new LinkedHashMultiSet<Integer>();
	}

	public void clearActive() {
		active.clear();
	}

	public void addActive(TilePosition tile) {
		active.addSequence(tile.layer, tile.row, tile.column);
	}
	
	public boolean isActive(TilePosition tile) {
		return active.containsSequence(tile.layer, tile.row, tile.column);
	}
	
	public boolean isActive(int layer, int row, int column) {
		return active.containsSequence(layer, row, column);
	}

}
