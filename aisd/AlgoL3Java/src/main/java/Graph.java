import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {

    public int verticles;
    private ArrayList<Edge> edges;
    private List<List<Edge>> neighbourLists;

    public Graph(int verticles) {
        this.verticles = verticles;
        this.edges = new ArrayList<>();
        this.neighbourLists = new ArrayList<>();

    }

    public List<List<Edge>> getNeighbourLists() {
        return neighbourLists;
    }

    public void addEdge(Edge e) {
        edges.add(e);
    }

    public int getVerticles() {
        return verticles;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }


    public void calculateNeighboursListForDirected() {
        for (int i = 0; i < verticles; i++)
            neighbourLists.add(new LinkedList<Edge>());

        for (Edge e : edges
        ) {
            neighbourLists.get(e.getPrevious()).add(e);
        }


    }

    public void calculateNeighboursListForUndirected() {
        for (int i = 0; i < verticles; i++)
            neighbourLists.add(new LinkedList<Edge>());

        for (Edge e : edges
        ) {
            neighbourLists.get(e.getPrevious()).add(e);
            neighbourLists.get(e.getNext()).add(e);
        }


    }

}
