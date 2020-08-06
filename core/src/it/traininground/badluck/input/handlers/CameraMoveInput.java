package it.traininground.badluck.input.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

import it.traininground.badluck.camera.CameraMovementHandler;
import it.traininground.badluck.input.InputHandler;
import it.traininground.badluck.input.InputManager;
import it.traininground.badluck.scenes.GameplayScene;
import it.traininground.badluck.util.GameInfo;

public class CameraMoveInput extends InputHandler {
	
    private Vector3 cameraDraggedStart;
    private Vector3 cameraDraggedTouch;
	
	GameplayScene scene;
	Camera camera;
	CameraMovementHandler cameraMove;
	
	boolean dragged;

	public CameraMoveInput(InputManager input) {
		super(input, EventType.KEY_DOWN, EventType.KEY_UP, EventType.TOUCH_DOWN, EventType.TOUCH_UP, EventType.TOUCH_DRAGGED, EventType.MOUSE_MOVED, EventType.SCROLLED);
		scene = (GameplayScene) input.getScene();
		camera = scene.getCamera().getMain();
		cameraMove = scene.getCamera().getCameraMove();
	}

	@Override
	public void keyDown(int keycode) {
		cameraMove.checkKeyCameraMovement(keycode, false);
	}

	@Override
	public void keyUp(int keycode) {
		cameraMove.checkKeyCameraMovement(keycode, true);
	}

	@Override
	public void touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 touchPoint = camera.unproject(new Vector3(screenX, screenY, 0));
		if (button == Input.Buttons.MIDDLE) {
			cameraMove.setEnabled(false);
			cameraDraggedStart = new Vector3(camera.position.x, camera.position.y, 0);
			cameraDraggedTouch = touchPoint;
			dragged = true;
		}
	}

	@Override
	public void touchUp(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.MIDDLE) {
			cameraMove.setEnabled(true);
			dragged = false;
		}
	}

	@Override
	public void touchDragged(int screenX, int screenY, int pointer) {
		Vector3 touchPoint = camera.unproject(new Vector3(screenX, screenY, 0));
		if (dragged) {
			camera.position.set(cameraDraggedStart.sub(touchPoint.sub(cameraDraggedTouch)));
			scene.getMap().getRegion().updateRegion(scene.getMap());
		}
	}

	@Override
	public void mouseMoved(int screenX, int screenY) {
		cameraMove.checkMousePosition(screenX, screenY);
	}

	@Override
	public void scrolled(int amount) {
		if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
			camera.viewportWidth = Math.max(GameInfo.WIDTH, camera.viewportWidth + (GameInfo.WIDTH / 20f) * amount);
			camera.viewportHeight = Math.max(GameInfo.HEIGHT, camera.viewportHeight + (GameInfo.HEIGHT / 20f) * amount);
		}
	}

}
