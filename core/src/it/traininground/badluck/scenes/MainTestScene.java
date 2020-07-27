package it.traininground.badluck.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import it.traininground.badluck.GameMain;
import it.traininground.badluck.actor.Dude;
import it.traininground.badluck.tiles.IsoMap;
import it.traininground.badluck.tiles.IsoMapBuilder;
import it.traininground.badluck.tiles.IsoMapRenderer;
import it.traininground.badluck.tiles.IsoMapSimpleRenderer;
import it.traininground.badluck.tiles.TerrainType;
import it.traininground.badluck.util.CameraMovementHandler;
import it.traininground.badluck.util.GameInfo;

public class MainTestScene implements Screen, InputProcessor {

    private GameMain game;

    private OrthographicCamera mainCamera;
    private Viewport gameViewport;

    private Dude dude;
    IsoMapRenderer isoMapRenderer;

    private CameraMovementHandler mouseEdgeCameraMoving;

    private boolean isCameraDragged;
    private Vector3 cameraDraggedStart;
    private Vector3 cameraDraggedTouch;

    public MainTestScene(GameMain game) {
        this.game = game;

        mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        mainCamera.position.set(GameInfo.WIDTH/2f, GameInfo.HEIGHT/2f, 0);

        gameViewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT);

        dude = new Dude(GameInfo.WIDTH/2f, GameInfo.HEIGHT/2f);

        IsoMap isoMap = new IsoMapBuilder(2, 10, 10).setBaseLayer(0).build();

        isoMap.setTile(1, 3, 3, TerrainType.PLAIN);
        isoMap.setTile(1, 3, 2, TerrainType.DOWN_NORTH);
        isoMap.setTile(1, 2, 2, TerrainType.DOWN_NORTH_WEST);
        isoMap.setTile(1, 2, 3, TerrainType.DOWN_WEST);
        isoMap.setTile(1, 2, 4, TerrainType.DOWN_SOUTH_WEST);
        isoMap.setTile(1, 3, 4, TerrainType.DOWN_SOUTH);
        isoMap.setTile(1, 4, 4, TerrainType.DOWN_SOUTH_EAST);
        isoMap.setTile(1, 4, 3, TerrainType.DOWN_EAST);
        isoMap.setTile(1, 4, 2, TerrainType.DOWN_NORTH_EAST);

        isoMapRenderer = new IsoMapSimpleRenderer(64, 32, 32, isoMap);

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

        Gdx.input.setInputProcessor(this);


        mouseEdgeCameraMoving = new CameraMovementHandler(mainCamera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!isCameraDragged) {
            mouseEdgeCameraMoving.moveCamera(delta);
        }


        game.getBatch().begin();

        isoMapRenderer.draw(game.getBatch());
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

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
            System.exit(0);
        }
        mouseEdgeCameraMoving.checkKeyCameraMovement(keycode, false);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mouseEdgeCameraMoving.checkKeyCameraMovement(keycode, true);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPoint = mainCamera.unproject(new Vector3(screenX, screenY, 0));
        switch (button) {
            case Input.Buttons.LEFT:
                dude.setNextX(touchPoint.x);
                dude.setNextY(touchPoint.y);
                break;
            case Input.Buttons.MIDDLE:
                isCameraDragged = true;
                cameraDraggedStart = new Vector3(mainCamera.position.x, mainCamera.position.y, 0);
                cameraDraggedTouch = touchPoint;
                break;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.MIDDLE) {
            isCameraDragged = false;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 touchPoint = mainCamera.unproject(new Vector3(screenX, screenY, 0));
        if (isCameraDragged) {
            mainCamera.position.set(cameraDraggedStart.sub(touchPoint.sub(cameraDraggedTouch)));
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseEdgeCameraMoving.checkMousePosition(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        mainCamera.viewportWidth = Math.max(GameInfo.WIDTH, mainCamera.viewportWidth + (GameInfo.WIDTH / 20f) * amount);
        mainCamera.viewportHeight = Math.max(GameInfo.HEIGHT, mainCamera.viewportHeight + (GameInfo.HEIGHT / 20f) * amount);
        return false;
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
