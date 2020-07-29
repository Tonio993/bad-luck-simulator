package it.traininground.badluck.tiles;

import com.badlogic.gdx.utils.Array;

public class TilesMap {

    private static final TerrainType DEFAULT_TILE = TerrainType.PLAIN;

    private final Array<Array<Array<TerrainType>>> mapMatrix;
    private final int layers;
    private final int rows;
    private final int columns;

    TilesMap(IsoMapBuilder builder) {
        this.layers = builder.getLayers();
        this.rows = builder.getRows();
        this.columns = builder.getColumns();
        mapMatrix = new Array<>(layers);
        TerrainType currentType = builder.getDefaultGroundTile();
        for (int l = 0; l < layers; l++) {
            if (builder.getBaseLayer() != -1 && l == builder.getBaseLayer() + 1) {
                currentType = builder.getDefaultEmptyTile();
            }
            Array<Array<TerrainType>> mapLayer = new Array<>(rows);
            for (int r = 0; r < rows; r++) {
                Array<TerrainType> mapRow = new Array<>(columns);
                for (int c = 0; c < columns; c++) {
                    mapRow.add(currentType);
                }
                mapLayer.add(mapRow);
            }
            mapMatrix.add(mapLayer);
        }
    }

    public TilesMap(int layers, int rows, int columns) {
        this(layers, rows, columns, DEFAULT_TILE);
    }

    public TilesMap(int layers, int rows, int columns, TerrainType defaultTile) {
        this.layers = layers;
        this.rows = rows;
        this.columns = columns;
        mapMatrix = new Array<>(layers);
        for (int l = 0; l < layers; l++) {
            Array<Array<TerrainType>> mapLayer = new Array<>(rows);
            for (int r = 0; r < rows; r++) {
                Array<TerrainType> mapRow = new Array<>(columns);
                for (int c = 0; c < columns; c++) {
                    mapRow.add(defaultTile);
                }
                mapLayer.add(mapRow);
            }
            mapMatrix.add(mapLayer);
        }
    }

    public TerrainType getTile(int layer, int row, int column) {
        return mapMatrix.get(layer).get(row).get(column);
    }

    public void setTile(int layer, int row, int column, TerrainType terrainType) {
        mapMatrix.get(layer).get(row).set(column, terrainType);
    }

    public int getLayers() {
        return layers;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
