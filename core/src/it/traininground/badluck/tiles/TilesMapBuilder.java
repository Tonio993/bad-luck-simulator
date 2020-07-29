package it.traininground.badluck.tiles;

public class TilesMapBuilder {

    private static final TerrainType DEFAULT_GROUND_TILE = TerrainType.PLAIN;
    private static final TerrainType DEFAULT_EMPTY_TILE = TerrainType.EMPTY;

    private int layers;
    private int rows;
    private int columns;
    private int baseLayer;

    private TerrainType defaultGroundTile;
    private TerrainType defaultEmptyTile;

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

    public TerrainType getDefaultGroundTile() {
        return defaultGroundTile;
    }

    public TilesMapBuilder setDefaultGroundTile(TerrainType defaultGroundTile) {
        this.defaultGroundTile = defaultGroundTile;
        return this;
    }

    public TerrainType getDefaultEmptyTile() {
        return defaultEmptyTile;
    }

    public TilesMapBuilder setDefaultEmptyTile(TerrainType defaultEmptyTile) {
        this.defaultEmptyTile = defaultEmptyTile;
        return this;
    }

    public TilesMap build() {
        return new TilesMap(this);
    }
}
