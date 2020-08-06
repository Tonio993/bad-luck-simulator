package it.traininground.badluck.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class GameBatch extends SpriteBatch {
	
	private ShapeDrawer shapeDrawer;

	public GameBatch() {
		super();
		shapeDrawer = ShapeDrawerUtil.createShapeDrawer(this);
	}

	public GameBatch(int size, ShaderProgram defaultShader) {
		super(size, defaultShader);
		shapeDrawer = ShapeDrawerUtil.createShapeDrawer(this);
	}

	public GameBatch(int size) {
		super(size);
		shapeDrawer = ShapeDrawerUtil.createShapeDrawer(this);
	}
	
	public ShapeDrawer getShapeDrawer() {
		return shapeDrawer;
	}

}
