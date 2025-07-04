package entity.boss;

import entity.Entity;
import lib.GameLib;

import java.awt.*;

public class ShieldSegment extends Entity {

    private double baseAngle;

    ShieldSegment(double x, double y, double radius, double baseAngle) {
        super(x, y, radius);
        this.baseAngle = baseAngle;
    }

    public void render() {
        GameLib.setColor(Color.GREEN);
        GameLib.drawCircle(x, y, radius);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getBaseAngle() {
        return baseAngle;
    }
}