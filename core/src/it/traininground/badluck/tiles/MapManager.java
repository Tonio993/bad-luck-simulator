package it.traininground.badluck.tiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import it.traininground.badluck.scenes.Scene;

public class MapManager {
	
	protected Scene scene;
	protected TilesMap tiles;
	protected TilesMapRenderer renderer;
	protected MapRegionFilter region;
	protected MapSelection selection;
	
	protected IsoActorsMap actors;
	
	protected boolean debugMode;
	
	public MapManager(Scene scene) {
		this.scene = scene;
	}
	
	public MapManager(Scene scene, TilesMap map, TilesMapRenderer renderer, MapRegionFilter regionFilter, MapSelection selection, IsoActorsMap actors) {
		this.scene = scene;
		
		this.tiles = map;
		this.renderer = renderer;
		this.region = regionFilter;
		this.selection = selection;
		this.actors = actors;
		
		init();
	}
	
	public void init() {
		region.visibleLayer = tiles.layers - 1;
		region.updateRegion(this);
	}

	public void draw(Batch batch, float delta) {
		this.region.drawFilteredRegion(this, delta);
	}
	
	public Vector2 getTilePosition(Tile tile) {
		return getTilePosition(tile.layer, tile.row, tile.column);
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

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public TilesMap getTiles() {
		return tiles;
	}

	public void setTiles(TilesMap tiles) {
		this.tiles = tiles;
	}

	public TilesMapRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(TilesMapRenderer renderer) {
		this.renderer = renderer;
	}

	public MapRegionFilter getRegion() {
		return region;
	}

	public void setRegion(MapRegionFilter region) {
		this.region = region;
	}

	public MapSelection getSelection() {
		return selection;
	}

	public void setSelection(MapSelection selection) {
		this.selection = selection;
	}

	public IsoActorsMap getActors() {
		return actors;
	}

	public void setActors(IsoActorsMap actors) {
		this.actors = actors;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

}
