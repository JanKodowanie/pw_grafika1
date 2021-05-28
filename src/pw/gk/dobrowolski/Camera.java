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
    final int WIDTH = 800;
    final int HEIGHT = 600;

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
        projector.focalLen = 500.0f;
        projector.Tx = 0.0f;
        projector.Ty = -50.0f;
        projector.Tz = 0.0f;
        projector.angleX = 0.0f;
        projector.angleY = 0.0f;
        projector.angleZ = 0.0f;
        readCubes();
    }

    public void draw() {
        background(0);
        translate(WIDTH/2, HEIGHT/2);
        noFill();
        for (Cube c : cubes) {
            Integer[] color = c.getColor();
            stroke(color[0], color[1], color[2]);
            strokeWeight(1);
            for (Edge e : c.getEdges()) {
                PVector aProjected = projector.getNormalizedProjection(e.getA());
                PVector bProjected = projector.getNormalizedProjection(e.getB());
                line(aProjected.x, aProjected.y, bProjected.x, bProjected.y);
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


}
