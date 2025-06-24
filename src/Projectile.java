public class Projectile {

    public State state;
    public Coordinates coordinates;
    public Speed speed;

    public Projectile() {
        this.state = State.INACTIVE;
    }

    public Projectile(Coordinates coordinates, Speed speed) {
        this.state = State.INACTIVE;
        this.coordinates = coordinates;
        this.speed = speed;
    }
}
