public class Player implements Size {

    public State state;
    public Coordinates coordinates;
    public Speed speed;
    public double radius;
    public Explosion explosion;
    public long nextShot;

    public Player(Coordinates coordinates, Speed speed) {
        this.state = State.ACTIVE;
        this.coordinates = coordinates;
        this.speed = speed;
        this.radius = 12.0;
        this.explosion = new Explosion(0, 0);
        this.nextShot = System.currentTimeMillis();
    }

    public double getRadius() {
        return this.radius;
    };
}
