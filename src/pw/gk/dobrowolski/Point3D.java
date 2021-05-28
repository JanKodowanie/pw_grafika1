package pw.gk.dobrowolski;

public class Point3D {
    public float[][] coords;

    Point3D (float x, float y, float z) {
        coords = new float[4][1];
        coords[0][0] = x;
        coords[1][0] = y;
        coords[2][0] = z;
        coords[3][0] = 1.0f;
    }

    Point3D (float[][] coords) {
        this.coords = coords;
    }

    public float getX() {
        return coords[0][0];
    }

    public float getY() {
        return coords[1][0];
    }

    public float getZ() {
        return coords[2][0];
    }

    public float getN() {
        return coords[3][0];
    }

    @Override
    public String toString() {
        return String.format("x: %.2f, y: %.2f, z: %.2f", getX(), getY(), getZ());
    }
}
