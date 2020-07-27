package it.traininground.badluck.tiles;

import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class IsoMapRenderer {

    protected Batch batch;

    protected int x;
    protected int y;
    protected int cellWidth;
    protected int cellHeight;
    protected int layerHeight;

    protected IsoMap isoMap;

    public IsoMapRenderer(Batch batch, int cellWidth, int cellHeight, int layerHeight, IsoMap isoMap) {
        this.batch = batch;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.layerHeight = layerHeight;
        this.isoMap = isoMap;
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

    public IsoMap getIsoMap() {
        return isoMap;
    }

    public void setIsoMap(IsoMap isoMap) {
        this.isoMap = isoMap;
    }
}
