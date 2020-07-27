package it.traininground.badluck.tiles;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.Map;

public class IsoMapTotalRenderer extends IsoMapRenderer {

    private Texture textureResult;

    public IsoMapTotalRenderer(int cellWidth, int cellHeight, int layerHeight, IsoMap isoMap) {
        super(cellWidth, cellHeight, layerHeight, isoMap);

        Map<TerrainType, Texture> terrainMap = new HashMap<>();
        terrainMap.put(TerrainType.PLAIN, new Texture("terrain/terrain_P.png"));
        terrainMap.put(TerrainType.DOWN_NORTH, new Texture("terrain/terrain_DN.png"));
        terrainMap.put(TerrainType.DOWN_NORTH_WEST, new Texture("terrain/terrain_DNW.png"));
        terrainMap.put(TerrainType.DOWN_WEST, new Texture("terrain/terrain_DW.png"));
        terrainMap.put(TerrainType.DOWN_SOUTH_WEST, new Texture("terrain/terrain_DSW.png"));
        terrainMap.put(TerrainType.DOWN_SOUTH, new Texture("terrain/terrain_DS.png"));
        terrainMap.put(TerrainType.DOWN_SOUTH_EAST, new Texture("terrain/terrain_DSE.png"));
        terrainMap.put(TerrainType.DOWN_EAST, new Texture("terrain/terrain_DE.png"));
        terrainMap.put(TerrainType.DOWN_NORTH_EAST, new Texture("terrain/terrain_DNE.png"));

        Map<TerrainType, Pixmap> pixmapMap = new HashMap<>();
        for (Map.Entry<TerrainType, Texture> entry : terrainMap.entrySet()) {
            entry.getValue().getTextureData().prepare();
            pixmapMap.put(entry.getKey(), entry.getValue().getTextureData().consumePixmap());
        }

        int gridSizeFactor = isoMap.getRows() + isoMap.getColumns();

        Pixmap pixmap = new Pixmap(gridSizeFactor * cellWidth/2, (gridSizeFactor + 1) * cellHeight/2 + layerHeight * isoMap.getLayers() , Pixmap.Format.RGBA8888);

        for (int l = 0; l < isoMap.getLayers(); l++) {
            for (int r = 0; r < isoMap.getRows(); r++) {
                for (int c = 0; c < isoMap.getColumns(); c++) {
                    TerrainType terrainType = isoMap.getTile(l, r, c);
                    if (terrainType != TerrainType.EMPTY) {
                        pixmap.drawPixmap(pixmapMap.get(terrainType), x + ((r-c + (gridSizeFactor - 1) / 2) * (cellWidth/2)), y + (isoMap.getLayers() - l) * layerHeight + ((r+c-1) * (cellHeight/2)));
                    }
                }
            }
        }

        textureResult = new Texture(pixmap);

        pixmap.dispose();
        for (Pixmap pixmapToDispose : pixmapMap.values()) {
            pixmapToDispose.dispose();
        }

    }

    public void draw(SpriteBatch batch) {
        batch.draw(textureResult, x, y);
    }
}
