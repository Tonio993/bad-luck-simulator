package it.traininground.badluck.tiles;

import it.traininground.badluck.util.GameInfo;
import it.traininground.badluck.util.InfoDrawer;
import it.traininground.badluck.util.MathUtil;

public class MapRegionFilterImpl extends MapRegionFilter {

    private int lowerLayerIndex;
    private int upperLayerIndex;
    private int lowerTileIndexX;
    private int upperTileIndexX;

    private float lowerTileBoundY;
    private float upperTileBoundY;

    private float screenSizeX;
    private float screenSizeY;
    private float gridSizeX;
    private float gridSizeY;

    public MapRegionFilterImpl() {
        this.screenSizeX = GameInfo.WIDTH / 2f;
        this.screenSizeY = GameInfo.HEIGHT / 2f;
    }

    public void updateRegion(MapManager map) {
        int tilesFactor = map.tiles.getRows() + map.tiles.getColumns();
        this.gridSizeX = (map.renderer.cellWidth / 4f) * tilesFactor;
        this.gridSizeY = (map.renderer.cellHeight / 2f) * tilesFactor;
        float offsetX = map.renderer.x - map.scene.getCamera().getMain().position.x;
        float offsetY = map.scene.getCamera().getMain().position.y - map.renderer.y;
        
        lowerLayerIndex = (int) Math.max(0, (offsetY - screenSizeY) / map.renderer.layerHeight + 1);
        upperLayerIndex = (int) Math.min(visibleLayer, Math.ceil((offsetY + screenSizeY + gridSizeY) / map.renderer.layerHeight + 1));
        lowerTileIndexX = (int) Math.max(0, (offsetX - screenSizeX + gridSizeX) / (map.renderer.cellWidth / 2f));
        upperTileIndexX = (int) Math.min(map.tiles.columns, Math.ceil((offsetX + screenSizeX) / (map.renderer.cellWidth / 2f) + 1));

        lowerTileBoundY = (int) (- offsetY - screenSizeY - map.renderer.layerHeight - (map.renderer.cellHeight / 2f));
        upperTileBoundY = (int) (- offsetY + screenSizeY + (map.renderer.cellHeight / 2f));
    }

    public void drawFilteredRegion(MapManager map) {
        InfoDrawer.put("lower layer", lowerLayerIndex);
        InfoDrawer.put("upper layer", upperLayerIndex);
        for (int layer = lowerLayerIndex; layer <= upperLayerIndex; layer++) {
            int layerPosition = layer * map.renderer.layerHeight;
            int lowerTileIndexY = (int) Math.max(0, (lowerTileBoundY + layerPosition) / (map.renderer.cellHeight / 2f));
            int upperTileIndexY = (int) Math.max(0, (upperTileBoundY + layerPosition) / (map.renderer.cellHeight / 2f));
            for (TileDrawer tileDrawer : map.renderer.getTileDrawerSet()) {
                for (int r = 0; r < map.tiles.rows; r++) {
                    int lowerTile = MathUtil.max(0, lowerTileIndexX -(map.tiles.rows - r), lowerTileIndexY - r);
                    int upperTile = MathUtil.min(map.tiles.columns, upperTileIndexX + r, upperTileIndexY - r);
                    for (int c = lowerTile; c < upperTile; c++) {
                        tileDrawer.draw(map, layer, r, c);
                    }
                }
            }
        }
    }

    public int getVisibleLayerLevel() {
		return visibleLayer;
	}

	public void setVisibleLayerLevel(int visibleLayerLevel) {
		this.visibleLayer = visibleLayerLevel;
	}

	public float getScreenSizeX() {
		return screenSizeX;
	}

	public void setScreenSizeX(float screenSizeX) {
		this.screenSizeX = screenSizeX;
	}

	public float getScreenSizeY() {
		return screenSizeY;
	}

	public void setScreenSizeY(float screenSizeY) {
		this.screenSizeY = screenSizeY;
	}

	public float getGridSizeX() {
		return gridSizeX;
	}

	public void setGridSizeX(float gridSizeX) {
		this.gridSizeX = gridSizeX;
	}

	public float getGridSizeY() {
		return gridSizeY;
	}

	public void setGridSizeY(float gridSizeY) {
		this.gridSizeY = gridSizeY;
	}
}