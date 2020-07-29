package it.traininground.badluck.tiles;

import it.traininground.badluck.scenes.DefaultScene;

public abstract class IsoMapRenderer {

    protected DefaultScene scene;
    protected TilesMap tilesMap;

    protected int x;
    protected int y;
    protected int cellWidth;
    protected int cellHeight;
    protected int layerHeight;

    public IsoMapRenderer(DefaultScene scene, TilesMap tilesMap, int cellWidth, int cellHeight, int layerHeight) {
        this.scene = scene;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.layerHeight = layerHeight;
        this.tilesMap = tilesMap;
    }

    public abstract void draw();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(int cellHeight) {
        this.cellHeight = cellHeight;
    }

    public TilesMap getTilesMap() {
        return tilesMap;
    }

    public void setTilesMap(TilesMap tilesMap) {
        this.tilesMap = tilesMap;
    }
}
