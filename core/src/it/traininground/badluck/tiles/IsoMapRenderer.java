package it.traininground.badluck.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class IsoMapRenderer {

    private TextureAtlas terrain;
    private Array<Array<TextureAtlas.AtlasRegion>> mapArray;

    private int x;
    private int y;
    private int rows;
    private int columns;
    private int cellWidth;
    private int cellHeight;

    private IsoMap isoMap;

    public IsoMapRenderer(int rows, int columns, int cellWidth, int cellHeight) {
        this.rows = rows;
        this.columns = columns;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;

        isoMap = new IsoMap(rows, columns);

        terrain = new TextureAtlas("terrain/terrain.atlas");
        Array<TextureAtlas.AtlasRegion> terrainTexture = terrain.findRegions("terrain_P");



    }

    public void draw(SpriteBatch batch) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                batch.draw(mapArray.get(r).get(c), x + ((r-c) * (cellWidth/2)), y - ((r+c) * (cellHeight/2)));
            }
        }
    }

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

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
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
}
