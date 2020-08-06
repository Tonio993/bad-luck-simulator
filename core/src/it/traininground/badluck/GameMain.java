package it.traininground.badluck;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import it.traininground.badluck.scenes.GameplayScene;
import it.traininground.badluck.scenes.Scene;
import it.traininground.badluck.util.GameBatch;
import it.traininground.badluck.util.ShapeDrawerUtil;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class GameMain extends Game {
	private GameBatch batch;
	private ShapeDrawer shape;

	boolean closeApplication;

	@Override
	public void create () {
		this.batch = new GameBatch();
		shape = ShapeDrawerUtil.createShapeDrawer(batch);
		try {
			setScreen(new GameplayScene(this));
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

	public GameBatch getBatch() {
		return batch;
	}

	public ShapeDrawer getShape() {
		return shape;
	}

	public void setScreen(Scene screen) {
		super.setScreen(screen);
		Gdx.input.setInputProcessor(screen.getInput());
	}

	public void closeApplication() {
		closeApplication = true;
	}
}
