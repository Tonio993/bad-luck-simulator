package it.traininground.badluck.tiles;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TileDrawerManager {
	
	public static final int LOW_PRIORITY = 10;
	public static final int MID_PRIORITY = 20;
	public static final int HIGH_PRIORITY = 30;
	
	Map<Integer, List<TileDrawer>> drawerSets;
	Map<TileDrawer, Integer> drawers;
	
	public TileDrawerManager() {
		drawerSets = new TreeMap<>();
		drawers = new HashMap<>();
	}
	
	public void set(int priority, TileDrawer drawer) {
		if (drawers.containsKey(drawer)) {
			removeDrawer(drawer);
		}
		addDrawer(drawer, priority);
	}
	
	public void remove(TileDrawer drawer) {
		removeDrawer(drawer);
	}
	
	private void addDrawer(TileDrawer drawer, int priority) {
		if (drawers.containsKey(drawer)) {
			return;
		}
		if (!drawerSets.containsKey(priority)) {
			drawerSets.put(priority, new LinkedList<>());
		}
		drawerSets.get(priority).add(drawer);
		drawers.put(drawer, priority);
	}
	
	private void removeDrawer(TileDrawer drawer) {
		Integer priority = drawers.get(drawer);
		if (priority == null) {
			return;
		}
		List<TileDrawer> drawerList = drawerSets.get(priority);
		drawerList.remove(drawer);
		if (drawerList.isEmpty()) {
			drawerSets.remove(priority);
		}
		drawers.remove(drawer);
	}
	
	public Iterable<List<TileDrawer>> getDrawerSets() {
		return drawerSets.values();
	}
}
