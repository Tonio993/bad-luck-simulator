package it.traininground.badluck.scenes;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;

import it.traininground.badluck.GameMain;
import it.traininground.badluck.actor.Dude;
import it.traininground.badluck.input.handlers.CameraMoveInput;
import it.traininground.badluck.input.handlers.DudeInput;
import it.traininground.badluck.input.handlers.GameCloseInput;
import it.traininground.badluck.input.handlers.MapSelectionHandler;
import it.traininground.badluck.input.handlers.UpdateRegionInput;
import it.traininground.badluck.tiles.IsoActorsMap;
import it.traininground.badluck.tiles.MapManager;
import it.traininground.badluck.tiles.MapRegionFilter;
import it.traininground.badluck.tiles.MapRegionFilterImpl;
import it.traininground.badluck.tiles.MapSelection;
import it.traininground.badluck.tiles.TileType;
import it.traininground.badluck.tiles.TilesMap;
import it.traininground.badluck.tiles.TilesMapBuilder;
import it.traininground.badluck.tiles.TilesMapRenderer;
import it.traininground.badluck.tiles.TilesMapRendererImpl;
import it.traininground.badluck.util.GameInfo;
import it.traininground.badluck.util.InfoDrawer;

public class GameplayScene extends Scene {

    private MapManager map;

    public GameplayScene(GameMain game) throws IOException, ClassNotFoundException {
        super(game);

        map = new MapManager(this);

        TilesMap tiles = new TilesMapBuilder(100, 100, 100).setBaseLayer(0).build();
		tiles.get(1, 3, 3).setType(TileType.PLAIN);
		tiles.get(1, 3, 2).setType(TileType.DOWN_NORTH);
		tiles.get(1, 2, 2).setType(TileType.DOWN_NORTH_WEST);
		tiles.get(1, 2, 3).setType(TileType.DOWN_WEST);
		tiles.get(1, 2, 4).setType(TileType.DOWN_SOUTH_WEST);
		tiles.get(1, 3, 4).setType(TileType.DOWN_SOUTH);
		tiles.get(1, 4, 4).setType(TileType.DOWN_SOUTH_EAST);
		tiles.get(1, 4, 3).setType(TileType.DOWN_EAST);
		tiles.get(1, 4, 2).setType(TileType.DOWN_NORTH_EAST);
		tiles.get(1, 8,  8).setType(TileType.PLAIN);
		for (int i = 1; i < 20; i++) {
			tiles.get(i, i, 9).setType(TileType.PLAIN);
		}
		for (int i = 20; i < 40; i++) {
			tiles.get(i - 1, 39 - i, 10).setType(TileType.PLAIN);
		}
		map.setTiles(tiles);
		
        TilesMapRenderer renderer = new TilesMapRendererImpl(map, 64, 32, 32);
        renderer.setX(GameInfo.WIDTH/2);
        renderer.setY(GameInfo.HEIGHT/2);
        map.setRenderer(renderer);

        MapRegionFilter region = new MapRegionFilterImpl(map);
        map.setRegion(region);
        
        MapSelection selection = new MapSelection(map);
        map.setSelection(selection);
        
        IsoActorsMap actors = new IsoActorsMap(map);
        map.setActors(actors);

        input.bind(new CameraMoveInput(input));
        input.bind(new UpdateRegionInput(input));
        input.bind(new GameCloseInput(input));
        input.bind(new MapSelectionHandler(input));
        
        Dude dude = new Dude(map, tiles.get(1, 0, 0));
        input.bind(new DudeInput(input, dude, Buttons.LEFT));
        actors.add(dude);
        gameplay.add(dude);
        
        region.setVisibleLayer(10);

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
        
        gameplay.move(delta);

        map.draw(game.getBatch(), delta);

        InfoDrawer.draw(game.getBatch());

        game.getBatch().end();


        InfoDrawer.put("fps", Gdx.graphics.getFramesPerSecond());
        InfoDrawer.put("java heap", String.format("%.02f mb", Gdx.app.getJavaHeap() / 1048576f));
        InfoDrawer.put("native heap", String.format("%.02f mb", Gdx.app.getNativeHeap() / 1048576f));
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
