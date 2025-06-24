public abstract class Enemy {

    public State state;
    public Coordinates coordinates;
    public double speed;
    public double rotationSpeed;
    public double radius;
    public double angle;
    public Explosion explosion;

    public Enemy(Coordinates coordinates, Speed speed, double radius) {
        this.state = State.ACTIVE;
        this.coordinates = coordinates;
        this.radius = radius;
        this.explosion = new Explosion(0, 0);
    }

    public double getRadius() {
        return this.radius;
    };

}
