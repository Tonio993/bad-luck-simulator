//package it.traininground.badluck.tiles;
//
//import com.badlogic.gdx.graphics.Pixmap;
//import com.badlogic.gdx.graphics.Texture;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import it.traininground.badluck.scenes.Scene;
//
//public class TilesMapSingleImageRenderer extends TilesMapRenderer {
//
//    private Texture textureResult;
//
//    public TilesMapSingleImageRenderer(Scene scene, TilesMap tilesMap, int cellWidth, int cellHeight, int layerHeight) {
//        super(scene, tilesMap, cellWidth, cellHeight, layerHeight);
//
//        Map<TerrainType, Texture> terrainMap = new HashMap<>();
//        terrainMap.put(TerrainType.PLAIN, new Texture("terrain/terrain_P.png"));
//        terrainMap.put(TerrainType.DOWN_NORTH, new Texture("terrain/terrain_DN.png"));
//        terrainMap.put(TerrainType.DOWN_NORTH_WEST, new Texture("terrain/terrain_DNW.png"));
//        terrainMap.put(TerrainType.DOWN_WEST, new Texture("terrain/terrain_DW.png"));
//        terrainMap.put(TerrainType.DOWN_SOUTH_WEST, new Texture("terrain/terrain_DSW.png"));
//        terrainMap.put(TerrainType.DOWN_SOUTH, new Texture("terrain/terrain_DS.png"));
//        terrainMap.put(TerrainType.DOWN_SOUTH_EAST, new Texture("terrain/terrain_DSE.png"));
//        terrainMap.put(TerrainType.DOWN_EAST, new Texture("terrain/terrain_DE.png"));
//        terrainMap.put(TerrainType.DOWN_NORTH_EAST, new Texture("terrain/terrain_DNE.png"));
//
//        Map<TerrainType, Pixmap> pixmapMap = new HashMap<>();
//        for (Map.Entry<TerrainType, Texture> entry : terrainMap.entrySet()) {
//            entry.getValue().getTextureData().prepare();
//            pixmapMap.put(entry.getKey(), entry.getValue().getTextureData().consumePixmap());
//        }
//
//        int gridSizeFactor = tilesMap.getRows() + tilesMap.getColumns();
//
//        Pixmap pixmap = new Pixmap(gridSizeFactor * cellWidth/2, (gridSizeFactor + 1) * cellHeight/2 + layerHeight * tilesMap.getLayers() , Pixmap.Format.RGBA8888);
//
//        for (int l = 0; l < tilesMap.getLayers(); l++) {
//            for (int r = 0; r < tilesMap.getRows(); r++) {
//                for (int c = 0; c < tilesMap.getColumns(); c++) {
//                    TerrainType terrainType = tilesMap.getTile(l, r, c);
//                    if (terrainType != TerrainType.EMPTY) {
//                        pixmap.drawPixmap(pixmapMap.get(terrainType), x + ((r-c + (gridSizeFactor - 1) / 2) * (cellWidth/2)), y + (tilesMap.getLayers() - l) * layerHeight + ((r+c-1) * (cellHeight/2)));
//                    }
//                }
//            }
//        }
//
//        textureResult = new Texture(pixmap);
//
//        pixmap.dispose();
//        for (Pixmap pixmapToDispose : pixmapMap.values()) {
//            pixmapToDispose.dispose();
//        }
//    }
//
//    public void draw() {
//        scene.getGame().getBatch().draw(textureResult, x, y);
//    }
//}
