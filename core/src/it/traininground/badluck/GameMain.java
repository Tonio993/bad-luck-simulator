package it.traininground.badluck;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.traininground.badluck.scenes.MainTestScene;
import it.traininground.badluck.scenes.MergeTestScene;

public class GameMain extends Game {
	SpriteBatch batch;

	@Override
	public void create () {
		this.batch = new SpriteBatch();
		setScreen(new MergeTestScene(this));
//		try {
//			final String VERTEX = FileUtil.readFile(FileUtil.getResourceAsStream("shader/shader.vert"));
//			final String FRAGMENT = FileUtil.readFile(FileUtil.getResourceAsStream("shader/shader.frag"));
//			ShaderProgram program = new ShaderProgram(VERTEX, FRAGMENT);
//			if (program.getLog().length() != 0) {
//				System.out.println(program.getLog());
//			}
//			batch = new SpriteBatch(1000, program);
//			setScreen(new TestScene(this));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
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
