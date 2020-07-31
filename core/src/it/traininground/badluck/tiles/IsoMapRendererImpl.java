package it.traininground.badluck.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import it.traininground.badluck.input.InputHandler;
import it.traininground.badluck.scenes.Scene;
import it.traininground.badluck.tiles.debug.DebugShape;
import it.traininground.badluck.util.GameInfo;
import it.traininground.badluck.util.InfoDrawer;
import it.traininground.badluck.util.MathUtil;
import it.traininground.badluck.util.ShapeDrawerUtil;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class IsoMapRendererImpl extends IsoMapRenderer {

    private Map<TerrainType, TextureAtlas.AtlasRegion> terrainMap;

    private ShapeDrawer shapeDrawer;

    private Set<TileDrawer> tileDrawerSet;

    private MapRegionSelector mapRegionSelector;

    private boolean debugMode;

    private int visibleLayerLevel;

    private boolean highlightedTile;
    private int highlightedLayer = -1;
    private int highlightedRow = -1;
    private int highlightedColumn = -1;

    private int lowerLayer;
    private int upperLayer;
    private int lowerTileX;
    private int lowerTileY;
    private int upperTileX;
    private int upperTileY;

    private DebugShape debugShape;

    public IsoMapRendererImpl(Scene scene, TilesMap tilesMap, int cellWidth, int cellHeight, int layerHeight) {
        super(scene, tilesMap, cellWidth, cellHeight, layerHeight);
        shapeDrawer = ShapeDrawerUtil.createShapeDrawer(scene.getGame().getBatch());
        this.visibleLayerLevel = tilesMap.getLayers() - 1;

        debugShape = new DebugShape(cellWidth, cellHeight, layerHeight);

        mapRegionSelector = new MapRegionSelector(this);

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

        upperLayer = tilesMap.getLayers();
        upperTileX = tilesMap.getColumns();
        upperTileY = tilesMap.getColumns();

    }

    private void selectHighlightedTile(int screenX, int screenY) {
        int currentLayer = getVisibleLayerLevel();
        highlightedTile = false;
        while (currentLayer >= 0) {
            highlightedLayer = currentLayer;
            float currentLayerHeight = currentLayer * layerHeight;
            Vector3 cameraPoint = scene.getMainCamera().unproject(new Vector3(screenX, screenY + currentLayerHeight, 0));
            float gridProjectedY = (y - cameraPoint.y) / cellHeight;
            float gridProjectedX = (x - cameraPoint.x) / cellWidth;
            highlightedRow = (int) Math.floor(gridProjectedY - gridProjectedX);
            highlightedColumn = (int) Math.floor(gridProjectedY + gridProjectedX);
            if (highlightedRow >= 0 && highlightedRow < tilesMap.getRows() && highlightedColumn >= 0 && highlightedColumn < tilesMap.getColumns() && tilesMap.getTile(currentLayer, highlightedRow, highlightedColumn) != TerrainType.EMPTY) {
                highlightedTile = true;
                break;
            }
            currentLayer--;
        }
        if (!highlightedTile) {
            highlightedLayer = highlightedRow = highlightedColumn = -1;
        }
    }

    @Override
    public void draw() {
        AtomicInteger drawnTiles = new AtomicInteger();
        Vector3 cameraPosition = scene.getMainCamera().position;
        handleMap(cameraPosition, (layer) -> {
            float currentLayerHeight = (layer - 1) * layerHeight;
            if (!debugMode) {
                handleLayer(cameraPosition, layer, (row, column) -> {
                    TerrainType terrainType = tilesMap.getTile(layer, row, column);
                    if (terrainType != TerrainType.EMPTY) {
                        float currentOffsetX = x + (row - column - 1) * cellWidth / 2f;
                        float currentOffsetY = y - (row + column + 2) * cellHeight / 2f + currentLayerHeight;
                        scene.getGame().getBatch().draw(terrainMap.get(terrainType), currentOffsetX, currentOffsetY);
                    }
                });
            } else {
                handleLayer(cameraPosition, layer, (row, column) -> {
                    TerrainType terrainType = tilesMap.getTile(layer, row, column);
                    if (terrainType != TerrainType.EMPTY) {
                        float debugLineOffsetX = x + (row - column) * cellWidth / 2f;
                        float debugLineOffsetY = y - (row + column) * cellHeight / 2f + layer * layerHeight;
                        shapeDrawer.setDefaultLineWidth(1);
                        shapeDrawer.setColor(Color.BLACK);
                        debugShape.draw(shapeDrawer, debugLineOffsetX, debugLineOffsetY);
                    }
                });
            }
            handleLayer(cameraPosition, layer, (row, column) -> {
                if (highlightedTile && highlightedLayer == layer) {
                    float offsetX = x + (highlightedRow - highlightedColumn) * (cellWidth/2f);
                    float offsetY = y - (highlightedRow + highlightedColumn) * (cellHeight/2f) + highlightedLayer * layerHeight;
                    shapeDrawer.setDefaultLineWidth(5);
                    shapeDrawer.setColor(Color.ORANGE);
                    shapeDrawer.polygon(new float[]{offsetX, offsetY, offsetX + cellWidth/2f, offsetY - cellHeight/2f, offsetX, offsetY - cellHeight, offsetX - cellWidth/2f, offsetY - cellHeight/2f});
                }
            });
            handleLayer(cameraPosition,layer, (row, column) -> drawnTiles.getAndIncrement());
        });
        InfoDrawer.put("drawn tiles", drawnTiles);
        mapRegionSelector.updateRegion(cameraPosition);
    }

    private void handleMap(Vector3 cameraPosition, MapHandler mapHandler) {
        int firstLayer = (int) Math.max(0, Math.ceil((cameraPosition.y - y - GameInfo.HEIGHT / 2f) / layerHeight));
        int lastLayer = (int) Math.min(visibleLayerLevel, Math.floor((cameraPosition.y - y + GameInfo.HEIGHT / 2f + cellHeight / 2f * (tilesMap.getRows() + tilesMap.getColumns())) / layerHeight + 1));
        for (int l = firstLayer; l <= lastLayer; l++) {
            mapHandler.handleMap(l);
        }
    }

    private void handleLayer(Vector3 cameraPosition, int layer, LayerHandler layerHandler) {
        for (int r = 0; r < tilesMap.getRows(); r++) {
            int firstCellX = (int) Math.max(0, -(tilesMap.getRows() - r - 1) + (x - cameraPosition.x - GameInfo.WIDTH / 2f + (cellWidth / 2f * (tilesMap.getRows() - 1))) / (cellWidth / 2f));
            int lastCellX = (int) Math.min(tilesMap.getColumns(), -(tilesMap.getRows() - r - 1) + (x - cameraPosition.x + GameInfo.WIDTH / 2f + (cellWidth / 2f * (tilesMap.getRows() + 1))) / (cellWidth / 2f));
            int firstCellY = (int) Math.max(0, -r + (y - cameraPosition.y - GameInfo.HEIGHT / 2f - (cellHeight / 2f) + (layer - 1) * layerHeight) / (layerHeight / 2f));
            int lastCellY = (int) Math.min(tilesMap.getColumns(), -r + (y - cameraPosition.y + GameInfo.HEIGHT / 2f + layer * layerHeight + cellHeight / 2f) / (layerHeight / 2f));
            for (int c = Math.max(firstCellX, firstCellY); c < Math.min(lastCellX, lastCellY); c++) {
                layerHandler.handleLayer(r, c);
            }
        }
    }

    public void updateMapDrawingBounds() {
        Vector3 cameraPosition = scene.getMainCamera().position;
        lowerLayer = (int) Math.max(0, Math.ceil((cameraPosition.y - y - GameInfo.HEIGHT / 2f) / layerHeight));
        upperLayer = (int) Math.min(visibleLayerLevel, Math.floor((cameraPosition.y - y + GameInfo.HEIGHT / 2f + cellHeight / 2f * (tilesMap.getRows() + tilesMap.getColumns())) / layerHeight + 1));
        lowerTileX = (int) Math.max(0, (x - cameraPosition.x - GameInfo.WIDTH / 2f + (cellWidth / 2f * (tilesMap.getRows() - 1))) / (cellWidth / 2f));
        upperTileX = (int) Math.min(tilesMap.getColumns(), (x - cameraPosition.x + GameInfo.WIDTH / 2f + (cellWidth / 2f * (tilesMap.getRows() + 1))) / (cellWidth / 2f));
        lowerTileY = (int) Math.max(0, (y - cameraPosition.y - GameInfo.HEIGHT / 2f - (cellHeight / 2f)) / (layerHeight / 2f));
        upperTileY = (int) Math.min(tilesMap.getColumns(), (y - cameraPosition.y + GameInfo.HEIGHT / 2f + cellHeight / 2f) / (layerHeight / 2f));

    }

    public int getVisibleLayerLevel() {
        return visibleLayerLevel;
    }

    public void setVisibleLayerLevel(int visibleLayerLevel) {
        this.visibleLayerLevel = visibleLayerLevel;
        selectHighlightedTile(Gdx.input.getX(), Gdx.input.getY());
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isHighlightedTile() {
        return highlightedTile;
    }

    public int getHighlightedLayer() {
        return highlightedLayer;
    }

    public int getHighlightedRow() {
        return highlightedRow;
    }

    public int getHighlightedColumn() {
        return highlightedColumn;
    }

    public Vector3 getHighLightedTile() {
        if (!highlightedTile) {
            return null;
        }
        return new Vector3(highlightedLayer, highlightedRow, highlightedColumn);
    }

    public void removeHighlightedTile() {
        this.highlightedTile = false;
        this.highlightedLayer = -1;
        this.highlightedRow = -1;
        this.highlightedColumn = -1;
    }

    public void setHighlightedTile(Vector3 tile) {
        setHighlightedTile((int) Math.floor(tile.x), (int) Math.floor(tile.y), (int) Math.floor(tile.z));
    }

    public void setHighlightedTile(int layer, int row, int column) {
        this.highlightedTile = true;
        this.highlightedLayer = MathUtil.between(layer, 0, tilesMap.getLayers() -1);
        this.highlightedRow = MathUtil.between(0, tilesMap.getRows() -1, row);
        this.highlightedColumn = MathUtil.between(0, tilesMap.getColumns() -1, column);
    }

    public final InputHandler inputHandler = new InputHandler() {
        @Override
        public void scrolled(int amount) {
            if (!Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                IsoMapRendererImpl.this.setVisibleLayerLevel(Math.min(Math.max(0, IsoMapRendererImpl.this.getVisibleLayerLevel() - amount), IsoMapRendererImpl.this.getTilesMap().getLayers()-1));
            }
        }

        @Override
        public void touchDown(int screenX, int screenY, int pointer, int button) {
            if (isHighlightedTile()) {
                TerrainType terrain;
                switch (button) {
                    case Input.Buttons.LEFT:
                        terrain = TerrainType.PLAIN;
                        break;
                    case Input.Buttons.RIGHT:
                        terrain = TerrainType.EMPTY;
                        break;
                    default:
                        return;
                }
                tilesMap.setTile(highlightedLayer + 1, highlightedRow, highlightedColumn, terrain);
            }
        }

        @Override
        public void mouseMoved(int screenX, int screenY) {
            selectHighlightedTile(screenX, screenY);
        }
    };

}