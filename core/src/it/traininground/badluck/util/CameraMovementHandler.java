package it.traininground.badluck.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

import it.traininground.badluck.input.InputHandler;
import it.traininground.badluck.tiles.mapregionfilter.MapRegionFilter;

public class CameraMovementHandler {

    private final Camera camera;
    private MapRegionFilter mapRegionFilter;

    private int edgeThreshold = 80;
    private int edgeHorizontalMovement = 0;
    private int edgeVerticalMovement = 0;
    private int keyHorizontalMovement = 0;
    private int keyVerticalMovement = 0;

    private boolean isCameraDragged;
    private Vector3 cameraDraggedStart;
    private Vector3 cameraDraggedTouch;

    private int cameraSpeed = 800;

    public CameraMovementHandler(Camera camera) {
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
        if (!isCameraDragged) {
            int horizontalMovement = edgeHorizontalMovement + keyHorizontalMovement;
            int verticalMovement = edgeVerticalMovement + keyVerticalMovement;
            if (horizontalMovement != 0 || verticalMovement != 0) {
                float offset = cameraSpeed * delta * (camera.viewportWidth / GameInfo.WIDTH);
                camera.position.add(horizontalMovement * offset, verticalMovement * offset, 0);
                if (mapRegionFilter != null) {
                    mapRegionFilter.updateRegion(camera.position);
                }
            }
        }
    }

    public MapRegionFilter getMapRegionFilter() {
        return mapRegionFilter;
    }

    public void setMapRegionFilter(MapRegionFilter mapRegionFilter) {
        this.mapRegionFilter = mapRegionFilter;
    }

    public final InputHandler inputHandler = new InputHandler() {
        @Override
        public void keyDown(int keycode) {
            checkKeyCameraMovement(keycode, false);
        }

        @Override
        public void keyUp(int keycode) {
            checkKeyCameraMovement(keycode, true);
        }

        @Override
        public void touchDown(int screenX, int screenY, int pointer, int button) {
            Vector3 touchPoint = camera.unproject(new Vector3(screenX, screenY, 0));
            if (button == Input.Buttons.MIDDLE) {
                isCameraDragged = true;
                cameraDraggedStart = new Vector3(camera.position.x, camera.position.y, 0);
                cameraDraggedTouch = touchPoint;
            }
        }

        @Override
        public void touchUp(int screenX, int screenY, int pointer, int button) {
            if (button == Input.Buttons.MIDDLE) {
                isCameraDragged = false;
            }
        }

        @Override
        public void touchDragged(int screenX, int screenY, int pointer) {
            Vector3 touchPoint = camera.unproject(new Vector3(screenX, screenY, 0));
            if (isCameraDragged) {
                camera.position.set(cameraDraggedStart.sub(touchPoint.sub(cameraDraggedTouch)));
                if (mapRegionFilter != null) {
                    mapRegionFilter.updateRegion(camera.position);
                }
            }
        }

        @Override
        public void mouseMoved(int screenX, int screenY) {
            checkMousePosition(screenX, screenY);
        }

        @Override
        public void scrolled(int amount) {
            if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                camera.viewportWidth = Math.max(GameInfo.WIDTH, camera.viewportWidth + (GameInfo.WIDTH / 20f) * amount);
                camera.viewportHeight = Math.max(GameInfo.HEIGHT, camera.viewportHeight + (GameInfo.HEIGHT / 20f) * amount);
            }
        }
    };

}
