package it.traininground.badluck.tiles;

import com.badlogic.gdx.math.Vector3;

import it.traininground.badluck.util.GameInfo;
import it.traininground.badluck.util.InfoDrawer;
import it.traininground.badluck.util.MathUtil;

public class MapRegionSelector {

    private IsoMapRenderer mapRenderer;


    private int lowerLayerIndex;
    private int upperLayerIndex;
    private int lowerTileIndexX;
    private int upperTileIndexX;

    private float lowerTileBoundY;
    private float upperTileBoundY;

    private float screenSizeX;
    private float screenSizeY;
    private float layerSize;
    private float gridSizeX;
    private float gridSizeY;

    private int visibleLayerLevel;

    public MapRegionSelector(IsoMapRenderer mapRenderer) {
        this.mapRenderer = mapRenderer;
        this.upperLayerIndex = mapRenderer.getTilesMap().getLayers();
        this.upperTileIndexX = mapRenderer.getTilesMap().getColumns();
        this.upperTileBoundY = mapRenderer.getTilesMap().getColumns();
        this.visibleLayerLevel = mapRenderer.getTilesMap().getLayers();

        this.screenSizeX = GameInfo.WIDTH / 2f;
        this.screenSizeY = GameInfo.HEIGHT / 2f;

        this.layerSize = mapRenderer.getLayerHeight() * mapRenderer.getTilesMap().getLayers();

        int tilesFactor = mapRenderer.getTilesMap().getRows() + mapRenderer.getTilesMap().getColumns();
        this.gridSizeX = (mapRenderer.getCellWidth() / 4f) * tilesFactor;
        this.gridSizeY = (mapRenderer.getCellHeight() / 2f) * tilesFactor;
        InfoDrawer.put("grid X", gridSizeX);
    }

    public void updateRegion(Vector3 cameraPosition) {
        float offsetX = mapRenderer.getX() - cameraPosition.x;
        float offsetY = cameraPosition.y - mapRenderer.getY();
        lowerLayerIndex = (int) Math.max(0, (offsetY - screenSizeY) / mapRenderer.getLayerHeight() + 1);
        upperLayerIndex = (int) Math.min(visibleLayerLevel, Math.ceil((offsetY + screenSizeY - gridSizeY + layerSize) / mapRenderer.getLayerHeight() + 1));
        lowerTileIndexX = (int) Math.max(0, (offsetX - screenSizeX + gridSizeX) / (mapRenderer.getCellWidth() / 2f) - 1);
        upperTileIndexX = (int) Math.min(mapRenderer.getTilesMap().getColumns(), Math.ceil((offsetX + screenSizeX) / (mapRenderer.getCellWidth() / 2f) + 1));

        lowerTileBoundY = (int) (- offsetY - screenSizeY - mapRenderer.getLayerHeight() - (mapRenderer.getCellHeight() / 2f));
        upperTileBoundY = (int) (- offsetY + screenSizeY + (mapRenderer.getCellHeight() / 2f));
    }

    public void draw() {
        for (int layer = lowerLayerIndex; layer < upperLayerIndex; layer++) {
            int layerPosition = layer * mapRenderer.getLayerHeight();
            int lowerTileIndexY = (int) Math.max(0, (lowerTileBoundY + layerPosition) / (mapRenderer.getCellHeight() / 2f));
            int upperTileIndexY = (int) Math.max(0, (upperTileBoundY + layerPosition) / (mapRenderer.getCellHeight() / 2f));
            InfoDrawer.put("lower tile y", lowerTileIndexY);
            InfoDrawer.put("upper tile y", upperTileIndexY);
            for (TileDrawer tileDrawer : mapRenderer.getTileDrawerSet()) {
                for (int r = 0; r < mapRenderer.getTilesMap().getRows(); r++) {
                    int lowerTile = MathUtil.max(0, lowerTileIndexX -(mapRenderer.getTilesMap().getRows() - r), lowerTileIndexY - r);
                    int upperTile = MathUtil.min(mapRenderer.getTilesMap().getColumns(), upperTileIndexX + r, upperTileIndexY - r);
                    for (int c = lowerTile; c < upperTile; c++) {
                        tileDrawer.draw(layer, r, c);
                    }
                }
            }
        }
    }

    public int getVisibleLayerLevel() {
        return visibleLayerLevel;
    }

    public void setVisibleLayerLevel(int visibleLayerLevel) {
        this.visibleLayerLevel = visibleLayerLevel;
    }
}