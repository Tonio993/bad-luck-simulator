package it.traininground.badluck.input;

import com.badlogic.gdx.InputProcessor;

import java.util.HashMap;
import java.util.HashSet;

public class InputManager implements InputProcessor {
    HashMap<InputHandler.EventType, HashSet<InputHandler>> inputEventMap;

    public InputManager() {
        inputEventMap = new HashMap<>();
        for (InputHandler.EventType eventType : InputHandler.EventType.values()) {
            inputEventMap.put(eventType, new HashSet<InputHandler>());
        }
    }

    public void bind(InputHandler inputHandler) {
        for (InputHandler.EventType eventType : inputHandler.enabledEvents) {
            inputEventMap.get(eventType).add(inputHandler);
        }
    }

    public void unbind(InputHandler inputHandler) {
        for (InputHandler.EventType eventType : inputHandler.enabledEvents) {
            inputEventMap.get(eventType).remove(inputHandler);
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        for(InputHandler inputHandler : inputEventMap.get(InputHandler.EventType.KEY_DOWN)) {
            inputHandler.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        for(InputHandler inputHandler : inputEventMap.get(InputHandler.EventType.KEY_UP)) {
            inputHandler.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        for(InputHandler inputHandler : inputEventMap.get(InputHandler.EventType.KEY_TYPED)) {
            inputHandler.keyTyped(character);
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for(InputHandler inputHandler : inputEventMap.get(InputHandler.EventType.TOUCH_DOWN)) {
            inputHandler.touchDown(screenX, screenY, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for(InputHandler inputHandler : inputEventMap.get(InputHandler.EventType.TOUCH_UP)) {
            inputHandler.touchUp(screenX, screenY, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for(InputHandler inputHandler : inputEventMap.get(InputHandler.EventType.TOUCH_DRAGGED)) {
            inputHandler.touchDragged(screenX, screenY, pointer);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        for(InputHandler inputHandler : inputEventMap.get(InputHandler.EventType.MOUSE_MOVED)) {
            inputHandler.mouseMoved(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        for(InputHandler inputHandler : inputEventMap.get(InputHandler.EventType.SCROLLED)) {
            inputHandler.scrolled(amount);
        }
        return false;
    }
}
