package it.traininground.badluck.tiles;

import java.util.List;

import it.traininground.badluck.util.GameInfo;

public class MapRegionFilterImpl extends MapRegionFilter {

    private int lowerTileX;
    private int upperTileX;

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
        
        lowerLayer = (int) Math.max(0, (offsetY - screenSizeY) / map.renderer.layerHeight + 1);
        upperLayer = (int) Math.min(visibleLayer, Math.ceil((offsetY + screenSizeY + gridSizeY) / map.renderer.layerHeight + 1));
        lowerTileX = (int) Math.max(0, (offsetX - screenSizeX + gridSizeX) / (map.renderer.cellWidth / 2f));
        upperTileX = (int) Math.min(map.tiles.columns, Math.ceil((offsetX + screenSizeX) / (map.renderer.cellWidth / 2f) + 1));

        lowerTileBoundY = (int) (- offsetY - screenSizeY - map.renderer.layerHeight - (map.renderer.cellHeight / 2f));
        upperTileBoundY = (int) (- offsetY + screenSizeY + (map.renderer.cellHeight / 2f));
    }

    public void drawFilteredRegion(MapManager map, float delta) {
    	Iterable<List<TileDrawer>> drawerSets = map.renderer.getTileDrawers().getDrawerSets();
    	for (List<TileDrawer> drawers : drawerSets) {
    		for (int layer = lowerLayer; layer <= upperLayer; layer++) {
    			int layerPosition = layer * map.renderer.layerHeight;
    			int lowerTileY = (int) Math.max(0, (lowerTileBoundY + layerPosition) / (map.renderer.cellHeight / 2f));
    			int upperTileY = (int) Math.max(0, (upperTileBoundY + layerPosition) / (map.renderer.cellHeight / 2f));
    			for (int r = 0; r < map.tiles.rows; r++) {
    				int lowerTile = Math.max(Math.max(0, lowerTileX - (map.tiles.rows - r)), lowerTileY - r);
    				int upperTile = Math.min(Math.min(map.tiles.columns, upperTileX + r), upperTileY - r);
    				for (int c = lowerTile; c < upperTile; c++) {
    					for (TileDrawer tileDrawer : drawers) {
    						tileDrawer.draw(map, layer, r, c, delta);
    					}
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