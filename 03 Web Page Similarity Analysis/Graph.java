import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<Vertex> vertexList;
    private List<Edge> edgeList;
    int disjointCount = 0;

    public Graph(List<Vertex> vertexList, List<Edge> edgeList) {
        this.vertexList = vertexList;
        this.edgeList = edgeList;
    }

    public boolean edgeSearch(Edge edge) {
        for (Edge e: edgeList) {
            if(edge.getPoint() != null && e.getSrc().getName().equals(edge.getPoint().getName())) return true;
        }
        return false;
    }

    public List<String> disjoint() {
        List<String> path = new ArrayList<String>();
        for (Edge e : edgeList) {
            if (edgeSearch(e)) {
                if (!path.contains(e.getSrc().getName())) {
                    path.add(e.getSrc().getName());
                    disjointCount ++;
                }
            }
        }
        return path;
    }

    public int getDisjointCount(){
        return disjointCount;
    }
}
