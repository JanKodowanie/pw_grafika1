package pw.gk.dobrowolski;

public class Cube {
    private Edge[] edges;
    private Integer[] color;

    public Cube(Edge[] edges, Integer[] color) {
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

    public Integer[] getColor() {
        return color;
    }

    public void setColor(Integer[] color) {
        this.color = color;
    }
}
