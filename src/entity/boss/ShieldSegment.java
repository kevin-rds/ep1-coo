package entity.boss;

import entity.Entity;
import lib.GameLib;
import util.State;

import java.awt.*;

public class ShieldSegment extends Entity {

    private final double baseAngle;
    private long delayedExplosionStart = -1;

    ShieldSegment(double x, double y, double radius, double baseAngle) {
        super(x, y, radius);
        this.baseAngle = baseAngle;
    }

    public void update(long currentTime) {
        if (state == State.EXPLODING) {
            explosion.update(currentTime);
            if (!explosion.isActive()) {
                setInactive();
            }
        }

        if (delayedExplosionStart != -1 && currentTime > delayedExplosionStart) {
            explode(currentTime);
        }
    }

    public void render(long currentTime) {
        if (state == State.EXPLODING) {
            explosion.render(currentTime);
        }

        if (state == State.ACTIVE) {
            GameLib.setColor(Color.GREEN);
            GameLib.drawCircle(x, y, radius);
        }
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getBaseAngle() {
        return baseAngle;
    }

    public void delayedExplode(long currentTime) {
        delayedExplosionStart = currentTime;
    }

    public void explode(long currentTime) {
        super.explode(currentTime, 700);
        delayedExplosionStart = -1;
    }

    public long getExplosionEnd() {
        return explosionEnd;
    }
}