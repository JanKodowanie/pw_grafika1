package pw.gk.jan_dobrowolski;

public class Cube {
    private Edge[] edges;
    private int[] color;

    public Cube(Edge[] edges, int[] color) {
        if (edges.length != 12)
            throw new RuntimeException("Invalid number of edges. Should be 12.");

        if (color.length != 3)
            throw new RuntimeException("Invalid length of color array. Should be 3.");

        this.edges = edges;
        this.color = color;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public void setEdges(Edge[] edges) {
        this.edges = edges;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }
}
