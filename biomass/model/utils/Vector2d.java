package biomass.model.utils;

/**
 * @author candysansores
 *
 */
public class Vector2d {
    public double x;
    public double y;

    public Vector2d() {
        this.x = 0.0;
        this.y = 0.0;
    }

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d(Vector2d v) {
        this.x = v.x;
        this.y = v.y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2d v) {
        this.x = v.x;
        this.y = v.y;
    }

    public void add(Vector2d v) {
        this.x += v.x;
        this.y += v.y;
    }

    public void sub(Vector2d v) {
        this.x -= v.x;
        this.y -= v.y;
    }

    public void scale(double s) {
        this.x *= s;
        this.y *= s;
    }

    public double dot(Vector2d v) {
        return this.x * v.x + this.y * v.y;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }
    
    public double lengthSquared() {
        return x * x + y * y;
    }
    
    public void normalize(Vector2d v) {
        double length = Math.sqrt(v.x * v.x + v.y * v.y);
        if (length > 0) {
            this.x = v.x / length;
            this.y = v.y / length;
        } else {
            this.x = 0;
            this.y = 0;
        }
    }

    public void normalize() {
        double mag = magnitude();
        if (mag > 0.0) {
            this.x /= mag;
            this.y /= mag;
        }
    }

    public double distance(Vector2d v) {
        double dx = this.x - v.x;
        double dy = this.y - v.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double distanceSquared(Vector2d v) {
        double dx = this.x - v.x;
        double dy = this.y - v.y;
        return dx * dx + dy * dy;
    }

    @Override
    public String toString() {
        return "Vector2d(" + x + ", " + y + ")";
    }
}

