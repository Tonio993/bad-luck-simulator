package it.traininground.badluck.scenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import it.traininground.badluck.GameMain;
import it.traininground.badluck.input.InputManager;
import it.traininground.badluck.util.GameInfo;

public abstract class DefaultScene implements Screen {

    protected GameMain game;
    protected OrthographicCamera mainCamera;
    protected Viewport gameViewport;
    protected InputManager inputManager;

    public DefaultScene(GameMain game) {
        this.game = game;
        mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        mainCamera.position.set(GameInfo.WIDTH/2f, GameInfo.HEIGHT/2f, 0);
        gameViewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT);
        inputManager = new InputManager();
    }

    public GameMain getGame() {
        return game;
    }

    public void setGame(GameMain game) {
        this.game = game;
    }

    public OrthographicCamera getMainCamera() {
        return mainCamera;
    }

    public void setMainCamera(OrthographicCamera mainCamera) {
        this.mainCamera = mainCamera;
    }

    public Viewport getGameViewport() {
        return gameViewport;
    }

    public void setGameViewport(Viewport gameViewport) {
        this.gameViewport = gameViewport;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }
}
