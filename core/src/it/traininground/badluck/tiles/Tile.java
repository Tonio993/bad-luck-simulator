package it.traininground.badluck.tiles;

public class Tile implements Comparable<Tile> {

	private int layer;
	private int row;
	private int column;

	public Tile() {
		this(-1, -1, -1);
	}
	
	public Tile(int layer, int row, int column) {
		super();
		this.layer = layer;
		this.row = row;
		this.column = column;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public void set(int layer, int row, int column) {
		this.layer = layer;
		this.row = row;
		this.column = column;
	}

	public void unset() {
		this.layer = this.row = this.column = -1;
	}
	
	public Tile add(Tile tile) {
		return new Tile(this.layer + tile.layer, this.row + tile.row, this.column + tile.column);
	}
	
	public Tile add(int layer, int row, int column) {
		return new Tile(this.layer + layer, this.row + row, this.column + column);
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

	@Override
	public int compareTo(Tile t) {
		return layer != t.layer ? t.layer - layer : (t.row + t.column) - (row + column);
	}

}
