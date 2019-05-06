import java.util.ArrayList;


public class Kruskal {

    private Graph graph;
    private Structure priorityQueue;
    private ArrayList<Edge> T = new ArrayList<>();
    private UnionFind unionFind = new UnionFind();

    public Kruskal(Graph graph) {
        this.graph = graph;
        priorityQueue = new Structure(Edge.class);
        for (Edge e : graph.getEdges()) {
            priorityQueue.insert(e);
        }
    }

    public void find() {
        Edge e;

        for (int i = 0; i < graph.getVerticles(); i++) {
            unionFind.getSetsList().add(unionFind.MakeSet(i)); //making one-element sets
        }

        while (!priorityQueue.isEmpty() && !check(graph)) {
            e = (Edge) priorityQueue.pop();
            if (unionFind.find(e.getPrevious()) != unionFind.find(e.getNext())) {
                T.add(e);
                unionFind.union(unionFind.find(e.getPrevious()), unionFind.find(e.getNext())); //connecting two nodes through that edge
            }
            //if condition is not made, we forget about that edge
        }
    }

    public int getMinWeight() {
        if (check(graph)) {
            int i = 0;
            for (Edge e : T
            ) {
                i += e.getWeight();

            }
            return i;
        }
        return 0;
    }

    public ArrayList<Edge> getT() {
        return T;
    }

    private boolean check(Graph graph) {  //checking if we have not a spanning tree yet
        if (unionFind.find(0).size() != graph.getVerticles())
            return false;

        return true;
    }

}