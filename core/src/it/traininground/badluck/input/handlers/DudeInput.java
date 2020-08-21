package it.traininground.badluck.input.handlers;

import java.util.List;

import com.badlogic.gdx.Input.Buttons;

import it.traininground.badluck.actor.Dude;
import it.traininground.badluck.input.InputHandler;
import it.traininground.badluck.input.InputManager;
import it.traininground.badluck.scenes.GameplayScene;
import it.traininground.badluck.tiles.MapManager;
import it.traininground.badluck.tiles.MapSelection;
import it.traininground.badluck.tiles.Tile;
import it.traininground.badluck.tiles.TilesMapRenderer;
import it.traininground.badluck.util.pathfind.TilePathFindAStar;

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
			List<Tile> path = new TilePathFindAStar(map.getTiles()).findPath(dude.getTile(), selection.getHover().add(1, 0, 0));
			if (path != null) {
				path.remove(0);
			}
			dude.setPath(path);
		}
	}
}
