package it.traininground.badluck.tiles;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import it.traininground.badluck.scenes.Scene;

public abstract class TilesMapRenderer {

    protected Scene scene;
    protected TilesMap tilesMap;
    protected Set<TileDrawer> tileDrawerSet;

    protected int x;
    protected int y;
    protected int cellWidth;
    protected int cellHeight;
    protected int layerHeight;

    public TilesMapRenderer(Scene scene, TilesMap tilesMap, int cellWidth, int cellHeight, int layerHeight) {
        this.scene = scene;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.layerHeight = layerHeight;
        this.tilesMap = tilesMap;

        tileDrawerSet = new LinkedHashSet<>();
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

    public int getLayerHeight() {
        return layerHeight;
    }

    public void setLayerHeight(int layerHeight) {
        this.layerHeight = layerHeight;
    }

    public TilesMap getTilesMap() {
        return tilesMap;
    }

    public void setTilesMap(TilesMap tilesMap) {
        this.tilesMap = tilesMap;
    }

    public Set<TileDrawer> getTileDrawerSet() {
        return Collections.unmodifiableSet(tileDrawerSet);
    }

    public void add(TileDrawer tileDrawer) {
        tileDrawerSet.add(tileDrawer);
    }

    public void remove(TileDrawer tileDrawer) {
        tileDrawerSet.remove(tileDrawer);
    }
}
