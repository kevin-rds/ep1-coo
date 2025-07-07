package entity.boss;

import entity.Entity;
import game.context.GameContext;
import lib.GameLib;
import util.State;

import java.awt.Color;

public class ShieldSegment extends Entity {

    private final double baseAngle;
    private long delayedExplosionStart = -1;

    ShieldSegment(double x, double y, double radius, double baseAngle) {
        super(x, y, radius);
        this.baseAngle = baseAngle;
    }

    @Override
    public void update(GameContext context) {
        long currentTime = context.getCurrentTime();
        if (state == State.EXPLODING) {
            explosion.update(context);
            if (!explosion.isActive()) {
                setInactive();
            }
        }

        if (delayedExplosionStart != -1 && currentTime > delayedExplosionStart) {
            explode(currentTime);
        }
    }

    @Override
    public void render(GameContext context) {
        if (state == State.EXPLODING) {
            explosion.render(context);
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
}