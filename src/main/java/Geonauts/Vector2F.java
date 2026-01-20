package Geonauts;

public class Vector2F implements Cloneable {
    float x;
    float y;

    public Vector2F(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2F rotated (int degrees) {
        double radians = Math.toRadians(degrees);
        float newX = (float)(x * Math.cos(radians) - y * Math.sin(radians));
        float newY = (float)(x * Math.sin(radians) + y * Math.cos(radians));
        return new Vector2F(newX, newY);
    }

    public Vector2F scaled(float scaleFactor){
        var out = this.clone();
        out.x *= scaleFactor;
        out.y *= scaleFactor;
        return out;
    }

    @Override
    public Vector2F clone() {
        try {
            return (Vector2F) super.clone();
        } catch (CloneNotSupportedException e) {
            // Should be impossible
            throw new AssertionError(e);
        }
    }

}
