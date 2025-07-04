package entity;

import util.State;

public abstract class Entity {
    protected double x, y; // coordenadas x e y
    protected double radius;
    protected State state;

    public Entity(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.state = State.ACTIVE;
    }

    public boolean collidesWith(Entity other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (this.radius + other.radius) * 0.8;
    }

    public boolean isActive() {
        return state == State.ACTIVE;
    }

    public void setInactive() {
        this.state = State.INACTIVE;
    }

    public void setActive() {
        this.state = State.ACTIVE;
    }

    public State getState() {
        return state;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getRadius() { return radius; }
}
