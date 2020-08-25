package it.traininground.badluck.tiles;

import com.badlogic.gdx.utils.Array;

public class TilesMap {

    private static final TileType DEFAULT_TILE = TileType.PLAIN;
    
    private MapManager map;

    private Array<Array<Array<Tile>>> mapMatrix;
    protected int layers;
    protected int rows;
    protected int columns;

    TilesMap(TilesMapBuilder builder) {
        this.layers = builder.getLayers();
        this.rows = builder.getRows();
        this.columns = builder.getColumns();
        mapMatrix = new Array<>(layers);
        TileType currentType = builder.getDefaultGroundTile();
        for (int l = 0; l < layers; l++) {
            if (builder.getBaseLayer() != -1 && l == builder.getBaseLayer() + 1) {
                currentType = builder.getDefaultEmptyTile();
            }
            Array<Array<Tile>> mapLayer = new Array<>(rows);
            for (int r = 0; r < rows; r++) {
                Array<Tile> mapRow = new Array<>(columns);
                for (int c = 0; c < columns; c++) {
                	Tile tile = new Tile(this, l, r, c);
                	tile.setType(currentType);
                    mapRow.add(tile);
                }
                mapLayer.add(mapRow);
            }
            mapMatrix.add(mapLayer);
        }

    }

    public TilesMap(MapManager map, int layers, int rows, int columns) {
        this(map, layers, rows, columns, DEFAULT_TILE);
    }

    public TilesMap(MapManager map, int layers, int rows, int columns, TileType defaultTile) {
    	this.map = map;
        this.layers = layers;
        this.rows = rows;
        this.columns = columns;
        mapMatrix = new Array<>(layers);
        for (int l = 0; l < layers; l++) {
            Array<Array<Tile>> mapLayer = new Array<>(rows);
            for (int r = 0; r < rows; r++) {
                Array<Tile> mapRow = new Array<>(columns);
                for (int c = 0; c < columns; c++) {
                    mapRow.add(new Tile(this, l, r, c));
                }
                mapLayer.add(mapRow);
            }
            mapMatrix.add(mapLayer);
        }
    }

    public Tile get(int layer, int row, int column) {
        return mapMatrix.get(layer).get(row).get(column);
    }
    
	public MapManager getMap() {
		return map;
	}

	public void setMap(MapManager map) {
		this.map = map;
	}

	public int getLayers() {
		return layers;
	}

	public void setLayers(int layers) {
		this.layers = layers;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
}
