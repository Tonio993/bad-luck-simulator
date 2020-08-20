package it.traininground.badluck.input.handlers;

import com.badlogic.gdx.math.Vector3;

import it.traininground.badluck.input.InputHandler;
import it.traininground.badluck.input.InputManager;
import it.traininground.badluck.scenes.GameplayScene;
import it.traininground.badluck.tiles.MapManager;
import it.traininground.badluck.tiles.TileType;
import it.traininground.badluck.tiles.TilesMapRenderer;

public class MapSelectionHandler extends InputHandler {
	
	protected GameplayScene scene;
	protected MapManager map;
	protected TilesMapRenderer renderer;

	public MapSelectionHandler(InputManager input) {
		super(input);

		scene = (GameplayScene) input.getScene();
		map = scene.getMap();
		renderer = map.getRenderer();
	}

	private void selectHighlightedTile(int screenX, int screenY) {
		int layer = map.getRegion().getUpperLayer();
		int row;
		int column;
		while (layer >= map.getRegion().getLowerLayer()) {
			float currentLayerHeight = layer * renderer.getLayerHeight();
			Vector3 cameraPoint = scene.getCamera().getMain().unproject(new Vector3(screenX, screenY + currentLayerHeight, 0));
			float gridProjectedY = (renderer.getY() - cameraPoint.y) / renderer.getCellHeight();
			float gridProjectedX = (renderer.getX() - cameraPoint.x) / renderer.getCellWidth();
			row = (int) Math.floor(gridProjectedY - gridProjectedX);
			column = (int) Math.floor(gridProjectedY + gridProjectedX);
			if (row >= 0 && row < map.getTiles().getRows() && column >= 0 && column < map.getTiles().getColumns()
					&& map.getTiles().tile(layer, row, column) != TileType.EMPTY) {
				map.getSelection().setHover(layer, row, column);
				return;
			}
			layer--;
		}
		map.getSelection().unsetHover();
	}

	@Override
	public void mouseMoved(int screenX, int screenY) {
		selectHighlightedTile(screenX, screenY);
	}

}
