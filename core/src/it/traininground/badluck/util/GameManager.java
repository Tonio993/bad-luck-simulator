package it.traininground.badluck.util;

public class GameManager {
    private static GameManager ourInstance = new GameManager();

    private GameManager() {}

    public static GameManager getInstance() {
        return ourInstance;
    }

}
