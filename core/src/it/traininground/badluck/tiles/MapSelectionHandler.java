package it.traininground.badluck.tiles;

import com.badlogic.gdx.math.Vector3;

import it.traininground.badluck.input.InputHandler;
import it.traininground.badluck.input.InputManager;
import it.traininground.badluck.scenes.GameplayScene;

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

	private TilePosition selectHighlightedTile(int screenX, int screenY) {
		int layer = map.region.upperLayer;
		int row;
		int column;
		while (layer >= map.region.lowerLayer) {
			float currentLayerHeight = layer * renderer.layerHeight;
			Vector3 cameraPoint = scene.getCamera().getMain().unproject(new Vector3(screenX, screenY + currentLayerHeight, 0));
			float gridProjectedY = (renderer.getY() - cameraPoint.y) / renderer.cellHeight;
			float gridProjectedX = (renderer.getX() - cameraPoint.x) / renderer.cellWidth;
			row = (int) Math.floor(gridProjectedY - gridProjectedX);
			column = (int) Math.floor(gridProjectedY + gridProjectedX);
			if (row >= 0 && row < map.tiles.rows && column >= 0 && column < map.tiles.columns
					&& map.tiles.tile(layer, row, column) != TerrainType.EMPTY) {
				return new TilePosition(layer, row, column);
			}
			layer--;
		}
		return null;
	}

	@Override
	public void mouseMoved(int screenX, int screenY) {
		map.selection.clearActive();
		TilePosition tile = selectHighlightedTile(screenX, screenY);
		if (tile != null) {
			map.selection.addActive(tile);
		}
	}

}
