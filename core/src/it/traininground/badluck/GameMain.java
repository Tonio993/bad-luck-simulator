package it.traininground.badluck;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import it.traininground.badluck.scenes.GameplayScene;
import it.traininground.badluck.scenes.Scene;
import it.traininground.badluck.util.GameBatch;

public class GameMain extends Game {
	private GameBatch batch;

	boolean closeApplication;
	
	public GameMain() {
	}

	@Override
	public void create () {
		try {
			batch = new GameBatch();
			setScreen(new GameplayScene(this));
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
	}

	@Override
	public void render () {
		super.render();
		if (closeApplication) {
			this.getScreen().dispose();
			this.dispose();
			Gdx.app.exit();
			System.exit(0);
		}
	}
	
	@Override
	public void dispose () {}

	public GameBatch getBatch() {
		return batch;
	}

	public void setScreen(Scene screen) {
		super.setScreen(screen);
		Gdx.input.setInputProcessor(screen.getInput());
	}

	public void closeApplication() {
		closeApplication = true;
	}
}
