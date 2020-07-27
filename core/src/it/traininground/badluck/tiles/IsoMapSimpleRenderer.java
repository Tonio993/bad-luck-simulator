package it.traininground.badluck.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

public class IsoMapSimpleRenderer extends IsoMapRenderer {

    private Map<TerrainType, TextureAtlas.AtlasRegion> terrainMap;

    public IsoMapSimpleRenderer(int cellWidth, int cellHeight, int layerHeight, IsoMap isoMap) {
        super(cellWidth, cellHeight, layerHeight, isoMap);

        TextureAtlas terrain = new TextureAtlas("terrain/terrain.atlas");
        terrainMap = new HashMap<>();
        terrainMap.put(TerrainType.PLAIN, terrain.findRegion("terrain_P"));
        terrainMap.put(TerrainType.DOWN_NORTH, terrain.findRegion("terrain_DN"));
        terrainMap.put(TerrainType.DOWN_NORTH_WEST, terrain.findRegion("terrain_DNW"));
        terrainMap.put(TerrainType.DOWN_WEST, terrain.findRegion("terrain_DW"));
        terrainMap.put(TerrainType.DOWN_SOUTH_WEST, terrain.findRegion("terrain_DSW"));
        terrainMap.put(TerrainType.DOWN_SOUTH, terrain.findRegion("terrain_DS"));
        terrainMap.put(TerrainType.DOWN_SOUTH_EAST, terrain.findRegion("terrain_DSE"));
        terrainMap.put(TerrainType.DOWN_EAST, terrain.findRegion("terrain_DE"));
        terrainMap.put(TerrainType.DOWN_NORTH_EAST, terrain.findRegion("terrain_DNE"));

    }

    @Override
    public void draw(SpriteBatch batch) {
        for (int l = 0; l < isoMap.getLayers(); l++) {
            for (int r = 0; r < isoMap.getRows(); r++) {
                for (int c = 0; c < isoMap.getColumns(); c++) {
                    TerrainType terrainType = isoMap.getTile(l, r, c);
                    if (terrainType != TerrainType.EMPTY) {
                        batch.draw(terrainMap.get(terrainType), x + ((r-c) * (cellWidth/2f)), y + (l * layerHeight) - ((r+c) * (cellHeight/2f)));
                    }
                }
            }
        }
    }
}
