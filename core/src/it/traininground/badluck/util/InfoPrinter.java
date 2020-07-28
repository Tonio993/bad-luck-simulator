package it.traininground.badluck.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedHashMap;

public class InfoPrinter {

    private static Viewport viewport;
    private static LinkedHashMap<String, InfoRow> infoMap;
    private static Label.LabelStyle style;
    static {
        Camera camera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        camera.position.set(GameInfo.WIDTH/2f, GameInfo.HEIGHT/2f, 0);
        camera.update();
        viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, camera);
        infoMap = new LinkedHashMap<>();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/data-unifon.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        BitmapFont font = generator.generateFont(parameter);
        style = new Label.LabelStyle(font, Color.DARK_GRAY);
    }

    private InfoPrinter() {}

    public static void put(String label, Object value) {
        if (infoMap.containsKey(label)) {
            infoMap.get(label).value.setText(value.toString());
        } else {
            infoMap.put(label, new InfoRow(label, value));
        }
    }

    public static void remove(String label) {
        infoMap.remove(label);
    }

    public static void draw(Batch batch) {
        Matrix4 previousMatrix = batch.getProjectionMatrix();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        float maxLabelWidth = 0;
        float currentHeight = GameInfo.HEIGHT;
        for (String key : infoMap.keySet()) {
            Label label = infoMap.get(key).label;
            label.setPosition(20, (currentHeight -= 20));
            maxLabelWidth = Math.max(maxLabelWidth, label.getWidth());
            label.draw(batch, 1);
        }
        currentHeight = GameInfo.HEIGHT;
        for (String key : infoMap.keySet()) {
            Label label = infoMap.get(key).value;
            label.setPosition(30 + maxLabelWidth, (currentHeight -= 20));
            label.draw(batch, 1);
        }
        batch.setProjectionMatrix(previousMatrix);
    }

    private static class InfoRow {
        Label label;
        Label value;
        InfoRow(String label, Object value) {
            this.label = new Label(label + ":", style);
            this.value = new Label(value.toString(), style);
        }
    }

}
