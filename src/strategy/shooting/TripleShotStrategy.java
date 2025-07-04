package strategy.shooting;

import entity.Player;
import entity.projectiles.PlayerProjectile;
import entity.projectiles.Projectile;

import java.awt.*;
import java.util.List;

public class TripleShotStrategy implements ShootingStrategy {
    private final int pelletCount = 5;
    private final double angleSpread = 30.0;

    @Override
    public void shoot(Player player, List<Projectile> projectiles) {
        double baseAngle = -angleSpread / 2;
        double angleStep = angleSpread / (pelletCount - 1);

        for (int i = 0; i < pelletCount; i++) {
            double angle = Math.toRadians(baseAngle + i * angleStep);
            double dx = Math.sin(angle);
            double dy = -Math.cos(angle);
            projectiles.add(new PlayerProjectile(player.getX(), player.getY(), dx, dy, Color.GREEN));
        }
    }
}
