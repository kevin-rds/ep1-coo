package graphics;

import lib.GameLib;
import util.State;

public class Explosion {
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

    public void update(long currentTime) {
        if (isActive() && currentTime > startTime + duration) {
            this.state = State.INACTIVE;
        }
    }

    public void render(long currentTime) {
        if (isActive()) {
            double alpha = (double) (currentTime - startTime) / duration;
            GameLib.drawExplosion(x, y, alpha);
        }
    }
    public boolean isActive() {
        return state == State.ACTIVE;
    }
}
