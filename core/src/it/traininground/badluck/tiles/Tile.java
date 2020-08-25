package it.traininground.badluck.tiles;

public class Tile implements Comparable<Tile> {

	private final TilesMap map;
	public final int layer;
	public final int row;
	public final int column;
	
	private TileType type;

	Tile(TilesMap map, int layer, int row, int column) {
		this.map = map;
		this.layer = layer;
		this.row = row;
		this.column = column;
	}

	public Tile toLayer(int layer) {
		return map.get(layer, this.row, this.column);
	}

	public Tile toRow(int row) {
		return map.get(this.layer, row, this.column);
	}

	public Tile toColumn(int column) {
		return map.get(this.layer, this.row, column);
	}
	
	public Tile addLayer(int offset) {
		return map.get(this.layer + offset, this.row, this.column);
	}
	
	public Tile addRow(int offset) {
		return map.get(this.layer, this.row + offset, this.column);
	}
	
	public Tile addColumn(int offset) {
		return map.get(this.layer, this.row, this.column + offset);
	}
	
	public Tile add(int layer, int row, int column) {
		return map.get(this.layer + layer, this.row + row, this.column + column);
	}

	@Override
	public String toString() {
		return "{l:" + layer + " r:" + row + " c:" + column + "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + layer;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (column != other.column)
			return false;
		if (layer != other.layer)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	public boolean equals(int layer, int row, int column) {
		return this.layer == layer && this.row == row && this.column == column;
	}

	@Override
	public int compareTo(Tile t) {
		return layer != t.layer ? t.layer - layer : (t.row + t.column) - (row + column);
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}

}
