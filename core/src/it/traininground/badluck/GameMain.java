package it.traininground.badluck;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.traininground.badluck.scenes.TestScene;

public class GameMain extends Game {
	SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new TestScene(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {}

	public SpriteBatch getBatch() {
		return batch;
	}
}
