package it.traininground.badluck.camera;

import com.badlogic.gdx.Input;

import it.traininground.badluck.util.GameInfo;

public class CameraMovementHandler {

    private final CameraManager camera;

    private int edgeThreshold = 80;
    private int edgeHorizontalMovement = 0;
    private int edgeVerticalMovement = 0;
    private int keyHorizontalMovement = 0;
    private int keyVerticalMovement = 0;

    private boolean enabled;

    private int cameraSpeed = 800;

    public CameraMovementHandler(CameraManager camera) {
        super();
        this.camera = camera;
    }

    public void checkMousePosition(int screenX, int screenY) {
        if (screenX < edgeThreshold) {
            edgeHorizontalMovement = -1;
        } else if (screenX > GameInfo.WIDTH - edgeThreshold) {
            edgeHorizontalMovement = 1;
        } else {
            edgeHorizontalMovement = 0;
        }
        if (screenY < edgeThreshold) {
            edgeVerticalMovement = 1;
        } else if (screenY > GameInfo.HEIGHT - edgeThreshold) {
            edgeVerticalMovement = -1;
        } else {
            edgeVerticalMovement = 0;
        }
    }

    public void checkKeyCameraMovement(int key, boolean released) {
        switch (key) {
            case Input.Keys.UP:
                keyVerticalMovement = released ? 0 : 1;
                break;
            case Input.Keys.DOWN:
                keyVerticalMovement = released ? 0 : -1;
                break;
            case Input.Keys.LEFT:
                keyHorizontalMovement = released ? 0 : -1;
                break;
            case Input.Keys.RIGHT:
                keyHorizontalMovement = released ? 0 : 1;
                break;
        }
    }

    public void moveCamera(float delta) {
        if (enabled) {
            int horizontalMovement = edgeHorizontalMovement + keyHorizontalMovement;
            int verticalMovement = edgeVerticalMovement + keyVerticalMovement;
            if (horizontalMovement != 0 || verticalMovement != 0) {
                float offset = cameraSpeed * delta * (camera.getMain().viewportWidth / GameInfo.WIDTH);
                camera.getMain().position.add(horizontalMovement * offset, verticalMovement * offset, 0);
            }
            
        }
    }

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
