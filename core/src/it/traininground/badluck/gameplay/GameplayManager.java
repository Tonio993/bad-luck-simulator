package it.traininground.badluck.gameplay;

import java.util.HashSet;

import it.traininground.badluck.actor.Actor;

public class GameplayManager {
	
	HashSet<Actor> actors;
	
	public GameplayManager(HashSet<Actor> actors) {
		super();
		this.actors = actors;
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
	
}
