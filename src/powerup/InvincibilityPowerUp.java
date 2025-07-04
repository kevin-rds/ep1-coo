package powerup;

import entity.Player;
import lib.GameLib;

import java.awt.*;
import java.util.List;

public class InvincibilityPowerUp extends TimedPowerUp {

    public InvincibilityPowerUp(long currentTime, long duration) {
        super(currentTime, duration);
        setColor(Color.GREEN);
    }

    @Override
    public void apply(Player player) {
        player.setInvincible(true);
    }

    @Override
    public void update(Player player, long currentTime) {
        super.update(player, currentTime);

        double baseRadius = player.getRadius() * 2.5;
        double amplitude = player.getRadius() * 0.5;

        double timeSeconds = currentTime / 1000.0;
        double oscillation = Math.sin(timeSeconds * 2 * Math.PI); // 1 oscilacao por segundo
        double animatedRadius = baseRadius + amplitude * oscillation;

        GameLib.setColor(new Color(0, 255, 0, 128));
        GameLib.drawCircle(player.getX(), player.getY(), animatedRadius);
    }

    @Override
    protected void onExpire(Player player) {
        player.setInvincible(false);
    }

    @Override
    public boolean conflictsWith(List<PowerUp> powerUps) {
        return powerUps.stream().anyMatch(pw -> pw instanceof InvincibilityPowerUp);
    }
}
