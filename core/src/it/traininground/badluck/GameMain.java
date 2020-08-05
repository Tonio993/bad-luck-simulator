package it.traininground.badluck;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.IOException;

import it.traininground.badluck.scenes.Scene;
import it.traininground.badluck.scenes.MainTestScene;

public class GameMain extends Game {
	SpriteBatch batch;

	boolean closeApplication;

	@Override
	public void create () {
		this.batch = new SpriteBatch();
		try {
			setScreen(new MainTestScene(this));
		} catch (IOException | ClassNotFoundException e) {
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

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setScreen(Scene screen) {
		super.setScreen(screen);
		Gdx.input.setInputProcessor(screen.getInputManager());
	}

	public void closeApplication() {
		closeApplication = true;
	}
}
