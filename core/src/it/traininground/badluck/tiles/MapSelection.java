package it.traininground.badluck.tiles;

import it.traininground.badluck.util.LinkedHashMultiSet;

public class MapSelection {
	
	protected MapManager map;
	
	protected Tile hover;
	protected LinkedHashMultiSet<Integer> active;
	
	public MapSelection(MapManager map) {
		this.map = map;
		this.active = new LinkedHashMultiSet<Integer>();
	}
	
	public Tile getHover() {
		return hover;
	}
	
	public void setHover(int layer, int row, int column) {
		hover = map.getTiles().get(layer, row, column);
	}
	
	public void unsetHover() {
		hover = null;
	}
	
	public boolean isHover() {
		return hover != null;
	}

	public boolean isHover(Tile tile) {
		return hover == tile;
	}
	
	public boolean isHover(int layer, int row, int column) {
		return hover != null && hover.layer == layer && hover.row == row && hover.column == column;
	}
	
	public void clearActive() {
		active.clear();
	}

	public void addActive(Tile tile) {
		active.addSequence(tile.layer, tile.row, tile.column);
	}
	
	public boolean isActive(Tile tile) {
		return active.containsSequence(tile.layer, tile.row, tile.column);
	}
	
	public boolean isActive(int layer, int row, int column) {
		return active.containsSequence(layer, row, column);
	}

}
