package it.traininground.badluck.tiles.debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class DebugShape {

    Polygon[] cubePolygonList;
    Color borderColor = Color.BLACK;
    Color[] cubePolygonColorList = new Color[]{
            new Color(0xbbbbbbff),
            new Color(0x999999ff),
            new Color(0x666666ff)
    };

    public DebugShape (int tileWidth, int tileHeight, int layerHeight) {
        float xUnit = tileWidth/2f;
        float yUnit = tileHeight/2f;
        cubePolygonList = new Polygon[]{
                new Polygon(new float[]{0, 0, -xUnit, -yUnit, 0, -2*yUnit, xUnit, -yUnit}),
                new Polygon(new float[]{-xUnit, -yUnit, -xUnit, -yUnit -layerHeight, 0, -2*yUnit - layerHeight, 0, -2*yUnit}),
                new Polygon(new float[]{xUnit, -yUnit, xUnit, -yUnit -layerHeight, 0, -2*yUnit - layerHeight, 0, -2*yUnit})
        };
    }

    public void draw(ShapeDrawer drawer, float x, float y) {
        drawer.setDefaultLineWidth(1);
        float storedColor = drawer.getPackedColor();
        drawer.setDefaultLineWidth(1);
        for (int i = 0; i < 3; i++) {
            drawer.setColor(cubePolygonColorList[i]);
            cubePolygonList[i].setPosition(x, y);
            drawer.filledPolygon(cubePolygonList[i]);
        }
        drawer.setColor(borderColor);
        for (Polygon polygon : cubePolygonList) {
            drawer.polygon(polygon);
        }
        drawer.setColor(storedColor);
    }
}
