package it.traininground.badluck.tiles.mapregionfilter;

import com.badlogic.gdx.math.Vector3;

import it.traininground.badluck.tiles.IsoMapRenderer;

public abstract class MapRegionFilter {

    protected IsoMapRenderer mapRenderer;
    protected int visibleLayerLevel;

    public MapRegionFilter(IsoMapRenderer mapRenderer) {
        this.mapRenderer = mapRenderer;
        this.visibleLayerLevel = mapRenderer.getTilesMap().getLayers() - 1;
    }

    public abstract void updateRegion(Vector3 cameraPosition);

    public abstract void drawFilteredRegion();

    public int getVisibleLayerLevel() {
        return visibleLayerLevel;
    }

    public void setVisibleLayerLevel(int visibleLayerLevel) {
        this.visibleLayerLevel = visibleLayerLevel;
    }
}