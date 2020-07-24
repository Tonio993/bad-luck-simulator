package it.traininground.badluck.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import it.traininground.badluck.util.GameInfo;

public class IsoMap {

    ShapeRenderer shapeRenderer;

    private int x;
    private int y;
    private int rows;
    private int columns;
    private int cellWidth;
    private int cellHeight;

    public IsoMap(int r, int c, int w, int h) {
        this.rows = r;
        this.columns = c;
        this.cellWidth = w;
        this.cellHeight = h;
        shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        shapeRenderer.setColor(Color.GRAY);

        for (int i = 0; i <= rows; i++) {
            shapeRenderer.line(x + (columns +i) * (cellWidth /2), y + i * (cellHeight /2), x + i * (cellWidth /2), y + (columns +i) * (cellHeight /2));
        }
        for (int i = 0; i <= columns; i++) {
            shapeRenderer.line(x + (columns -i) * (cellWidth /2), y + i * (cellHeight /2), x + (columns + rows -i) * (cellWidth /2), y + (rows +i) * (cellHeight /2));
        }
        shapeRenderer.end();
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
