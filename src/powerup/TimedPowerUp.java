package powerup;

import entity.Player;

public abstract class TimedPowerUp extends PowerUp {
    protected long expireAt; // ms
    private long updatedAt; // ms

    public TimedPowerUp(long currentTime, long duration) {
        this.expireAt = currentTime + duration;
        this.updatedAt = currentTime;
    }

    @Override
    public void update(Player player, long currentTime) {
        updatedAt = currentTime;
        if (isExpired()) {
            onExpire(player);
        }
    }

    @Override
    public boolean isExpired() {
        return updatedAt >= expireAt;
    }

    protected abstract void onExpire(Player player);
}
