package it.traininground.badluck.scenes;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import it.traininground.badluck.GameMain;
import it.traininground.badluck.input.handlers.CameraMoveInput;
import it.traininground.badluck.input.handlers.GameCloseInput;
import it.traininground.badluck.input.handlers.UpdateRegionInput;
import it.traininground.badluck.tiles.MapManager;
import it.traininground.badluck.tiles.MapRegionFilter;
import it.traininground.badluck.tiles.MapRegionFilterImpl;
import it.traininground.badluck.tiles.MapSelection;
import it.traininground.badluck.tiles.MapSelectionHandler;
import it.traininground.badluck.tiles.TerrainType;
import it.traininground.badluck.tiles.TilesMap;
import it.traininground.badluck.tiles.TilesMapBuilder;
import it.traininground.badluck.tiles.TilesMapRendererImpl;
import it.traininground.badluck.util.GameInfo;
import it.traininground.badluck.util.InfoDrawer;

public class GameplayScene extends Scene {

//    private Dude dude;
    private MapManager map;

    public GameplayScene(GameMain game) throws IOException, ClassNotFoundException {
        super(game);

//        FileHandle filehandle = Gdx.files.local("map/map.igm");
        TilesMap tiles;
//        if (!filehandle.exists()) {
//            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filehandle.path())));
            tiles = new TilesMapBuilder(10, 20, 20).setBaseLayer(0).build();

            tiles.tile(1, 3, 3, TerrainType.PLAIN);
            tiles.tile(1, 3, 2, TerrainType.DOWN_NORTH);
            tiles.tile(1, 2, 2, TerrainType.DOWN_NORTH_WEST);
            tiles.tile(1, 2, 3, TerrainType.DOWN_WEST);
            tiles.tile(1, 2, 4, TerrainType.DOWN_SOUTH_WEST);
            tiles.tile(1, 3, 4, TerrainType.DOWN_SOUTH);
            tiles.tile(1, 4, 4, TerrainType.DOWN_SOUTH_EAST);
            tiles.tile(1, 4, 3, TerrainType.DOWN_EAST);
            tiles.tile(1, 4, 2, TerrainType.DOWN_NORTH_EAST);

            for (int i=1; i<10; i++) {
                tiles.tile(i, 0, 9, TerrainType.PLAIN);
            }

//            oos.writeObject(tiles);
//            oos.close();
//        } else {
//            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filehandle.path())));
//            tiles = (TilesMap) ois.readObject();
//            ois.close();
//        }


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
