package it.traininground.badluck.scenes;

import com.badlogic.gdx.Screen;

import it.traininground.badluck.GameMain;
import it.traininground.badluck.camera.CameraManager;
import it.traininground.badluck.gameplay.GameplayManager;
import it.traininground.badluck.input.InputManager;

public abstract class Scene implements Screen {

    protected GameMain game;
    protected CameraManager camera;
    protected InputManager input;
    protected GameplayManager gameplay;

    public Scene(GameMain game) {
        this.game = game;
        camera = new CameraManager(this);
        input = new InputManager(this);
        gameplay = new GameplayManager(this);
    }

	public GameMain getGame() {
		return game;
	}

	public void setGame(GameMain game) {
		this.game = game;
	}

	public CameraManager getCamera() {
		return camera;
	}

	public void setCamera(CameraManager camera) {
		this.camera = camera;
	}

	public InputManager getInput() {
		return input;
	}

	public void setInput(InputManager input) {
		this.input = input;
	}

	public GameplayManager getGameplay() {
		return gameplay;
	}

	public void setGameplay(GameplayManager gameplay) {
		this.gameplay = gameplay;
	}

}
