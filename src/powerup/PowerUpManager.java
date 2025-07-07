package powerup;

import entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PowerUpManager {
    private final List<PowerUp> activePowerUps = new ArrayList<>();

    public void addPowerUp(PowerUp powerUp, Player player) {
        if (!powerUp.conflictsWith(activePowerUps) && activePowerUps.stream().noneMatch(pw -> pw.conflictsWith(powerUp))) {
            powerUp.apply(player);
            activePowerUps.add(powerUp);
        }
    }

    public void update(Player player, long currentTime) {
        Iterator<PowerUp> iterator = activePowerUps.iterator();

        while (iterator.hasNext()) {
            PowerUp powerUp = iterator.next();
            powerUp.update(player, currentTime);
            if (powerUp.isExpired()) {
                iterator.remove();
            }
        }
    }
}
