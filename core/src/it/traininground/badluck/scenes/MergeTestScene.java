package it.traininground.badluck.scenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import it.traininground.badluck.GameMain;

public class MergeTestScene implements Screen {
    GameMain game;
    TextureAtlas texAtlas;
    Texture tex1;
    Texture tex2;
    Texture textureResult;

    public MergeTestScene(GameMain game) {
        this.game = game;
        texAtlas = new TextureAtlas("terrain/terrain.atlas");
        tex1 = texAtlas.findRegion("terrain_DE");
        tex2 = texAtlas.findRegion("terrain_DN").getTexture();

        tex1.getTextureData().prepare();
        Pixmap pixmap1 = tex1.getTextureData().consumePixmap();

        tex2.getTextureData().prepare();
        Pixmap pixmap2 = tex2.getTextureData().consumePixmap();

        pixmap1.drawPixmap(pixmap2, 0, 100);
        textureResult = new Texture(pixmap1);

        pixmap1.dispose();
        pixmap2.dispose();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        game.getBatch().begin();

        game.getBatch().draw(textureResult, 100, 100);


        game.getBatch().end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
