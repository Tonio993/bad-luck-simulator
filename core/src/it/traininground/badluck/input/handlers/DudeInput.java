package it.traininground.badluck.input.handlers;

import java.util.List;

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
	private int button;

	public DudeInput(InputManager input, Dude dude, int button) {
		super(input, EventType.TOUCH_DOWN);
		this.dude = dude;
		this.button = button;

		scene = (GameplayScene) input.getScene();
		map = scene.getMap();
		renderer = map.getRenderer();
		selection = map.getSelection();
	}

	@Override
	public void touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == this.button && selection.isHover()) {
			List<Tile> path = new TilePathFindAStar(map.getTiles()).findPath(dude.getTile(), selection.getHover().add(1, 0, 0));
			if (path != null) {
				path.remove(0);
				if (!path.isEmpty() && path.get(0).equals(dude.getNextTile())) {
					path.remove(0);
				}
			}
			dude.setPath(path);
		}
	}
}
