package it.traininground.badluck.tiles;

import it.traininground.badluck.util.LinkedHashMultiSet;

public class MapSelection {
	
	protected Tile hover;
	protected LinkedHashMultiSet<Integer> active;
	
	public MapSelection() {
		this.hover = new Tile();
		this.active = new LinkedHashMultiSet<Integer>();
	}
	
	public Tile getHover() {
		return hover;
	}
	
	public void setHover(int layer, int row, int column) {
		hover.set(layer, row, column);
	}
	
	public void unsetHover() {
		hover.unset();
	}
	
	public boolean isHover() {
		return hover.getLayer() != -1;
	}

	public boolean isHover(Tile tile) {
		return hover.equals(tile);
	}
	
	public boolean isHover(int layer, int row, int column) {
		return hover.getLayer() == layer && hover.getRow() == row && hover.getColumn() == column;
	}
	
	public void clearActive() {
		active.clear();
	}

	public void addActive(Tile tile) {
		active.addSequence(tile.getLayer(), tile.getRow(), tile.getColumn());
	}
	
	public boolean isActive(Tile tile) {
		return active.containsSequence(tile.getLayer(), tile.getRow(), tile.getColumn());
	}
	
	public boolean isActive(int layer, int row, int column) {
		return active.containsSequence(layer, row, column);
	}

}
