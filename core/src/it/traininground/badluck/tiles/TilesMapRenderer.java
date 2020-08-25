package it.traininground.badluck.tiles;

public abstract class TilesMapRenderer {
	
	protected MapManager map;

    protected TileDrawerManager tileDrawers;

    protected int x;
    protected int y;
    protected int cellWidth;
    protected int cellHeight;
    protected int layerHeight;

    public TilesMapRenderer(MapManager map, int cellWidth, int cellHeight, int layerHeight) {
    	this.map = map;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.layerHeight = layerHeight;

        tileDrawers = new TileDrawerManager(map);
    }

	public TileDrawerManager getTileDrawers() {
		return tileDrawers;
	}

	public void setTileDrawers(TileDrawerManager tileDrawers) {
		this.tileDrawers = tileDrawers;
	}

	public MapManager getMap() {
		return map;
	}

	public void setMap(MapManager map) {
		this.map = map;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCellWidth() {
		return cellWidth;
	}

	public void setCellWidth(int cellWidth) {
		this.cellWidth = cellWidth;
	}

	public int getCellHeight() {
		return cellHeight;
	}

	public void setCellHeight(int cellHeight) {
		this.cellHeight = cellHeight;
	}

	public int getLayerHeight() {
		return layerHeight;
	}

	public void setLayerHeight(int layerHeight) {
		this.layerHeight = layerHeight;
	}
	
}
