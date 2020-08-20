package it.traininground.badluck.tiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import it.traininground.badluck.scenes.Scene;

public class MapManager {
	
	protected final Scene scene;
	protected final TilesMap tiles;
	protected final TilesMapRenderer renderer;
	protected final MapRegionFilter region;
	protected final MapSelection selection;
	
	protected boolean debugMode;
	
	public MapManager(Scene scene, TilesMap map, TilesMapRenderer renderer, MapRegionFilter regionFilter, MapSelection selection) {
		this.scene = scene;
		this.tiles = map;
		this.renderer = renderer;
		this.region = regionFilter;
		this.selection = selection;
		
		region.visibleLayer = tiles.layers - 1;
		region.updateRegion(this);
	}

	public void draw(Batch batch) {
		this.region.drawFilteredRegion(this);
	}
	
	public Vector2 getTilePosition(Tile tile) {
		return getTilePosition(tile.getLayer(), tile.getRow(), tile.getColumn());
	}
	
	public Vector2 getTilePosition(int layer, int row, int column) {
		float xUnit = renderer.cellWidth / 2f;
		float yUnit = renderer.cellHeight / 2f;
		int xFactor = row - column;
		int yFactor = row + column + 1;
		int offsetX = renderer.x + (int) (xFactor * xUnit);
		int offsetY = renderer.y - (int) (yFactor * yUnit) + layer * renderer.layerHeight;
		return new Vector2(offsetX, offsetY);
	}

	public Scene getScene() {
		return scene;
	}

	public TilesMap getTiles() {
		return tiles;
	}

	public TilesMapRenderer getRenderer() {
		return renderer;
	}

	public MapRegionFilter getRegion() {
		return region;
	}

	public MapSelection getSelection() {
		return selection;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

}
