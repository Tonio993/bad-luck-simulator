package it.traininground.badluck.actor;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import it.traininground.badluck.tiles.MapManager;
import it.traininground.badluck.tiles.Tile;

public class IsoActor {
	
	protected MapManager map;
	
	protected float x;
	protected float y;
	
	protected float lAxis;
	protected float rAxis;
	protected float cAxis;
	
	protected Tile tile;
	protected Tile nextTile;
	protected List<Tile> path;
	
	protected Vector2 direction = new Vector2(1, 0);
	
	protected float speed;
	
	public IsoActor(MapManager map) {
		this.map = map;
	}

	public void move(float delta) {
		if (nextTile == null) {
			if (path == null || path.isEmpty()) {
				return;
			}
			nextTile = path.remove(0);
		}
        
		Vector3 vector = new Vector3(nextTile.getLayer() - lAxis, nextTile.getRow() - rAxis, nextTile.getColumn() - cAxis);
		Vector3 moveVector = new Vector3(vector);
		moveVector.setLength(speed * delta);
		if (vector.len() <= moveVector.len()) {
			tile = nextTile;
			nextTile = path != null && !path.isEmpty() ? path.remove(0) : null;
		}
        
        lAxis += moveVector.x;
        rAxis += moveVector.y;
        cAxis += moveVector.z;
        
    	float nextX = map.getRenderer().getX() + (rAxis - cAxis) * (map.getRenderer().getCellWidth() / 2f);
    	float nextY = map.getRenderer().getY() - (rAxis + cAxis) * (map.getRenderer().getCellHeight() / 2f) + map.getRenderer().getLayerHeight() * lAxis;
    	
    	direction.set(nextX - x, nextY - y);
    	direction.nor();
    	
    	x = nextX;
    	y = nextY;

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
