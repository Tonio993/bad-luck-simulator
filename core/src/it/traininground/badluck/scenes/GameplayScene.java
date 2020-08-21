package it.traininground.badluck.scenes;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import it.traininground.badluck.GameMain;
import it.traininground.badluck.actor.Dude;
import it.traininground.badluck.input.handlers.CameraMoveInput;
import it.traininground.badluck.input.handlers.DudeInput;
import it.traininground.badluck.input.handlers.GameCloseInput;
import it.traininground.badluck.input.handlers.MapSelectionHandler;
import it.traininground.badluck.input.handlers.UpdateRegionInput;
import it.traininground.badluck.tiles.MapManager;
import it.traininground.badluck.tiles.MapRegionFilter;
import it.traininground.badluck.tiles.MapRegionFilterImpl;
import it.traininground.badluck.tiles.MapSelection;
import it.traininground.badluck.tiles.Tile;
import it.traininground.badluck.tiles.TileType;
import it.traininground.badluck.tiles.TilesMap;
import it.traininground.badluck.tiles.TilesMapBuilder;
import it.traininground.badluck.tiles.TilesMapRendererImpl;
import it.traininground.badluck.util.GameInfo;
import it.traininground.badluck.util.InfoDrawer;
import it.traininground.badluck.util.pathfind.TilePathFindAStar;

public class GameplayScene extends Scene {

    private Dude dude;
    private MapManager map;

    public GameplayScene(GameMain game) throws IOException, ClassNotFoundException {
        super(game);

		TilesMap tiles;
		tiles = new TilesMapBuilder(100, 20, 20).setBaseLayer(0).build();

		tiles.tile(1, 3, 3, TileType.PLAIN);
		tiles.tile(1, 3, 2, TileType.DOWN_NORTH);
		tiles.tile(1, 2, 2, TileType.DOWN_NORTH_WEST);
		tiles.tile(1, 2, 3, TileType.DOWN_WEST);
		tiles.tile(1, 2, 4, TileType.DOWN_SOUTH_WEST);
		tiles.tile(1, 3, 4, TileType.DOWN_SOUTH);
		tiles.tile(1, 4, 4, TileType.DOWN_SOUTH_EAST);
		tiles.tile(1, 4, 3, TileType.DOWN_EAST);
		tiles.tile(1, 4, 2, TileType.DOWN_NORTH_EAST);
		
		tiles.tile(1, 8,  8, TileType.PLAIN);

		for (int i = 1; i < 20; i++) {
			tiles.tile(i, i, 9, TileType.PLAIN);
		}
		for (int i = 20; i < 40; i++) {
			tiles.tile(i - 1, 39 - i, 10, TileType.PLAIN);
		}

        TilesMapRendererImpl renderer = new TilesMapRendererImpl(64, 32, 32);
        renderer.setX(GameInfo.WIDTH/2);
        renderer.setY(GameInfo.HEIGHT/2);
        
        MapRegionFilter region = new MapRegionFilterImpl();
        MapSelection selection = new MapSelection();
        
        map = new MapManager(this, tiles, renderer, region, selection);
//        map.setDebugMode(true);

        input.bind(new CameraMoveInput(input));
        input.bind(new UpdateRegionInput(input));
        input.bind(new GameCloseInput(input));
        input.bind(new MapSelectionHandler(input));
        
        dude = new Dude(map, new Tile(1, 0, 5));
        input.bind(new DudeInput(input, dude));
        
        TilePathFindAStar pathfind = new TilePathFindAStar(tiles);
        System.out.println(pathfind.findPath(new Tile(1, 0, 0), new Tile(39, 0, 10)));

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.getCameraMove().moveCamera(delta);
        camera.getMain().update();

        game.getBatch().begin();
        game.getBatch().setProjectionMatrix(camera.getMain().combined);

        map.draw(game.getBatch());
        dude.draw(game.getBatch(), delta);

        InfoDrawer.draw(game.getBatch());

        game.getBatch().end();


        InfoDrawer.put("fps", Gdx.graphics.getFramesPerSecond());

    }

    @Override
    public void resize(int width, int height) {
        camera.getViewport().update(GameInfo.WIDTH, GameInfo.HEIGHT);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
//        dude.dispose();
//        try {
//            File file = new File("map/map.igm");
//            if (file.exists()) {
//                File tmpFile = new File("map/map.igm.tmp");
//                tmpFile.createNewFile();
//                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(tmpFile)));
//                oos.writeObject(map.tiles);
//                oos.close();
//                file.delete();
//                tmpFile.renameTo(file);
//                tmpFile.delete();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

	public MapManager getMap() {
		return map;
	}

	public void setMap(MapManager map) {
		this.map = map;
	}

}
