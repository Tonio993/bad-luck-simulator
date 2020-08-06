package it.traininground.badluck.input;

import java.util.Arrays;
import java.util.HashSet;

public class InputHandler {
	
	protected InputManager input;

    public enum EventType {
        KEY_DOWN,
        KEY_UP,
        KEY_TYPED,
        TOUCH_DOWN,
        TOUCH_UP,
        TOUCH_DRAGGED,
        MOUSE_MOVED,
        SCROLLED
    }

    protected HashSet<EventType> enabledEvents;

    public InputHandler(InputManager input, EventType ...enabledEvents) {
    	this.input = input;
        if (enabledEvents == null || enabledEvents.length == 0) {
            this.enabledEvents = new HashSet<>(Arrays.asList(EventType.values()));
        } else {
            this.enabledEvents = new HashSet<>(Arrays.asList(enabledEvents));
        }
    }

    public void keyDown(int keycode) {}

    public void keyUp(int keycode) {}

    public void keyTyped(char character) {}

    public void touchDown(int screenX, int screenY, int pointer, int button) {}

    public void touchUp(int screenX, int screenY, int pointer, int button) {}

    public void touchDragged(int screenX, int screenY, int pointer) {}

    public void mouseMoved(int screenX, int screenY) {}

    public void scrolled(int amount) {}
}
