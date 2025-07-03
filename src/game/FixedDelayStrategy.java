package game;

public class FixedDelayStrategy implements DelayStrategy {
    private final long delay;

    public FixedDelayStrategy(long delay) {
        this.delay = delay;
    }

    @Override
    public long getNextDelay() {
        return delay;
    }
}