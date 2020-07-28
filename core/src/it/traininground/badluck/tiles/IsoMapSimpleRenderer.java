package it.traininground.badluck.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Map;

import it.traininground.badluck.input.InputHandler;
import it.traininground.badluck.util.ShapeDrawerUtil;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class IsoMapSimpleRenderer extends IsoMapRenderer {

    private Camera camera;
    private Map<TerrainType, TextureAtlas.AtlasRegion> terrainMap;
    private ShapeDrawer shapeDrawer;
    private int visibleLayerLevel;

    private int highlightedRow = -1;
    private int highlightedColumn = -1;

    public IsoMapSimpleRenderer(SpriteBatch batch, int cellWidth, int cellHeight, int layerHeight, IsoMap isoMap, Camera camera) {
        super(batch, cellWidth, cellHeight, layerHeight, isoMap);
        this.camera = camera;
        shapeDrawer = ShapeDrawerUtil.createShapeDrawer(batch);
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
        Vector3 touchPoint = camera.unproject(new Vector3(screenX, screenY, 0));
        System.out.println(touchPoint);
    }

    @Override
    public void draw() {
        for (int l = 0; l <= visibleLayerLevel; l++) {
            for (int r = 0; r < isoMap.getRows(); r++) {
                for (int c = 0; c < isoMap.getColumns(); c++) {
                    TerrainType terrainType = isoMap.getTile(l, r, c);
                    if (terrainType != TerrainType.EMPTY) {
                        batch.draw(terrainMap.get(terrainType), x + ((r-c) * (cellWidth/2f)), y + (l * layerHeight) - ((r+c) * (cellHeight/2f)));
                    }
                }
            }
        }

        shapeDrawer.setDefaultLineWidth(1);
        shapeDrawer.setColor(Color.BLACK);
        shapeDrawer.line(0, 0, (isoMap.getRows() + isoMap.getColumns()) * cellWidth/2f, 0);
        shapeDrawer.line(0, 0, 0, (isoMap.getRows() + isoMap.getColumns() + 1) * cellHeight/2f + layerHeight * isoMap.getLayers());

        shapeDrawer.setColor(Color.ORANGE);
        shapeDrawer.setDefaultLineWidth(5);
        shapeDrawer.polygon(new float[]{0, layerHeight + cellHeight/2f, cellWidth/2f, layerHeight, cellWidth, layerHeight + cellHeight/2f, cellWidth/2f, layerHeight + cellHeight});

    }

    public int getVisibleLayerLevel() {
        return visibleLayerLevel;
    }

    public void setVisibleLayerLevel(int visibleLayerLevel) {
        this.visibleLayerLevel = visibleLayerLevel;
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
