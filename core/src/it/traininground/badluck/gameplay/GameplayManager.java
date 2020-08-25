package it.traininground.badluck.gameplay;

import java.util.HashSet;

import it.traininground.badluck.actor.Actor;
import it.traininground.badluck.scenes.Scene;

public class GameplayManager {
	
	protected Scene scene;
	
	HashSet<Actor> actors;
	
	public GameplayManager(Scene scene) {
		this.actors = new HashSet<>();
		this.scene = scene;
	}

	public void add(Actor actor) {
		actors.add(actor);
	}
	
	public void remove(Actor actor) {
		actors.remove(actor);
	}
	
	public void move(float delta) {
		for (Actor actor : actors) {
			actor.move(delta);
		}
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
}
