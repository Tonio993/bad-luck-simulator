package it.traininground.badluck.actor;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

import it.traininground.badluck.tiles.MapManager;
import it.traininground.badluck.tiles.Tile;

public class Dude extends IsoActor implements Disposable {

    private final TextureAtlas idleDude;
    private final Animation<TextureAtlas.AtlasRegion> idleDudeAnimation;

    private final TextureAtlas moveDude;
    private final Animation<TextureAtlas.AtlasRegion> moveDudeAnimation;

    private Animation<TextureAtlas.AtlasRegion> currentDudeAnimation;

    private boolean flipped = false;

    private float currentFrameTime;

    public Dude(MapManager map, Tile tile) {
    	super(map);
    	this.speed = 5;
    	this.map = map;
    	this.tile = tile;
    	
    	this.lAxis = tile.getLayer();
    	this.rAxis = tile.getRow();
    	this.cAxis = tile.getColumn();
    	
    	this.x = map.getRenderer().getX() + (tile.getRow() - tile.getColumn()) * (map.getRenderer().getCellWidth() / 2f);
    	this.y = map.getRenderer().getY() - (tile.getRow() + tile.getColumn()) * (map.getRenderer().getCellHeight() / 2f) + map.getRenderer().getLayerHeight() * tile.getLayer();
    	
        idleDude = new TextureAtlas("dude/idle.atlas");
        idleDudeAnimation = new Animation<>(1/10f, idleDude.getRegions());

        moveDude = new TextureAtlas("dude/move.atlas");
        moveDudeAnimation = new Animation<>(1/10f, moveDude.getRegions());

        currentDudeAnimation = idleDudeAnimation;

        currentFrameTime = 0;
        
    }

    public void draw(SpriteBatch batch, float delta) {
        currentFrameTime += delta;
        
        move(delta);

        if (direction.x > 0) flipped = false;
        if (direction.x < 0) flipped = true;

        if (nextTile != null && (lAxis != nextTile.getLayer() || rAxis != nextTile.getRow() || cAxis != nextTile.getColumn())) {
            switchAnimation(moveDudeAnimation);
        } else {
            switchAnimation(idleDudeAnimation);
        }

        TextureAtlas.AtlasRegion dudeFrame = currentDudeAnimation.getKeyFrame(currentFrameTime, true);
        if ((flipped && !dudeFrame.isFlipX()) || (!flipped && dudeFrame.isFlipX())) {
            dudeFrame.flip(true, false);
        }
        batch.draw(
                dudeFrame,
                x - dudeFrame.getRegionWidth() / 10f,
                y - dudeFrame.getRegionHeight() / 10f,
                dudeFrame.getRegionWidth() / 5f,
                dudeFrame.getRegionHeight() / 5f);
    }

    private void switchAnimation(Animation<TextureAtlas.AtlasRegion> animation) {
        if (currentDudeAnimation != animation) {
            currentDudeAnimation = animation;
            currentFrameTime = 0;
        }
    }

    @Override
    public void dispose() {
        idleDude.dispose();
        moveDude.dispose();
    }

}
