package it.traininground.badluck.tiles;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import it.traininground.badluck.actor.IsoActor;
import it.traininground.badluck.tiles.debug.CubeShape;
import it.traininground.badluck.util.GameBatch;
import it.traininground.badluck.util.InfoDrawer;
import it.traininground.badluck.util.MathUtil;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class TilesMapRendererImpl extends TilesMapRenderer {

    private Map<TileType, TextureAtlas.AtlasRegion> terrainMap;

    private CubeShape debugShape;

    AtomicInteger drawnTiles = new AtomicInteger();
    
    private ShaderProgram shader = new ShaderProgram(Gdx.files.internal("shader/fadeTile.vs"), Gdx.files.internal("shader/fadeTile.fs"));
    
    public TilesMapRendererImpl(MapManager map, int cellWidth, int cellHeight, int layerHeight) {
        super(map, cellWidth, cellHeight, layerHeight);
        
        if (shader.getLog().length()!=0)
    		System.out.println(shader.getLog());

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
        
		tileDrawers.set(TileDrawerManager.MID_PRIORITY, (layer, row, column, delta) -> {
			if (map.isDebugMode())
				return;
			TileType tileType = map.tiles.get(layer, row, column).getType();
			if (tileType != TileType.EMPTY) {
				float currentOffsetX = x + (row - column - 1) * cellWidth / 2f;
				float currentOffsetY = y - (row + column + 2) * cellHeight / 2f + layerHeight * (layer - 1);
				GameBatch batch = map.scene.getGame().getBatch();
				
				InfoDrawer.put("layer", (layer - map.getRegion().visibleLayer));
				batch.setShader(shader);
				shader.setUniformf("u_fade", MathUtil.between((map.getRegion().visibleLayer - layer - 10) / 30f, 0, 1));
				shader.setUniformf("u_fadeColor", Color.LIGHT_GRAY);
				batch.setColor(2f, 2f, 2f, 1);
				batch.draw(terrainMap.get(tileType), currentOffsetX, currentOffsetY);
				batch.setShader(null);
			}
		});

		tileDrawers.set(TileDrawerManager.MID_PRIORITY, (layer, row, column, delta) -> {
			if (!map.isDebugMode())
				return;
			TileType terrainType = map.tiles.get(layer, row, column).getType();
			if (terrainType != TileType.EMPTY) {
				float debugLineOffsetX = x + (row - column) * cellWidth / 2f;
				float debugLineOffsetY = y - (row + column) * cellHeight / 2f + layer * layerHeight;
				ShapeDrawer shapeDrawer = map.scene.getGame().getBatch().getShapeDrawer();
				shapeDrawer.setDefaultLineWidth(1);
				shapeDrawer.setColor(Color.BLACK);
				debugShape.draw(shapeDrawer, debugLineOffsetX, debugLineOffsetY);
			}
		});

		tileDrawers.set(TileDrawerManager.HIGH_PRIORITY, (layer, row, column, delta) -> {
			if (map.selection.isHover(layer, row, column)) {
				float offsetX = x + (row - column) * (cellWidth / 2f);
				float offsetY = y - (row + column) * (cellHeight / 2f) + layer * layerHeight;
				ShapeDrawer shapeDrawer = map.scene.getGame().getBatch().getShapeDrawer();
				shapeDrawer.setDefaultLineWidth(5);
				shapeDrawer.setColor(Color.ORANGE);
				shapeDrawer.polygon(new float[] { offsetX, offsetY, offsetX + cellWidth / 2f, offsetY - cellHeight / 2f,
						offsetX, offsetY - cellHeight, offsetX - cellWidth / 2f, offsetY - cellHeight / 2f });
//        		debugShape.draw(shapeDrawer, offsetX, offsetY);        		
			}
		});

		tileDrawers.set(TileDrawerManager.MID_PRIORITY, (layer, row, column, delta) -> {
			if (map.tiles.get(layer, row, column).getType() != TileType.EMPTY) {
				drawnTiles.getAndIncrement();
			}
		});

		tileDrawers.set(TileDrawerManager.MID_PRIORITY,
				(layer, row, column, delta) -> {
					Set<IsoActor> actors = map.actors.get(layer, row, column);
        			if (actors == null) {
        				return;
        			}
					for (IsoActor actor : actors) {
						GameBatch batch = map.scene.getGame().getBatch();
						batch.setShader(shader);
						shader.setUniformf("u_fade", MathUtil.between((map.getRegion().visibleLayer - layer - 10) / 30f, 0, 1));
						shader.setUniformf("u_fadeColor", Color.LIGHT_GRAY);
						actor.draw(batch, delta);
						batch.setShader(null);
					}
				}
		);

	}
}