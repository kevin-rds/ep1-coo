package graphics;

import game.Renderable;
import game.Updatable;
import game.context.GameContext;
import lib.GameLib;
import util.State;

public class Explosion implements Renderable, Updatable {
    private double x, y;
    private long startTime;
    private long duration;
    private State state;

    public Explosion() {
        this.state = State.INACTIVE;
    }

    public void start(long currentTime, double x, double y, long duration) {
        this.x = x;
        this.y = y;
        this.startTime = currentTime;
        this.duration = duration;
        this.state = State.ACTIVE;
    }

    public void update(GameContext context) {
        if (isActive() && context.getCurrentTime() > startTime + duration) {
            this.state = State.INACTIVE;
        }
    }

    public void render(GameContext context) {
        if (isActive()) {
            double alpha = (double) (context.getCurrentTime() - startTime) / duration;
            GameLib.drawExplosion(x, y, alpha);
        }
    }
    public boolean isActive() {
        return state == State.ACTIVE;
    }
}
