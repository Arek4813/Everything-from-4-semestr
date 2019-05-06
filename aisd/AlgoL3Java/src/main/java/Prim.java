import java.util.*;


public class Prim {
    private UnionFind unionFind = new UnionFind();
    private ArrayList<Edge> T = new ArrayList<>();  // przechowywane krawedzie
    private HashSet hashSet = new HashSet();

    public void calculate(Graph graph, int source) {

        if (source <= graph.verticles) {

            Structure priorityQueue = new Structure(Edge.class);

            for (Edge e : graph.getNeighbourLists().get(source)
            ) {
                priorityQueue.insert(e);       // queue contains all edges connecting source with its neighbours
            }


            for (int i = 0; i < graph.verticles; i++) {
                unionFind.getSetsList().add(unionFind.MakeSet(i));
            }

            Edge e;

            while (!priorityQueue.isEmpty() && !check(graph)) {

                e = (Edge) priorityQueue.pop();
                if (unionFind.find(source) != unionFind.find(e.getPrevious())) {
                    unionFind.union(unionFind.find(source), unionFind.find(e.getPrevious()));
                    for (Edge k : graph.getNeighbourLists().get(e.getPrevious())
                    ) {
                        priorityQueue.insert(k);

                    }
                    T.add(e);
                }
                else if (unionFind.find(source) != unionFind.find(e.getNext())) {
                    unionFind.union(unionFind.find(source), unionFind.find(e.getNext()));
                    for (Edge k : graph.getNeighbourLists().get(e.getNext())
                    ) {
                        priorityQueue.insert(k);


                    }
                    T.add(e);
                }
            }

        }

    }


    public int getMinWeight(Graph graph) {
        if( check(graph)) {
            int i = 0;
            for (Edge e : T
            ) {
                i += e.getWeight();

            }
            return i;
        }
        return 0;
    }

    private boolean check(Graph graph) {
      if (unionFind.find(0).size()!=graph.getVerticles())
        return false;

        return true;
    }

    public ArrayList<Edge> getT() {
        return T;
    }
}

