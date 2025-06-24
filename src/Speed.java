public class Speed {
    public double x;
    public double y;

    public Speed(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Speed(double s) {
        this.x = s;
        this.y = s;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
