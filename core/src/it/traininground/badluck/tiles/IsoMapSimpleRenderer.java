package it.traininground.badluck.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Map;

import it.traininground.badluck.input.InputHandler;
import it.traininground.badluck.scenes.DefaultScene;
import it.traininground.badluck.util.InfoPrinter;
import it.traininground.badluck.util.ShapeDrawerUtil;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class IsoMapSimpleRenderer extends IsoMapRenderer {

    private Map<TerrainType, TextureAtlas.AtlasRegion> terrainMap;
    private ShapeDrawer shapeDrawer;
    private int visibleLayerLevel;

    private int highlightedLayer = -1;
    private int highlightedRow = -1;
    private int highlightedColumn = -1;

    public IsoMapSimpleRenderer(DefaultScene scene, IsoMap isoMap, int cellWidth, int cellHeight, int layerHeight) {
        super(scene, isoMap, cellWidth, cellHeight, layerHeight);
        shapeDrawer = ShapeDrawerUtil.createShapeDrawer(scene.getGame().getBatch());
        this.visibleLayerLevel = isoMap.getLayers() - 1;

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

    private void setSelectedCell(int screenX, int screenY) {
        float currentLayerHeight = getVisibleLayerLevel() * layerHeight;
        Vector3 cameraPoint = scene.getMainCamera().unproject(new Vector3(screenX, screenY + currentLayerHeight, 0));
        float gridProjectedY = (y - cameraPoint.y) / cellHeight;
        float gridProjectedX = (x - cameraPoint.x) / cellWidth;
        highlightedRow = (int) Math.floor(gridProjectedY - gridProjectedX);
        highlightedColumn = (int) Math.floor(gridProjectedY + gridProjectedX);
        if (highlightedRow < 0 || highlightedRow >= isoMap.getRows() || highlightedColumn < 0 || highlightedColumn >= isoMap.getColumns()) {
            highlightedRow = -1;
            highlightedColumn = -1;
        }
        InfoPrinter.put("pointer X", cameraPoint.x);
        InfoPrinter.put("pointer Y", cameraPoint.y);
        InfoPrinter.put("row", highlightedRow);
        InfoPrinter.put("column", highlightedColumn);
    }

    @Override
    public void draw() {
        float currentLayerHeight;
        float currentOffsetX;
        float currentOffsetY;
        for (int l = 0; l <= visibleLayerLevel; l++) {
            currentLayerHeight = (l - 1) * layerHeight;
            for (int r = 0; r < isoMap.getRows(); r++) {
                for (int c = 0; c < isoMap.getColumns(); c++) {
                    TerrainType terrainType = isoMap.getTile(l, r, c);
                    if (terrainType != TerrainType.EMPTY) {
                        currentOffsetX = x + (r - c - 1) * cellWidth / 2f;
                        currentOffsetY = y - (r + c + 2) * cellHeight / 2f + currentLayerHeight;
                        scene.getGame().getBatch().draw(terrainMap.get(terrainType), currentOffsetX, currentOffsetY);
                    }
                }
            }
        }

        float originX = x;
        float originY = y + visibleLayerLevel * layerHeight;

        InfoPrinter.put("origin x", originX);
        InfoPrinter.put("origin y", originY);

        if (highlightedRow != -1 && highlightedColumn != -1 && isoMap.getTile(visibleLayerLevel, highlightedRow, highlightedColumn) != TerrainType.EMPTY) {
            InfoPrinter.put("current tile", isoMap.getTile(visibleLayerLevel, highlightedRow, highlightedColumn));
            float offsetX = originX + (highlightedRow - highlightedColumn) * (cellWidth/2f);
            float offsetY = originY - (highlightedRow + highlightedColumn) * (cellHeight/2f);
            shapeDrawer.setDefaultLineWidth(5);
            shapeDrawer.setColor(Color.ORANGE);
            shapeDrawer.polygon(new float[]{offsetX, offsetY, offsetX + cellWidth/2f, offsetY - cellHeight/2f, offsetX, offsetY - cellHeight, offsetX - cellWidth/2f, offsetY - cellHeight/2f});
        }

        shapeDrawer.setDefaultLineWidth(1);
        shapeDrawer.setColor(Color.BLACK);
//        shapeDrawer.filledCircle(originX, originY, 5);
        shapeDrawer.line(originX - 5, originY, originX + 5, originY);
        shapeDrawer.line(originX, originY - 5, originX, originY + 5);
    }

    public int getVisibleLayerLevel() {
        return visibleLayerLevel;
    }

    public void setVisibleLayerLevel(int visibleLayerLevel) {
        this.visibleLayerLevel = visibleLayerLevel;
        setSelectedCell(Gdx.input.getX(), Gdx.input.getY());
    }

    public final InputHandler inputHandler = new InputHandler() {
        @Override
        public void scrolled(int amount) {
            if (!Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                IsoMapSimpleRenderer.this.setVisibleLayerLevel(Math.min(Math.max(0, IsoMapSimpleRenderer.this.getVisibleLayerLevel() - amount), IsoMapSimpleRenderer.this.getIsoMap().getLayers()-1));
            }
        }

        @Override
        public void mouseMoved(int screenX, int screenY) {
            setSelectedCell(screenX, screenY);
        }
    };

}
