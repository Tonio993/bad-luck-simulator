package it.traininground.badluck.actor;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Dude implements Disposable {

    private final TextureAtlas idleDude;
    private final Animation<TextureAtlas.AtlasRegion> idleDudeAnimation;

    private final TextureAtlas moveDude;
    private final Animation<TextureAtlas.AtlasRegion> moveDudeAnimation;

    private Animation<TextureAtlas.AtlasRegion> currentDudeAnimation;

    private float x;
    private float y;

    private float nextX;
    private float nextY;

    private float speed = 600;

    private boolean flipped = false;

    private float currentFrameTime;

    public Dude() {
        this(0, 0, 0, 0);
    }
    public Dude(float x, float y) {
        this(x, y, x, y);
    }

    public Dude(float x, float y, float nextX, float nextY) {
        idleDude = new TextureAtlas("dude/idle.atlas");
        idleDudeAnimation = new Animation<>(1/10f, idleDude.getRegions());

        moveDude = new TextureAtlas("dude/move.atlas");
        moveDudeAnimation = new Animation<>(1/10f, moveDude.getRegions());

        currentDudeAnimation = idleDudeAnimation;

        this.x = x;
        this.y = y;
        this.nextX = nextX;
        this.nextY = nextY;
        currentFrameTime = 0;
    }

    public void draw(SpriteBatch batch, float delta) {
        currentFrameTime += delta;

        Vector2 v = new Vector2(nextX - x, nextY - y);
        float xOffset = x + MathUtils.cosDeg(v.angle()) * speed * delta;
        float yOffset = y + MathUtils.sinDeg(v.angle()) * speed * delta;

        if (nextX > x) flipped = false;
        if (nextX < x) flipped = true;

        if (x != nextX || y != nextY) {
            switchAnimation(moveDudeAnimation);
            x = x > nextX ? Math.max(nextX, xOffset) : Math.min(nextX, xOffset);
            y = y > nextY ? Math.max(nextY, yOffset) : Math.min(nextY, yOffset);
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
                y - dudeFrame.getRegionHeight() / 10f + 50,
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

    public float getNextX() {
        return nextX;
    }

    public void setNextX(float nextX) {
        this.nextX = nextX;
    }

    public float getNextY() {
        return nextY;
    }

    public void setNextY(float nextY) {
        this.nextY = nextY;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
