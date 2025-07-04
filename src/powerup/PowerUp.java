package powerup;

import entity.Player;

import java.util.Collections;
import java.util.List;

public abstract class PowerUp {
    public abstract void apply(Player player);
    public abstract void update(Player player, long currentTime);
    public abstract boolean isExpired();
    public boolean conflictsWith(List<PowerUp> powerUps) {
        return false;
    }
    public boolean conflictsWith(PowerUp powerUp) {
        return conflictsWith(Collections.singletonList(powerUp));
    };
}
