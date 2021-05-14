package pw.gk.jan_dobrowolski;

import processing.core.PVector;

public class Point3D {
    public float[][] coords;

    Point3D (float x, float y, float z) {
        coords = new float[4][1];
        coords[0][0] = x;
        coords[1][0] = y;
        coords[2][0] = z;
        coords[3][0] = 1.0f;
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

    public PVector getNormalizedProjection() {
        return new PVector(this.getX() / this.getN(), this.getY() / this.getN());

    }
}
