package it.traininground.badluck.tiles;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import it.traininground.badluck.tiles.debug.CubeShape;
import it.traininground.badluck.util.GameBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class TilesMapRendererImpl extends TilesMapRenderer {

    private Map<TileType, TextureAtlas.AtlasRegion> terrainMap;

//    private boolean highlightedTile;
//    private int highlightedLayer = -1;
//    private int highlightedRow = -1;
//    private int highlightedColumn = -1;

    private CubeShape debugShape;

    AtomicInteger drawnTiles = new AtomicInteger();

    public TilesMapRendererImpl(int cellWidth, int cellHeight, int layerHeight) {
        super(cellWidth, cellHeight, layerHeight);

        debugShape = new CubeShape(cellWidth, cellHeight, layerHeight);

        TextureAtlas terrain = new TextureAtlas("terrain/terrain.atlas");
        terrainMap = new HashMap<>();
        terrainMap.put(TileType.PLAIN, terrain.findRegion("terrain_P"));
        terrainMap.put(TileType.DOWN_NORTH, terrain.findRegion("terrain_DN"));
        terrainMap.put(TileType.DOWN_NORTH_WEST, terrain.findRegion("terrain_DNW"));
        terrainMap.put(TileType.DOWN_WEST, terrain.findRegion("terrain_DW"));
        terrainMap.put(TileType.DOWN_SOUTH_WEST, terrain.findRegion("terrain_DSW"));
        terrainMap.put(TileType.DOWN_SOUTH, terrain.findRegion("terrain_DS"));
        terrainMap.put(TileType.DOWN_SOUTH_EAST, terrain.findRegion("terrain_DSE"));
        terrainMap.put(TileType.DOWN_EAST, terrain.findRegion("terrain_DE"));
        terrainMap.put(TileType.DOWN_NORTH_EAST, terrain.findRegion("terrain_DNE"));

        tileDrawerSet.add((map, layer, row, column) -> {
            if (map.isDebugMode()) return;
            TileType terrainType = map.tiles.tile(layer, row, column);
            if (terrainType != TileType.EMPTY) {
                float currentOffsetX = x + (row - column - 1) * cellWidth / 2f;
                float currentOffsetY = y - (row + column + 2) * cellHeight / 2f + layerHeight * (layer - 1);
                GameBatch batch = map.scene.getGame().getBatch();
				batch.draw(terrainMap.get(terrainType), currentOffsetX, currentOffsetY);
            }
        });

        tileDrawerSet.add((map, layer, row, column) -> {
            if (!map.isDebugMode()) return;
            TileType terrainType = map.tiles.tile(layer, row, column);
            if (terrainType != TileType.EMPTY) {
                float debugLineOffsetX = x + (row - column) * cellWidth / 2f;
                float debugLineOffsetY = y - (row + column) * cellHeight / 2f + layer * layerHeight;
                ShapeDrawer shapeDrawer = map.scene.getGame().getBatch().getShapeDrawer();
                shapeDrawer.setDefaultLineWidth(1);
                shapeDrawer.setColor(Color.BLACK);
                debugShape.draw(shapeDrawer, debugLineOffsetX, debugLineOffsetY);
            }
        });

        tileDrawerSet.add((map, layer, row, column) -> {
        	if (map.selection.isHover(layer, row, column)) {
        		float offsetX = x + (row - column) * (cellWidth/2f);
        		float offsetY = y - (row + column) * (cellHeight/2f) + layer * layerHeight;
        		ShapeDrawer shapeDrawer = map.scene.getGame().getBatch().getShapeDrawer();
        		shapeDrawer.setDefaultLineWidth(5);
        		shapeDrawer.setColor(Color.ORANGE);
        		shapeDrawer.polygon(new float[]{offsetX, offsetY, offsetX + cellWidth/2f, offsetY - cellHeight/2f, offsetX, offsetY - cellHeight, offsetX - cellWidth/2f, offsetY - cellHeight/2f});
//        		debugShape.draw(shapeDrawer, offsetX, offsetY);        		
            }
        });

        tileDrawerSet.add((map, layer, row, column) -> {
            if (map.tiles.tile(layer, row, column) != TileType.EMPTY) {
                drawnTiles.getAndIncrement();
            }
        });

        tileDrawerSet.add((map, layer, row, column) -> {
//            if (!map.isDebugMode()) return;
            ShapeDrawer shapeDrawer = map.scene.getGame().getBatch().getShapeDrawer();
            shapeDrawer.setDefaultLineWidth(1);
            shapeDrawer.setColor(Color.BLACK);
            shapeDrawer.line(x - 10, y, x + 10, y);
            shapeDrawer.line(x, y - 10, x, y + 10);
        });

    }

//    private void selectHighlightedTile(int screenX, int screenY) {
//        int currentLayer = map.region.visibleLayer;
//        highlightedTile = false;
//        while (currentLayer >= 0) {
//            highlightedLayer = currentLayer;
//            float currentLayerHeight = currentLayer * layerHeight;
//            //TODO ripristino variabili
////            Vector3 cameraPoint = scene.getMainCamera().unproject(new Vector3(screenX, screenY + currentLayerHeight, 0));
////            float gridProjectedY = (y - cameraPoint.y) / cellHeight;
////            float gridProjectedX = (x - cameraPoint.x) / cellWidth;
////            highlightedRow = (int) Math.floor(gridProjectedY - gridProjectedX);
////            highlightedColumn = (int) Math.floor(gridProjectedY + gridProjectedX);
////            if (highlightedRow >= 0 && highlightedRow < tilesMap.getRows() && highlightedColumn >= 0 && highlightedColumn < tilesMap.getColumns() && tilesMap.getTile(currentLayer, highlightedRow, highlightedColumn) != TerrainType.EMPTY) {
////                highlightedTile = true;
////                break;
////            }
//            currentLayer--;
//        }
//        if (!highlightedTile) {
//            highlightedLayer = highlightedRow = highlightedColumn = -1;
//        }
//    }
//
//    public boolean isHighlightedTile() {
//        return highlightedTile;
//    }
//
//    public int getHighlightedLayer() {
//        return highlightedLayer;
//    }
//
//    public int getHighlightedRow() {
//        return highlightedRow;
//    }
//
//    public int getHighlightedColumn() {
//        return highlightedColumn;
//    }
//
//    public Vector3 getHighLightedTile() {
//        if (!highlightedTile) {
//            return null;
//        }
//        return new Vector3(highlightedLayer, highlightedRow, highlightedColumn);
//    }

//    public void removeHighlightedTile() {
//        this.highlightedTile = false;
//        this.highlightedLayer = -1;
//        this.highlightedRow = -1;
//        this.highlightedColumn = -1;
//    }
//
//    public void setHighlightedTile(Vector3 tile) {
//        setHighlightedTile((int) Math.floor(tile.x), (int) Math.floor(tile.y), (int) Math.floor(tile.z));
//    }
//
//    public void setHighlightedTile(int layer, int row, int column) {
//        this.highlightedTile = true;
//        this.highlightedLayer = MathUtil.between(layer, 0, map.tiles.layers - 1);
//        this.highlightedRow = MathUtil.between(0, map.tiles.rows - 1, row);
//        this.highlightedColumn = MathUtil.between(0, map.tiles.columns - 1, column);
//    }

//    public final InputHandler inputHandler = new InputHandler() {
//        @Override
//        public void scrolled(int amount) {
//            if (!Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
//                map.region.visibleLayer = (Math.min(Math.max(0, map.region.visibleLayer - amount), map.tiles.layers - 1));
//                //TODO ripristino variabili
////                map.regionFilter.updateRegion(scene.getMainCamera().position);
//            }
//        }
//
//        @Override
//        public void touchDown(int screenX, int screenY, int pointer, int button) {
//            if (isHighlightedTile()) {
//                switch (button) {
//                    case Input.Buttons.LEFT:
//                        map.tiles.tile(highlightedLayer + 1, highlightedRow, highlightedColumn, TerrainType.PLAIN);
//                        break;
//                    case Input.Buttons.RIGHT:
//                        map.tiles.tile(highlightedLayer, highlightedRow, highlightedColumn, TerrainType.EMPTY);
//                        break;
//                }
//            }
//        }
//
//        @Override
//        public void mouseMoved(int screenX, int screenY) {
//            selectHighlightedTile(screenX, screenY);
//        }
//    };

}