package pw.gk.dobrowolski;

import processing.core.PVector;

import static processing.core.PApplet.sin;
import static processing.core.PApplet.cos;

public class ProjectionUtils {
    public float focalLen;
    public float Tx;
    public float Ty;
    public float Tz;
    public float angleX;
    public float angleY;
    public float angleZ;

    public PVector getNormalizedProjection(Point3D point) {
        float[][] res =  MatrixMultiplier.multiplyMatrices(getFinalTransform(), point.coords);
        Point3D pointTransformed = new Point3D(res);
        return new PVector(pointTransformed.getX() / pointTransformed.getN(), pointTransformed.getY() / pointTransformed.getN());
    }

    private float[][] getFinalTransform() {
        float[][] tmp = MatrixMultiplier.multiplyMatrices(getRotMatrix(), getTranslationMatrix());

        return MatrixMultiplier.multiplyMatrices(tmp, getProjectionMatrix());
    }

    private float[][] getRotMatrix() {
        float[][] rotX = {
            {1.0f, 0.0f, 0.0f, 0.0f},
            {0.0f, cos(angleX), -sin(angleX), 0.0f},
            {0.0f, sin(angleX), cos(angleX), 0.0f},
            {0.0f, 0.0f, 0.0f, 1.0f}
        };
        float[][] rotY = {
            {cos(angleY), 0.0f, sin(angleY), 0.0f},
            {0.0f, 1.0f, 0.0f, 0.0f},
            {-sin(angleY), 0.0f, cos(angleY), 0.0f},
            {0.0f, 0.0f, 0.0f, 1.0f}
        };
        float[][] rotZ = {
            {cos(angleZ), -sin(angleZ), 0.0f, 0.0f},
            {sin(angleZ), cos(angleZ), 0.0f, 0.0f},
            {0.0f, 0.0f, 1.0f, 0.0f},
            {0.0f, 0.0f, 0.0f, 1.0f}
        };

        return MatrixMultiplier.multiplyMatrices(MatrixMultiplier.multiplyMatrices(rotX, rotY), rotZ);
    }

    private float[][] getTranslationMatrix() {
        float[][] mT = {
            {1.0f, 0.0f, 0.0f, Tx},
            {0.0f, 1.0f, 0.0f, Ty},
            {0.0f, 0.0f, 1.0f, Tz},
            {0.0f, 0.0f, 0.0f, 1.0f}
        };

        return mT;
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
}
