package it.traininground.badluck.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import it.traininground.badluck.GameMain;
import it.traininground.badluck.actor.Dude;
import it.traininground.badluck.input.handlers.GameCloseInput;
import it.traininground.badluck.tiles.IsoMap;
import it.traininground.badluck.tiles.IsoMapBuilder;
import it.traininground.badluck.tiles.IsoMapSimpleRenderer;
import it.traininground.badluck.tiles.TerrainType;
import it.traininground.badluck.util.CameraMovementHandler;
import it.traininground.badluck.util.GameInfo;
import it.traininground.badluck.util.InfoPrinter;

public class MainTestScene extends DefaultScene {

    private Dude dude;
    IsoMapSimpleRenderer isoMapRenderer;

    private CameraMovementHandler mouseEdgeCameraMoving;

    public MainTestScene(GameMain game) {
        super(game);

        dude = new Dude(GameInfo.WIDTH/2f, GameInfo.HEIGHT/2f);

        IsoMap isoMap = new IsoMapBuilder(10, 10, 10).setBaseLayer(0).build();

        isoMap.setTile(1, 3, 3, TerrainType.PLAIN);
        isoMap.setTile(1, 3, 2, TerrainType.DOWN_NORTH);
        isoMap.setTile(1, 2, 2, TerrainType.DOWN_NORTH_WEST);
        isoMap.setTile(1, 2, 3, TerrainType.DOWN_WEST);
        isoMap.setTile(1, 2, 4, TerrainType.DOWN_SOUTH_WEST);
        isoMap.setTile(1, 3, 4, TerrainType.DOWN_SOUTH);
        isoMap.setTile(1, 4, 4, TerrainType.DOWN_SOUTH_EAST);
        isoMap.setTile(1, 4, 3, TerrainType.DOWN_EAST);
        isoMap.setTile(1, 4, 2, TerrainType.DOWN_NORTH_EAST);

        for (int i=1; i<10; i++) {
            isoMap.setTile(i, 0, 9, TerrainType.PLAIN);
        }

        isoMapRenderer = new IsoMapSimpleRenderer(this, isoMap, 64, 32, 32);

        mouseEdgeCameraMoving = new CameraMovementHandler(mainCamera);
        inputManager.bind(mouseEdgeCameraMoving.inputHandler);
        inputManager.bind(isoMapRenderer.inputHandler);
        inputManager.bind(new GameCloseInput());

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mouseEdgeCameraMoving.moveCamera(delta);

        game.getBatch().begin();

        isoMapRenderer.draw();
        dude.draw(game.getBatch(), delta);

        InfoPrinter.draw(game.getBatch());

        game.getBatch().end();

        game.getBatch().setProjectionMatrix(mainCamera.combined);
        mainCamera.update();
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(GameInfo.WIDTH, GameInfo.HEIGHT);
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
        dude.dispose();
    }
}
