package it.traininground.badluck.tiles;

public class TilesMapBuilder {

    private static final TileType DEFAULT_GROUND_TILE = TileType.PLAIN;
    private static final TileType DEFAULT_EMPTY_TILE = TileType.EMPTY;

    private int layers;
    private int rows;
    private int columns;
    private int baseLayer;

    private TileType defaultGroundTile;
    private TileType defaultEmptyTile;

    public TilesMapBuilder(int layers, int rows, int columns) {
        this.defaultGroundTile = DEFAULT_GROUND_TILE;
        this.defaultEmptyTile = DEFAULT_EMPTY_TILE;
        setLayers(layers);
        setRows(rows);
        setColumns(columns);
        baseLayer = -1;
    }

    public int getLayers() {
        return layers;
    }

    public TilesMapBuilder setLayers(int layers) {
        this.layers = layers;
        return this;
    }

    public int getRows() {
        return rows;
    }

    public TilesMapBuilder setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public int getColumns() {
        return columns;
    }

    public TilesMapBuilder setColumns(int columns) {
        this.columns = columns;
        return this;
    }

    public int getBaseLayer() {
        return baseLayer;
    }

    public TilesMapBuilder setBaseLayer(int baseLayer) {
        this.baseLayer = baseLayer;
        return this;
    }

    public TileType getDefaultGroundTile() {
        return defaultGroundTile;
    }

    public TilesMapBuilder setDefaultGroundTile(TileType defaultGroundTile) {
        this.defaultGroundTile = defaultGroundTile;
        return this;
    }

    public TileType getDefaultEmptyTile() {
        return defaultEmptyTile;
    }

    public TilesMapBuilder setDefaultEmptyTile(TileType defaultEmptyTile) {
        this.defaultEmptyTile = defaultEmptyTile;
        return this;
    }

    public TilesMap build() {
        return new TilesMap(this);
    }
}
