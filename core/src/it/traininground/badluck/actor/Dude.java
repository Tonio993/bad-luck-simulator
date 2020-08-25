package it.traininground.badluck.actor;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

import it.traininground.badluck.draw.Drawable;
import it.traininground.badluck.tiles.MapManager;
import it.traininground.badluck.tiles.Tile;
import it.traininground.badluck.util.GameBatch;

public class Dude extends IsoActor implements Disposable, Drawable {

    private final TextureAtlas idleDude;
    private final Animation<TextureAtlas.AtlasRegion> idleDudeAnimation;

    private final TextureAtlas moveDude;
    private final Animation<TextureAtlas.AtlasRegion> moveDudeAnimation;

    private Animation<TextureAtlas.AtlasRegion> currentDudeAnimation;

    private boolean flipped = false;

    private float currentFrameTime;

    public Dude(MapManager map, Tile tile) {
    	super(map, tile);
    	this.speed = 5;
    	this.map = map;
    	
        idleDude = new TextureAtlas("dude/idle.atlas");
        idleDudeAnimation = new Animation<>(1/10f, idleDude.getRegions());

        moveDude = new TextureAtlas("dude/move.atlas");
        moveDudeAnimation = new Animation<>(1/10f, moveDude.getRegions());

        currentDudeAnimation = idleDudeAnimation;

        currentFrameTime = 0;
        
    }

    public void draw(GameBatch batch, float delta) {
        currentFrameTime += delta;
        
        if (direction.x > 0) flipped = false;
        if (direction.x < 0) flipped = true;

        if (nextTile != null && (lAxis != nextTile.layer || rAxis != nextTile.row || cAxis != nextTile.column)) {
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
