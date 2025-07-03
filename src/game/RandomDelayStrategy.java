package game;

public class RandomDelayStrategy implements DelayStrategy {
    private final long delay;

    public RandomDelayStrategy(long delay) {
        this.delay = delay;
    }

    @Override
    public long getNextDelay() {
        return delay + (long)(Math.random() * delay);
    }
}
