package it.traininground.badluck.tiles;

import com.badlogic.gdx.utils.Array;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class TilesMap implements Serializable {

    private static final TerrainType DEFAULT_TILE = TerrainType.PLAIN;

    private Array<Array<Array<TerrainType>>> mapMatrix;
    protected int layers;
    protected int rows;
    protected int columns;

    TilesMap(TilesMapBuilder builder) {
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

    public TerrainType tile(int layer, int row, int column) {
        return mapMatrix.get(layer).get(row).get(column);
    }

    public void tile(int layer, int row, int column, TerrainType terrainType) {
        mapMatrix.get(layer).get(row).set(column, terrainType);
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeInt(layers);
        stream.writeInt(rows);
        stream.writeInt(columns);
        List<TerrainType> terrainTypes = Arrays.asList(TerrainType.values());
        for (Array<Array<TerrainType>> layer : mapMatrix) {
            for (Array<TerrainType> row : layer) {
                for (TerrainType tile : row) {
                    stream.writeInt(terrainTypes.indexOf(tile));
                }
            }
        }
    }

    private void readObject(ObjectInputStream stream) throws IOException {
        layers = stream.readInt();
        rows = stream.readInt();
        columns = stream.readInt();
        mapMatrix = new Array<>(layers);
        mapMatrix = new Array<>(layers);
        List<TerrainType> terrainTypes = Arrays.asList(TerrainType.values());
        for (int l = 0; l < layers; l++) {
            Array<Array<TerrainType>> mapLayer = new Array<>(rows);
            for (int r = 0; r < rows; r++) {
                Array<TerrainType> mapRow = new Array<>(columns);
                for (int c = 0; c < columns; c++) {
                    mapRow.add(terrainTypes.get(stream.readInt()));
                }
                mapLayer.add(mapRow);
            }
            mapMatrix.add(mapLayer);
        }
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
