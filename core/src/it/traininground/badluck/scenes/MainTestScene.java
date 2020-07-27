package it.traininground.badluck.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import it.traininground.badluck.GameMain;
import it.traininground.badluck.actor.Dude;
import it.traininground.badluck.input.InputManager;
import it.traininground.badluck.tiles.IsoMap;
import it.traininground.badluck.tiles.IsoMapBuilder;
import it.traininground.badluck.tiles.IsoMapSimpleRenderer;
import it.traininground.badluck.tiles.TerrainType;
import it.traininground.badluck.util.CameraMovementHandler;
import it.traininground.badluck.util.GameInfo;

public class MainTestScene implements Screen {

    private GameMain game;

    private OrthographicCamera mainCamera;
    private Viewport gameViewport;

    private Dude dude;
    IsoMapSimpleRenderer isoMapRenderer;

    private CameraMovementHandler mouseEdgeCameraMoving;

    private InputManager inputManager;


    public MainTestScene(GameMain game) {
        this.game = game;

        mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        mainCamera.position.set(GameInfo.WIDTH/2f, GameInfo.HEIGHT/2f, 0);

        gameViewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT);

        dude = new Dude(GameInfo.WIDTH/2f, GameInfo.HEIGHT/2f);

        IsoMap isoMap = new IsoMapBuilder(10, 10, 10).setBaseLayer(0).build();

        isoMap.setTile(0, 3, 3, TerrainType.EMPTY);
        isoMap.setTile(0, 3, 2, TerrainType.EMPTY);
        isoMap.setTile(0, 2, 2, TerrainType.EMPTY);
        isoMap.setTile(0, 2, 3, TerrainType.EMPTY);
        isoMap.setTile(0, 2, 4, TerrainType.EMPTY);
        isoMap.setTile(0, 3, 4, TerrainType.EMPTY);
        isoMap.setTile(0, 4, 4, TerrainType.EMPTY);
        isoMap.setTile(0, 4, 3, TerrainType.EMPTY);
        isoMap.setTile(0, 4, 2, TerrainType.EMPTY);

        isoMap.setTile(1, 3, 3, TerrainType.PLAIN);
        isoMap.setTile(1, 3, 2, TerrainType.DOWN_NORTH);
        isoMap.setTile(1, 2, 2, TerrainType.DOWN_NORTH_WEST);
        isoMap.setTile(1, 2, 3, TerrainType.DOWN_WEST);
        isoMap.setTile(1, 2, 4, TerrainType.DOWN_SOUTH_WEST);
        isoMap.setTile(1, 3, 4, TerrainType.DOWN_SOUTH);
        isoMap.setTile(1, 4, 4, TerrainType.DOWN_SOUTH_EAST);
        isoMap.setTile(1, 4, 3, TerrainType.DOWN_EAST);
        isoMap.setTile(1, 4, 2, TerrainType.DOWN_NORTH_EAST);

        isoMapRenderer = new IsoMapSimpleRenderer(game.getBatch(), 64, 32, 32, isoMap);

        new Thread(new IsoMapRunnable(isoMap) {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    isoMap.setTile(1, 7, 7, TerrainType.PLAIN);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        inputManager = new InputManager();
        Gdx.input.setInputProcessor(inputManager);

        mouseEdgeCameraMoving = new CameraMovementHandler(mainCamera);
        inputManager.bind(mouseEdgeCameraMoving);
        inputManager.bind(isoMapRenderer.inputHandler);
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

    static abstract class IsoMapRunnable implements Runnable {

        protected IsoMap isoMap;

        public IsoMapRunnable(IsoMap isoMap) {
            this.isoMap = isoMap;
        }

        @Override
        public void run() {

        }
    }
}
