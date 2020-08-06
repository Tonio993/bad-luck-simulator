package it.traininground.badluck.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import it.traininground.badluck.scenes.Scene;
import it.traininground.badluck.util.GameInfo;

public class CameraManager {
	
	protected Scene scene;
	
    protected OrthographicCamera main;
    protected Viewport viewport;
    protected CameraMovementHandler cameraMove;
	
	public CameraManager(Scene scene) {
		this.scene = scene;
		
        main = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        main.position.set(GameInfo.WIDTH/2f, GameInfo.HEIGHT/2f, 0);
        viewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT);
        cameraMove = new CameraMovementHandler(this);
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public OrthographicCamera getMain() {
		return main;
	}

	public void setMain(OrthographicCamera main) {
		this.main = main;
	}

	public Viewport getViewport() {
		return viewport;
	}

	public void setViewport(Viewport viewport) {
		this.viewport = viewport;
	}

	public CameraMovementHandler getCameraMove() {
		return cameraMove;
	}

	public void setCameraMove(CameraMovementHandler cameraMove) {
		this.cameraMove = cameraMove;
	}
}
