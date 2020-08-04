package it.traininground.badluck.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import it.traininground.badluck.GameMain;
import it.traininground.badluck.input.handlers.GameCloseInput;
import it.traininground.badluck.tiles.IsoMapRendererImpl;
import it.traininground.badluck.tiles.TerrainType;
import it.traininground.badluck.tiles.TilesMap;
import it.traininground.badluck.tiles.TilesMapBuilder;
import it.traininground.badluck.util.CameraMovementHandler;
import it.traininground.badluck.util.GameInfo;
import it.traininground.badluck.util.InfoDrawer;

public class MainTestScene extends Scene {

//    private Dude dude;
    IsoMapRendererImpl isoMapRenderer;

    private CameraMovementHandler mouseEdgeCameraMoving;

    public MainTestScene(GameMain game) {
        super(game);

//        dude = new Dude(GameInfo.WIDTH/2f, GameInfo.HEIGHT/2f);

        TilesMap tilesMap = new TilesMapBuilder(10, 10, 10).setBaseLayer(-1).build();

        tilesMap.setTile(1, 3, 3, TerrainType.PLAIN);
        tilesMap.setTile(1, 3, 2, TerrainType.DOWN_NORTH);
        tilesMap.setTile(1, 2, 2, TerrainType.DOWN_NORTH_WEST);
        tilesMap.setTile(1, 2, 3, TerrainType.DOWN_WEST);
        tilesMap.setTile(1, 2, 4, TerrainType.DOWN_SOUTH_WEST);
        tilesMap.setTile(1, 3, 4, TerrainType.DOWN_SOUTH);
        tilesMap.setTile(1, 4, 4, TerrainType.DOWN_SOUTH_EAST);
        tilesMap.setTile(1, 4, 3, TerrainType.DOWN_EAST);
        tilesMap.setTile(1, 4, 2, TerrainType.DOWN_NORTH_EAST);

        for (int i=1; i<10; i++) {
            tilesMap.setTile(i, 0, 9, TerrainType.PLAIN);
        }

        isoMapRenderer = new IsoMapRendererImpl(this, tilesMap, 64, 32, 32);
        isoMapRenderer.setX(GameInfo.WIDTH/2);
        isoMapRenderer.setY(GameInfo.HEIGHT/2);
//        isoMapRenderer.setDebugMode(true);

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
        mainCamera.update();

        game.getBatch().begin();
        game.getBatch().setProjectionMatrix(mainCamera.combined);

        isoMapRenderer.draw();
//        dude.draw(game.getBatch(), delta);

        InfoDrawer.draw(game.getBatch());

        game.getBatch().end();


        InfoDrawer.put("fps", Gdx.graphics.getFramesPerSecond());

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
//        dude.dispose();
    }
}
