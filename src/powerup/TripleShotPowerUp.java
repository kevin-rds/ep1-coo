package powerup;

import entity.Player;
import strategy.shooting.ShootingStrategy;
import strategy.shooting.TripleShotStrategy;

import java.awt.*;
import java.util.List;

public class TripleShotPowerUp extends TimedPowerUp {

    private ShootingStrategy previousStrategy;

    public TripleShotPowerUp(long currentTime, long duration) {
        super(currentTime, duration);
        setColor(Color.YELLOW);
    }

    @Override
    public void apply(Player player) {
        previousStrategy = player.getShootingStrategy();
        player.setShootingStrategy(new TripleShotStrategy());
    }

    @Override
    protected void onExpire(Player player) {
        player.setShootingStrategy(previousStrategy);
    }

    @Override
    public boolean conflictsWith(List<PowerUp> powerUps) {
        return powerUps.stream().anyMatch(pw -> pw instanceof TripleShotPowerUp);
    }
}
