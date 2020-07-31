package it.traininground.badluck.tiles;

import com.badlogic.gdx.math.Vector3;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import it.traininground.badluck.util.GameInfo;
import it.traininground.badluck.util.InfoDrawer;

public class MapRegionSelector {

    private IsoMapRenderer mapRenderer;

    private Set<TileDrawer> tileDrawerSet;

    private int lowerLayer;
    private int upperLayer;
    private int lowerTileX;
    private int lowerTileY;
    private int upperTileX;
    private int upperTileY;

    private int visibleLayerLevel;

    public MapRegionSelector(IsoMapRenderer mapRenderer) {
        this.mapRenderer = mapRenderer;
        this.upperLayer = mapRenderer.getTilesMap().getLayers();
        this.upperTileX = mapRenderer.getTilesMap().getColumns();
        this.upperTileY = mapRenderer.getTilesMap().getColumns();
        this.visibleLayerLevel = mapRenderer.getTilesMap().getLayers();

        this.tileDrawerSet = new LinkedHashSet<>();
    }

    public void updateRegion(Vector3 cameraPosition) {
        lowerLayer = (int) Math.max(0, Math.ceil((cameraPosition.y - mapRenderer.getY() - GameInfo.HEIGHT / 2f) / mapRenderer.layerHeight));
        upperLayer = (int) Math.min(visibleLayerLevel, Math.floor((cameraPosition.y - mapRenderer.getY() + GameInfo.HEIGHT / 2f + mapRenderer.getCellHeight() / 2f * (mapRenderer.getTilesMap().getRows() + mapRenderer.getTilesMap().getColumns())) / mapRenderer.getLayerHeight() + 1));
        lowerTileX = (int) Math.max(0, (mapRenderer.getX() - cameraPosition.x - GameInfo.WIDTH / 2f + (mapRenderer.getCellWidth() / 2f * (mapRenderer.getTilesMap().getRows() - 1))) / (mapRenderer.getCellWidth() / 2f));
        upperTileX = (int) Math.min(mapRenderer.getTilesMap().getColumns(), (mapRenderer.getX() - cameraPosition.x + GameInfo.WIDTH / 2f + (mapRenderer.getCellWidth() / 2f * (mapRenderer.getTilesMap().getRows() + 1))) / (mapRenderer.getCellWidth() / 2f));
        lowerTileY = (int) Math.max(0, (mapRenderer.getY() - cameraPosition.y - GameInfo.HEIGHT / 2f - (mapRenderer.getCellHeight() / 2f)) / (mapRenderer.getLayerHeight() / 2f));
        upperTileY = (int) Math.min(mapRenderer.getTilesMap().getColumns(), (mapRenderer.getY() - cameraPosition.y + GameInfo.HEIGHT / 2f + mapRenderer.getCellHeight() / 2f) / (mapRenderer.getLayerHeight() / 2f));

        InfoDrawer.put("lower layer", lowerLayer);
        InfoDrawer.put("upper layer", upperLayer);
        InfoDrawer.put("lower tile x", lowerTileX);
        InfoDrawer.put("lower tile y", lowerTileY);
        InfoDrawer.put("upper tile x", upperTileX);
        InfoDrawer.put("upper tile y", upperTileY);
    }

    public void draw() {
        for (int l = lowerLayer; l <= upperLayer; l++) {
            for (TileDrawer tileDrawer : tileDrawerSet) {
                for (int r = 0; r < mapRenderer.getTilesMap().getRows(); r++) {
                    int lowerTile = Math.max(lowerTileX -(mapRenderer.getTilesMap().getRows() - r - 1), lowerTileY -r);
                    int upperTile = Math.min(upperTileX -(mapRenderer.getTilesMap().getRows() - r - 1), upperTileY -r);
                    for (int c = lowerTile; c < upperTile; c++) {
                        tileDrawer.draw(l, r, c);
                    }
                }
            }
        }
    }

    public Set<TileDrawer> getLayerHandlerSet() {
        return Collections.unmodifiableSet(tileDrawerSet);
    }

    public void add(TileDrawer tileDrawer) {
        tileDrawerSet.add(tileDrawer);
    }

    public void remove(TileDrawer tileDrawer) {
        tileDrawerSet.remove(tileDrawer);
    }

}