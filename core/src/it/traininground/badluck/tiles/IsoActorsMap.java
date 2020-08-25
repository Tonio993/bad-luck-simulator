package it.traininground.badluck.tiles;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import it.traininground.badluck.actor.IsoActor;
import it.traininground.badluck.util.InfoDrawer;

public class IsoActorsMap {
	
	private Map<Tile, Set<IsoActor>> actors;
	private Tile getTile = new Tile();
	
	public IsoActorsMap() {
		actors = new HashMap<>();
	}
	
	public void add(IsoActor actor) {
		Set<IsoActor> set;
		if ((set = actors.get(actor.getDrawingTile())) == null) {
			set = new HashSet<>();
			actors.put(actor.getDrawingTile(), set);
		}
		set.add(actor);
		actor.add(this);
	}
	
	public void remove(IsoActor actor) {
		Set<IsoActor> set;
		if ((set = actors.get(actor.getDrawingTile())) == null) {
			return;
		}
		set.remove(actor);
		if (set.isEmpty()) {
			actors.remove(actor.getDrawingTile());
		}
		actor.remove(this);
	}
	
	public void move(IsoActor actor, Tile next) {
		Set<IsoActor> set;
		if ((set = actors.get(actor.getDrawingTile())) == null) {
			return;
		}
		set.remove(actor);
		if (set.isEmpty()) {
			actors.remove(actor.getDrawingTile());
		}
		if ((set = actors.get(next)) == null) {
			set = new HashSet<>();
			actors.put(next, set);
		}
		set.add(actor);
	}
	
	public Set<IsoActor> get(int layer, int row, int column) {
		InfoDrawer.put("info", actors.size());
		getTile.set(layer, row, column);
		return actors.get(getTile);
	}
	
}
