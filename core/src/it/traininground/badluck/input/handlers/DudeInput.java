package it.traininground.badluck.input.handlers;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector2;

import it.traininground.badluck.actor.Dude;
import it.traininground.badluck.input.InputHandler;
import it.traininground.badluck.input.InputManager;
import it.traininground.badluck.scenes.GameplayScene;
import it.traininground.badluck.tiles.MapManager;
import it.traininground.badluck.tiles.MapSelection;
import it.traininground.badluck.tiles.TilesMapRenderer;

public class DudeInput extends InputHandler {

	protected GameplayScene scene;
	protected MapManager map;
	protected TilesMapRenderer renderer;
	protected MapSelection selection;
	
	private Dude dude;

	public DudeInput(InputManager input, Dude dude) {
		super(input, EventType.TOUCH_DOWN);
		this.dude = dude;

		scene = (GameplayScene) input.getScene();
		map = scene.getMap();
		renderer = map.getRenderer();
		selection = map.getSelection();
	}

	@Override
	public void touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Buttons.LEFT && selection.isHover()) {
			Vector2 position = map.getTilePosition(selection.getHover());
			dude.setNextX(position.x);
			dude.setNextY(position.y);
		}
	}
}
