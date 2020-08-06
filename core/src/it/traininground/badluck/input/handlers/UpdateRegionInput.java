package it.traininground.badluck.input.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import it.traininground.badluck.input.InputHandler;
import it.traininground.badluck.input.InputManager;
import it.traininground.badluck.scenes.GameplayScene;
import it.traininground.badluck.tiles.MapManager;

public class UpdateRegionInput extends InputHandler {

	public UpdateRegionInput(InputManager input) {
		super(input, EventType.SCROLLED, EventType.TOUCH_DOWN, EventType.MOUSE_MOVED);
	}

	@Override
	public void scrolled(int amount) {
		if (!Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
			MapManager map = ((GameplayScene) input.getScene()).getMap(); 
			map.getRegion().setVisibleLayer((Math.min(Math.max(0, map.getRegion().getVisibleLayer() - amount), map.getTiles().getLayers() - 1)));
            map.getRegion().updateRegion(map);
		}
	}

	@Override
	public void touchDown(int screenX, int screenY, int pointer, int button) {
		//TODO
//		if (isHighlightedTile()) {
//			switch (button) {
//			case Input.Buttons.LEFT:
//				map.tiles.tile(highlightedLayer + 1, highlightedRow, highlightedColumn, TerrainType.PLAIN);
//				break;
//			case Input.Buttons.RIGHT:
//				map.tiles.tile(highlightedLayer, highlightedRow, highlightedColumn, TerrainType.EMPTY);
//				break;
//			}
//		}
	}

	@Override
	public void mouseMoved(int screenX, int screenY) {
		//TODO
//		selectHighlightedTile(screenX, screenY);
	}
};