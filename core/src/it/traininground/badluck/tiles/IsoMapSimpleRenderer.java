package it.traininground.badluck.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

import it.traininground.badluck.input.InputHandler;
import it.traininground.badluck.util.ShapeDrawerUtil;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class IsoMapSimpleRenderer extends IsoMapRenderer {

    private Map<TerrainType, TextureAtlas.AtlasRegion> terrainMap;

    private ShapeDrawer shapeDrawer;

    private int visibleLayerLevel;

    public IsoMapSimpleRenderer(SpriteBatch batch, int cellWidth, int cellHeight, int layerHeight, IsoMap isoMap) {
        super(batch, cellWidth, cellHeight, layerHeight, isoMap);
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

        shapeDrawer.setColor(Color.ORANGE);
        shapeDrawer.setDefaultLineWidth(5);
        shapeDrawer.polygon(new float[]{0, cellHeight/2f, cellWidth/2f, 0, cellWidth, cellHeight/2f, cellWidth/2f, cellHeight});

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
            if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                IsoMapSimpleRenderer.this.setVisibleLayerLevel(Math.min(Math.max(0, IsoMapSimpleRenderer.this.getVisibleLayerLevel() - amount), IsoMapSimpleRenderer.this.getIsoMap().getLayers()-1));
            }
        }
    };


}
