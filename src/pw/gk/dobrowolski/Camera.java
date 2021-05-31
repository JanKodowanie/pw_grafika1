package pw.gk.dobrowolski;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Camera extends PApplet {

    List<Cube> cubes;
    ProjectionUtils projector;
    final int WIDTH = 1600;
    final int HEIGHT = 900;
    final float INIT_FOCAL_LEN = 500.0f;
    final float MIN_FOCAL_LEN = 100.0f;
    final float MAX_FOCAL_LEN = 3000.0f;
    final float FOCAL_STEP = -100.0f;
    final float TRANSLATION_STEP = 5.0f;
    final float ANGLE_STEP = (float) Math.PI / 60;

    public static void main(String[] args) {
        String[] processingArgs = {"Camera"};
        Camera camera = new Camera();
        PApplet.runSketch(processingArgs, camera);
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        projector = new ProjectionUtils();
        projector.focalLen = INIT_FOCAL_LEN;
        readCubes();
    }

    public void draw() {
        background(0);
        translate(WIDTH/2, HEIGHT/2);
        scale(1, -1);
        noFill();
        for (Cube c : cubes) {
            Integer[] color = c.getColor();
            stroke(color[0], color[1], color[2]);
            strokeWeight(1);
            for (Edge e : c.getEdges()) {
                PVector aProjected = projector.getNormalizedProjection(e.getA());
                PVector bProjected = projector.getNormalizedProjection(e.getB());

                if (aProjected == null || bProjected == null) {
                    continue;
                }

                line(aProjected.x, aProjected.y, bProjected.x, bProjected.y);
            }
        }
    }

    public void keyPressed() {

        if (key == CODED) {
            switch (keyCode) {
                case LEFT:
                    moveCamLeft();
                    break;
                case RIGHT:
                    moveCamRight();
                    break;
                case UP:
                    moveCamForward();
                    break;
                case DOWN:
                    moveCamBackwards();
                    break;
                case CONTROL:
                    moveCamDown();
                    break;
                case SHIFT:
                    moveCamUp();
                    break;
            }
        } else {
            switch (key) {
                case 'q':
                    rollCameraClockwise();
                    break;
                case 'e':
                    rollCameraCounterClockwise();
                    break;
                case 'a':
                    yawCameraLeft();
                    break;
                case 'd':
                    yawCameraRight();
                    break;
                case 'w':
                    pitchCameraUp();
                    break;
                case 's':
                    pitchCameraDown();
                    break;
                case '-':
                    increaseZoom();
                    break;
                case '=':
                    decreaseZoom();
                    break;
            }
        }
    }

    private void readCubes() {
        cubes = new ArrayList<>();
        String[] lines = loadStrings("cubes.txt");

        int n = lines.length;
        if (n == 0) {
            throw new RuntimeException("File is empty");
        }

        for (int i = 0; i < n; ) {
            if (lines[i].length() !=0 && lines[i].charAt(0) == '#') {
                int k = i + 1;
                Edge[] edges = new Edge[12];
                for(int j = 0; j < 12; j++) {
                    edges[j] = readEdge(lines[k]);
                    k++;
                }
                Integer[] color = Arrays.stream(split(lines[k], ',')).map(Integer::parseInt).toArray(Integer[]::new);
                cubes.add(new Cube(edges, color));
                i = k + 1;
            }
            else {
                i++;
            }
        }
    }

    private Edge readEdge(String line) {
        String[] points = split(line,  ";");
        List<Float> p1coords = Arrays.stream(split(points[0], ',')).map(Float::parseFloat).collect(Collectors.toList());
        List<Float> p2coords = Arrays.stream(split(points[1], ',')).map(Float::parseFloat).collect(Collectors.toList());

        return new Edge(new Point3D(p1coords.get(0), p1coords.get(1), p1coords.get(2)),
                new Point3D(p2coords.get(0), p2coords.get(1), p2coords.get(2)));
    }

    private void moveCamLeft() {
        projector.translatePointsByX(cubes, TRANSLATION_STEP);
    }

    private void moveCamRight() {
        projector.translatePointsByX(cubes, (-1) * TRANSLATION_STEP);
    }

    private void moveCamDown() {
        projector.translatePointsByY(cubes, TRANSLATION_STEP);
    }

    private void moveCamUp() {
        projector.translatePointsByY(cubes, (-1) * TRANSLATION_STEP);
    }

    private void moveCamForward() {
        projector.translatePointsByZ(cubes, (-1) * TRANSLATION_STEP);
    }

    private void moveCamBackwards() {
        projector.translatePointsByZ(cubes,  TRANSLATION_STEP);
    }

    private void rollCameraClockwise() {
        projector.rotatePointsByZ(cubes, (-1) * ANGLE_STEP);
    }

    private void rollCameraCounterClockwise() {
        projector.rotatePointsByZ(cubes, ANGLE_STEP);
    }

    private void yawCameraLeft() {
        projector.rotatePointsByY(cubes, ANGLE_STEP);
    }

    private void yawCameraRight() {
        projector.rotatePointsByY(cubes, (-1) * ANGLE_STEP);
    }

    private void pitchCameraUp() {
        projector.rotatePointsByX(cubes, (-1) * ANGLE_STEP);
    }

    private void pitchCameraDown() {
        projector.rotatePointsByX(cubes, ANGLE_STEP);
    }

    private void increaseZoom() {
        projector.focalLen += FOCAL_STEP;
        if (projector.focalLen < MIN_FOCAL_LEN) {
            projector.focalLen = MIN_FOCAL_LEN;
        }
    }

    private void decreaseZoom() {
        projector.focalLen -= FOCAL_STEP;
        if (projector.focalLen > MAX_FOCAL_LEN) {
            projector.focalLen = MAX_FOCAL_LEN;
        }
    }

}
