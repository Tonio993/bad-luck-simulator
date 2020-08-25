package it.traininground.badluck.tiles;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import it.traininground.badluck.actor.IsoActor;

public class IsoActorsMap {
	
	private MapManager map;
	
	private Map<Tile, Set<IsoActor>> actors;
	
	public IsoActorsMap(MapManager map) {
		this.map = map;
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
		return actors.get(map.getTiles().get(layer, row, column));
	}

	public MapManager getMap() {
		return map;
	}

	public void setMap(MapManager map) {
		this.map = map;
	}
	
}
