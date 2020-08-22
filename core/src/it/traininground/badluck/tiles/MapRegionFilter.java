package it.traininground.badluck.tiles;

public abstract class MapRegionFilter {
	
    protected int lowerLayer;
    protected int upperLayer;
    protected int visibleLayer;

    public abstract void updateRegion(MapManager map);

    public abstract void drawFilteredRegion(MapManager map, float delta);

	public int getLowerLayer() {
		return lowerLayer;
	}

	public void setLowerLayer(int lowerLayer) {
		this.lowerLayer = lowerLayer;
	}

	public int getUpperLayer() {
		return upperLayer;
	}

	public void setUpperLayer(int upperLayer) {
		this.upperLayer = upperLayer;
	}

	public int getVisibleLayer() {
		return visibleLayer;
	}

	public void setVisibleLayer(int visibleLayer) {
		this.visibleLayer = visibleLayer;
	}
}