package it.traininground.badluck.tiles;

public abstract class MapRegionFilter {
	
    protected int visibleLayer;

    public abstract void updateRegion(MapManager map);

    public abstract void drawFilteredRegion(MapManager map);

	public int getVisibleLayer() {
		return visibleLayer;
	}

	public void setVisibleLayer(int visibleLayer) {
		this.visibleLayer = visibleLayer;
	}
}