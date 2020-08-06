package it.traininground.badluck.tiles;

import com.badlogic.gdx.graphics.g2d.Batch;

import it.traininground.badluck.tiles.mapregionfilter.MapRegionFilter;

public class MapManager {
	
	private TilesMap map;
	private TilesMapRenderer renderer;
	private MapRegionFilter regionFilter;

	public void draw(Batch batch) {
		
	}
	
	public TilesMap getMap() {
		return map;
	}
	public void setMap(TilesMap map) {
		this.map = map;
	}
	public TilesMapRenderer getRenderer() {
		return renderer;
	}
	public void setRenderer(TilesMapRenderer renderer) {
		this.renderer = renderer;
	}
	public MapRegionFilter getRegionFilter() {
		return regionFilter;
	}
	public void setRegionFilter(MapRegionFilter regionFilter) {
		this.regionFilter = regionFilter;
	}
	
	

}
