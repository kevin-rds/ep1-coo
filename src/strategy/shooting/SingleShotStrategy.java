package strategy.shooting;

import entity.Player;
import entity.projectiles.PlayerProjectile;
import entity.projectiles.Projectile;

import java.awt.*;
import java.util.List;

public class SingleShotStrategy implements ShootingStrategy {
    @Override
    public void shoot(Player player, List<Projectile> projectiles) {
        projectiles.add(new PlayerProjectile(
            player.getX(),
            player.getY() - 2 * player.getRadius(),
            0.0,
            -1.0,
            Color.GREEN
        ));
    }
}
