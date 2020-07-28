package it.traininground.badluck;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.traininground.badluck.scenes.MainTestScene;

public class GameMain extends Game {
	SpriteBatch batch;

	@Override
	public void create () {
		this.batch = new SpriteBatch();
		setScreen(new MainTestScene(this));
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
