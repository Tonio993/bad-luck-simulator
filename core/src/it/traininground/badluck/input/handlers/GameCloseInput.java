package it.traininground.badluck.input.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import it.traininground.badluck.GameMain;
import it.traininground.badluck.input.InputHandler;

public class GameCloseInput extends InputHandler {

    public GameCloseInput() {
        super(EventType.KEY_DOWN);
    }

    @Override
    public void keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            ((GameMain) Gdx.app.getApplicationListener()).closeApplication();
        }
    }
}
