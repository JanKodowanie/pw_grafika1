package pw.gk.jan_dobrowolski;

public class Edge {
    private Point3D a;
    private Point3D b;

    public Edge(Point3D a, Point3D b) {
        this.setA(a);
        this.setB(b);
    }

    public Point3D getA() {
        return a;
    }

    public void setA(Point3D a) {
        this.a = a;
    }

    public Point3D getB() {
        return b;
    }

    public void setB(Point3D b) {
        this.b = b;
    }
}
