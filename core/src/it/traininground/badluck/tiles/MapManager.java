package it.traininground.badluck.tiles;

import com.badlogic.gdx.graphics.g2d.Batch;

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

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

}
