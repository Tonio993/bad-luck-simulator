package it.traininground.badluck.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Map;

import it.traininground.badluck.input.InputHandler;
import it.traininground.badluck.scenes.DefaultScene;
import it.traininground.badluck.util.MathUtil;
import it.traininground.badluck.util.ShapeDrawerUtil;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class IsoMapSimpleRenderer extends IsoMapRenderer {

    private Map<TerrainType, TextureAtlas.AtlasRegion> terrainMap;
    private ShapeDrawer shapeDrawer;

    private boolean debugMode;

    private int visibleLayerLevel;
    private boolean highlightedTile;
    private int highlightedLayer = -1;
    private int highlightedRow = -1;
    private int highlightedColumn = -1;

    private DebugShape debugShape;

    public IsoMapSimpleRenderer(DefaultScene scene, TilesMap tilesMap, int cellWidth, int cellHeight, int layerHeight) {
        super(scene, tilesMap, cellWidth, cellHeight, layerHeight);
        shapeDrawer = ShapeDrawerUtil.createShapeDrawer(scene.getGame().getBatch());
        this.visibleLayerLevel = tilesMap.getLayers() - 1;

        debugShape = new DebugShape(cellWidth, cellHeight, layerHeight);

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
        float currentLayerHeight;
        float currentOffsetX;
        float currentOffsetY;
        float debugLineOffsetX;
        float debugLineOffsetY;
        for (int l = 0; l <= visibleLayerLevel; l++) {
            currentLayerHeight = (l - 1) * layerHeight;
            for (int r = 0; r < tilesMap.getRows(); r++) {
                for (int c = 0; c < tilesMap.getColumns(); c++) {
                    TerrainType terrainType = tilesMap.getTile(l, r, c);
                    if (terrainType != TerrainType.EMPTY) {
                        currentOffsetX = x + (r - c - 1) * cellWidth / 2f;
                        currentOffsetY = y - (r + c + 2) * cellHeight / 2f + currentLayerHeight;
                        scene.getGame().getBatch().draw(terrainMap.get(terrainType), currentOffsetX, currentOffsetY);
                    }
                }
            }

            // DEBUG
            if (debugMode) {
                for (int r = 0; r < tilesMap.getRows(); r++) {
                    for (int c = 0; c < tilesMap.getColumns(); c++) {
                        TerrainType terrainType = tilesMap.getTile(l, r, c);
                        if (terrainType != TerrainType.EMPTY) {
                            debugLineOffsetX = x + (r - c) * cellWidth / 2f;
                            debugLineOffsetY = y - (r + c) * cellHeight / 2f + l * layerHeight;
                            shapeDrawer.setDefaultLineWidth(1);
                            shapeDrawer.setColor(Color.BLACK);
                            debugShape.draw(shapeDrawer, debugLineOffsetX, debugLineOffsetY);
                        }
                    }
                }
            }
            // FINE DEBUG

            if (highlightedTile && highlightedLayer == l) {
                float offsetX = x + (highlightedRow - highlightedColumn) * (cellWidth/2f);
                float offsetY = y - (highlightedRow + highlightedColumn) * (cellHeight/2f) + highlightedLayer * layerHeight;
                shapeDrawer.setDefaultLineWidth(5);
                shapeDrawer.setColor(Color.ORANGE);
                shapeDrawer.polygon(new float[]{offsetX, offsetY, offsetX + cellWidth/2f, offsetY - cellHeight/2f, offsetX, offsetY - cellHeight, offsetX - cellWidth/2f, offsetY - cellHeight/2f});
            }
        }

        if (debugMode) {
            float originX = x;
            float originY = y + visibleLayerLevel * layerHeight;

            shapeDrawer.setDefaultLineWidth(1);
            shapeDrawer.setColor(Color.BLACK);
            shapeDrawer.line(originX - 5, originY, originX + 5, originY);
            shapeDrawer.line(originX, originY - 5, originX, originY + 5);
        }
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
        this.highlightedLayer = (int) MathUtil.between(layer, 0, tilesMap.getLayers() -1);
        this.highlightedRow = (int) MathUtil.between(0, tilesMap.getRows() -1, row);
        this.highlightedColumn = (int) MathUtil.between(0, tilesMap.getColumns() -1, column);
    }

    public final InputHandler inputHandler = new InputHandler() {
        @Override
        public void scrolled(int amount) {
            if (!Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                IsoMapSimpleRenderer.this.setVisibleLayerLevel(Math.min(Math.max(0, IsoMapSimpleRenderer.this.getVisibleLayerLevel() - amount), IsoMapSimpleRenderer.this.getTilesMap().getLayers()-1));
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

    class DebugShape {
        Polygon[] cubePolygonList;
        Color borderColor = Color.BLACK;
        Color[] cubePolygonColorList = new Color[]{
        new Color(0xbbbbbbff),
        new Color(0x999999ff),
        new Color(0x666666ff)
        };
        DebugShape (int tileWidth, int tileHeight, int layerHeight) {
            float xUnit = tileWidth/2f;
            float yUnit = tileHeight/2f;
            cubePolygonList = new Polygon[]{
                new Polygon(new float[]{0, 0, -xUnit, -yUnit, 0, -2*yUnit, xUnit, -yUnit}),
                new Polygon(new float[]{-xUnit, -yUnit, -xUnit, -yUnit -layerHeight, 0, -2*yUnit - layerHeight, 0, -2*yUnit}),
                new Polygon(new float[]{xUnit, -yUnit, xUnit, -yUnit -layerHeight, 0, -2*yUnit - layerHeight, 0, -2*yUnit})
            };
        }
        void draw(ShapeDrawer drawer, float x, float y) {
            shapeDrawer.setDefaultLineWidth(1);
            float storedColor = drawer.getPackedColor();
            drawer.setDefaultLineWidth(1);
            for (int i = 0; i < 3; i++) {
                drawer.setColor(cubePolygonColorList[i]);
                cubePolygonList[i].setPosition(x, y);
                drawer.filledPolygon(cubePolygonList[i]);
            }
            drawer.setColor(borderColor);
            for (Polygon polygon : cubePolygonList) {
                drawer.polygon(polygon);
            }
            drawer.setColor(storedColor);
        }
    }
}

