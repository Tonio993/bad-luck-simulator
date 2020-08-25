package it.traininground.badluck.actor;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import it.traininground.badluck.tiles.IsoActorsMap;
import it.traininground.badluck.tiles.MapManager;
import it.traininground.badluck.tiles.Tile;
import it.traininground.badluck.util.GameBatch;
import it.traininground.badluck.util.InfoDrawer;
import it.traininground.badluck.util.MathUtil;
import it.traininground.badluck.util.statistics.TimeStats;

public abstract class IsoActor implements Actor {
	
	protected MapManager map;
	
	protected float x;
	protected float y;
	
	protected float lAxis;
	protected float rAxis;
	protected float cAxis;
	
	protected Tile tile;
	protected Tile nextTile;
	protected List<Tile> path;
	
	protected Tile drawingTile;
	
	protected Vector2 direction = new Vector2(1, 0);
	
	protected float speed;
	
	protected List<IsoActorsMap> listenersMap;
	
	public IsoActor(MapManager map, Tile tile) {
		this.map = map;
		
    	this.tile = tile;
    	this.drawingTile = new Tile(tile);
    	
    	this.lAxis = tile.getLayer();
    	this.rAxis = tile.getRow();
    	this.cAxis = tile.getColumn();
    	
    	this.x = map.getRenderer().getX() + (tile.getRow() - tile.getColumn()) * (map.getRenderer().getCellWidth() / 2f);
    	this.y = map.getRenderer().getY() - (tile.getRow() + tile.getColumn()) * (map.getRenderer().getCellHeight() / 2f) + map.getRenderer().getLayerHeight() * tile.getLayer();
    	
    	listenersMap = new LinkedList<>();
	}

	public void move(float delta) {
		if (nextTile == null) {
			if (path == null || path.isEmpty()) {
				return;
			}
			nextTile = path.remove(0);
		}
		if (!TimeStats.started("move")) {
			TimeStats.start("move");
		}
		float step = speed * delta;
		while (nextTile != null) {
			Vector3 vector = new Vector3(nextTile.getLayer() - lAxis, nextTile.getRow() - rAxis, nextTile.getColumn() - cAxis);
			if (step >= vector.len()) {
				tile = nextTile;
				lAxis = nextTile.getLayer();
				rAxis = nextTile.getRow();
				cAxis = nextTile.getColumn();
				nextTile = path != null && !path.isEmpty() ? path.remove(0) : null;
				step -= vector.len();
			} else {
				Vector3 moveVector = new Vector3(vector);
				moveVector.setLength(step);
				lAxis += moveVector.x;
				rAxis += moveVector.y;
				cAxis += moveVector.z;
				break;
			}
		}
        
    	float nextX = map.getRenderer().getX() + (rAxis - cAxis) * (map.getRenderer().getCellWidth() / 2f);
    	float nextY = map.getRenderer().getY() - (rAxis + cAxis) * (map.getRenderer().getCellHeight() / 2f) + map.getRenderer().getLayerHeight() * lAxis;
    	
    	direction.set(MathUtil.round(nextX - x, 2), MathUtil.round(nextY - y, 2));
    	direction.setLength(1);
    	
    	x = nextX;
    	y = nextY;
    	
    	if (nextTile == null) {
    		InfoDrawer.put("movement", TimeStats.stopGetAndReset("move"));
    	}
    	
    	Tile nextDrawingTile = new Tile(Math.round(getlAxis()), Math.round(getrAxis()), Math.round(getcAxis()));
    	if (!drawingTile.equals(nextDrawingTile)) {
    		for (IsoActorsMap listener : listenersMap) {
    			listener.move(this, nextDrawingTile);
    		}
    	}
    	drawingTile.set(nextDrawingTile);

	}
	
	public abstract void draw(GameBatch batch, float delta);
	
	public void add(IsoActorsMap listener) {
		listenersMap.add(listener);
	}
	
	public void remove(IsoActorsMap listener) {
		listenersMap.remove(listener);
	}
	
	public MapManager getMap() {
		return map;
	}

	public void setMap(MapManager map) {
		this.map = map;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getlAxis() {
		return lAxis;
	}

	public void setlAxis(float lAxis) {
		this.lAxis = lAxis;
	}

	public float getrAxis() {
		return rAxis;
	}

	public void setrAxis(float rAxis) {
		this.rAxis = rAxis;
	}

	public float getcAxis() {
		return cAxis;
	}

	public void setcAxis(float cAxis) {
		this.cAxis = cAxis;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public Tile getNextTile() {
		return nextTile;
	}

	public void setNextTile(Tile nextTile) {
		this.nextTile = nextTile;
	}

	public List<Tile> getPath() {
		return path;
	}

	public void setPath(List<Tile> path) {
		this.path = path;
	}

	public Tile getDrawingTile() {
		return drawingTile;
	}

	public void setDrawingTile(Tile drawingTile) {
		this.drawingTile = drawingTile;
	}

	public Vector2 getDirection() {
		return direction;
	}

	public void setDirection(Vector2 direction) {
		this.direction = direction;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
