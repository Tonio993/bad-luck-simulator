package it.traininground.badluck.tiles;

import com.badlogic.gdx.utils.Array;

public class IsoMap {

    private static final TerrainType DEFAULT_TILE = TerrainType.PLAIN;

    enum TerrainType { PLAIN, DOWN_NORTH, DOWN_WEST, DOWN_SOUTH, DOWN_EAST }

    Array<Array<TerrainType>> mapMatrix;

    public IsoMap(int rows, int columns) {
        mapMatrix = new Array<>(rows);
        for (int r = 0; r < rows; r++) {
            Array<TerrainType> mapRow = new Array<>(columns);
            for (int c = 0; c < columns; c++) {
                mapRow.add(DEFAULT_TILE);
            }
        }
    }

    public TerrainType getTile(int row, int column) {
        return mapMatrix.get(row).get(column);
    }

    public void setTile(TerrainType terrainType, int row, int column) {
        mapMatrix.get(row).set(column, terrainType);
    }

}
