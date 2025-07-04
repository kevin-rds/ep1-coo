package entity.boss;

import entity.Entity;
import entity.projectiles.Projectile;
import lib.GameLib;
import util.State;

import java.util.ArrayList;
import java.util.List;

public abstract class Boss extends Entity {
    protected double rotationVelocity; // velocidades de rotação
    protected double velocity; // velocidades
    protected long explosionStart; // instantes dos inícios das explosões
    protected long explosionEnd; // instantes dos finais da explosões
    protected List<ShieldSegment> shields = new ArrayList<>();

    public Boss(double x, double y, double radius) {
        super(x, y, radius);
    }

    public void explode(long currentTime) {
        state = State.EXPLODING;
        explosionStart = currentTime;
        explosionEnd = currentTime + 500;
    }

    public void update(long delta, long currentTime) {
        if (state == State.EXPLODING && currentTime > explosionEnd) {
            setInactive();
        }

        if (state == State.ACTIVE) {
            move(delta);
            keepOnScreen();
        }
    }

    public abstract void move(long delta);
    public abstract void render(long currentTime);
    public abstract List<ShieldSegment> getShields();
    private void keepOnScreen() {
        double margin = radius;
        if (x < margin) x = margin;
        if (x > GameLib.WIDTH - margin) x = GameLib.WIDTH - margin;
        if (y < margin) y = margin;
        if (y > GameLib.HEIGHT - margin) y = GameLib.HEIGHT - margin;
    }
}
