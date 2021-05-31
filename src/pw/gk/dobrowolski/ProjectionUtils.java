package pw.gk.dobrowolski;

import processing.core.PVector;

import java.util.List;

import static processing.core.PApplet.sin;
import static processing.core.PApplet.cos;

public class ProjectionUtils {

    public float focalLen;


    public PVector getNormalizedProjection(Point3D point) {
        Point3D pointTransformed = projectPoint(point);
        if (pointTransformed == null) {
            return null;
        }
        return new PVector(pointTransformed.getX() / pointTransformed.getN(), pointTransformed.getY() / pointTransformed.getN());
    }

    public void rotatePointsByX(List<Cube> cubes, float angleX) {
        float[][] rotX = {
                {1.0f, 0.0f, 0.0f, 0.0f},
                {0.0f, cos(angleX), -sin(angleX), 0.0f},
                {0.0f, sin(angleX), cos(angleX), 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        };

        applyRotation(cubes, rotX);
    }

    public void rotatePointsByY(List<Cube> cubes, float angleY) {

        float[][] rotY = {
                {cos(angleY), 0.0f, sin(angleY), 0.0f},
                {0.0f, 1.0f, 0.0f, 0.0f},
                {-sin(angleY), 0.0f, cos(angleY), 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        };

        applyRotation(cubes, rotY);
    }

    public void rotatePointsByZ(List<Cube> cubes, float angleZ) {

        float[][] rotZ = {
                {cos(angleZ), -sin(angleZ), 0.0f, 0.0f},
                {sin(angleZ), cos(angleZ), 0.0f, 0.0f},
                {0.0f, 0.0f, 1.0f, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        };

        applyRotation(cubes, rotZ);
    }

    public void translatePointsByX(List<Cube> cubes, float step) {
        for (Cube c : cubes) {
            for (Edge e : c.getEdges()) {
                Point3D a = e.getA();
                Point3D b = e.getB();

                a.setX(a.getX() + step);
                b.setX(b.getX() + step);
            }
        }
    }

    public void translatePointsByY(List<Cube> cubes, float step) {
        for (Cube c : cubes) {
            for (Edge e : c.getEdges()) {
                Point3D a = e.getA();
                Point3D b = e.getB();

                a.setY(a.getY() + step);
                b.setY(b.getY() + step);
            }
        }
    }

    public void translatePointsByZ(List<Cube> cubes, float step) {
        for (Cube c : cubes) {
            for (Edge e : c.getEdges()) {
                Point3D a = e.getA();
                Point3D b = e.getB();

                a.setZ(a.getZ() + step);
                b.setZ(b.getZ() + step);
            }
        }
    }

    private Point3D projectPoint(Point3D point) {
        if (point.getZ() <= (-1) * focalLen) {
            return null;
        }
        Point3D pointTransformed = new Point3D(MatrixMultiplier.multiplyMatrices(getProjectionMatrix(), point.coords));
        return pointTransformed;
    }

    private float[][] getProjectionMatrix() {
        float[][] mP = {
                {1.0f, 0.0f, 0.0f, 0.0f},
                {0.0f, 1.0f, 0.0f, 0.0f},
                {0.0f, 0.0f, 0.0f, 0.0f},
                {0.0f, 0.0f, 1/focalLen, 1.0f}
        };
        return mP;
    }

    private void applyRotation(List<Cube> cubes, float[][] rot) {
        for (Cube c : cubes) {
            for (Edge e : c.getEdges()) {
                Point3D a = e.getA();
                Point3D b = e.getB();

                a.coords = MatrixMultiplier.multiplyMatrices(rot, a.coords);
                b.coords = MatrixMultiplier.multiplyMatrices(rot, b.coords);
            }
        }
    }
}
