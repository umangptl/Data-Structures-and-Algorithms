import java.io.Serializable;

public class Edge implements Serializable {

    private double weight;
    private Vertex src;
    private Vertex point;

    public Edge(double weight, Vertex src, Vertex point) {
        this.weight = weight;
        this.src = src;
        this.point = point;
    }

    public double getWeight() {
        return this.weight;
    }

    public Vertex getSrc() {
        return this.src;
    }

    public Vertex getPoint() {
        return this.point;
    }
}