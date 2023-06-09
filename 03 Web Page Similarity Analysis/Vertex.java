import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Vertex implements Comparable<Vertex>, Serializable {

    private String name;
    private double distance = Double.MAX_VALUE;
    private List<Edge> listOfEdges = new ArrayList<Edge>();
    private Vertex source;
    private boolean beenVisited;

    public Vertex (String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return this.distance;
    }

    public void addEdge(Edge edge) {
        this.listOfEdges.add(edge);
    }

    public List<Edge> getListOfEdges() {
        return this.listOfEdges;
    }

    public void setSource(Vertex source) {
        this.source = source;
    }

    public Vertex getSource() {
        return this.source;
    }

    public void setVisited(boolean beenVisited) {
        this.beenVisited = beenVisited;
    }

    public boolean getVisited() {
        return this.beenVisited;
    }

    public int compareTo(Vertex vertex) {
        return Double.compare(this.distance, vertex.distance);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
