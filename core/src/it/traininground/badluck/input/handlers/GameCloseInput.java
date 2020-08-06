package it.traininground.badluck.input.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import it.traininground.badluck.GameMain;
import it.traininground.badluck.input.InputHandler;
import it.traininground.badluck.input.InputManager;

public class GameCloseInput extends InputHandler {

    public GameCloseInput(InputManager input) {
        super(input, EventType.KEY_DOWN);
    }

    @Override
    public void keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            ((GameMain) Gdx.app.getApplicationListener()).closeApplication();
        }
    }
}
