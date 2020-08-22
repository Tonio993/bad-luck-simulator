package it.traininground.badluck.tiles;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import it.traininground.badluck.actor.Dude;
import it.traininground.badluck.tiles.debug.CubeShape;
import it.traininground.badluck.util.GameBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class TilesMapRendererImpl extends TilesMapRenderer {

    private Map<TileType, TextureAtlas.AtlasRegion> terrainMap;

    private CubeShape debugShape;

    AtomicInteger drawnTiles = new AtomicInteger();
    
    public Dude dude;

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
        
        tileDrawers.set(TileDrawerManager.MID_PRIORITY,
				(map, layer, row, column, delta) -> {
					Tile tile = dude.getNextTile() != null ? dude.getNextTile() : dude.getTile();
					if (dude != null && tile.getLayer() == layer && tile.getRow() == row && tile.getColumn() == column) {
						GameBatch batch = map.scene.getGame().getBatch();
						dude.draw(batch, delta);
					}
				}
		);

		tileDrawers.set(TileDrawerManager.MID_PRIORITY, (map, layer, row, column, delta) -> {
			if (map.isDebugMode())
				return;
			TileType terrainType = map.tiles.tile(layer, row, column);
			if (terrainType != TileType.EMPTY) {
				float currentOffsetX = x + (row - column - 1) * cellWidth / 2f;
				float currentOffsetY = y - (row + column + 2) * cellHeight / 2f + layerHeight * (layer - 1);
				GameBatch batch = map.scene.getGame().getBatch();
				batch.draw(terrainMap.get(terrainType), currentOffsetX, currentOffsetY);
			}
		});

		tileDrawers.set(TileDrawerManager.MID_PRIORITY, (map, layer, row, column, delta) -> {
			if (!map.isDebugMode())
				return;
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

		tileDrawers.set(TileDrawerManager.HIGH_PRIORITY, (map, layer, row, column, delta) -> {
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

		tileDrawers.set(TileDrawerManager.MID_PRIORITY, (map, layer, row, column, delta) -> {
			if (map.tiles.tile(layer, row, column) != TileType.EMPTY) {
				drawnTiles.getAndIncrement();
			}
		});

		tileDrawers.set(TileDrawerManager.HIGH_PRIORITY, (map, layer, row, column, delta) -> {
//            if (!map.isDebugMode()) return;
			ShapeDrawer shapeDrawer = map.scene.getGame().getBatch().getShapeDrawer();
			shapeDrawer.setDefaultLineWidth(1);
			shapeDrawer.setColor(Color.BLACK);
			shapeDrawer.line(x - 10, y, x + 10, y);
			shapeDrawer.line(x, y - 10, x, y + 10);
		});

	}
}