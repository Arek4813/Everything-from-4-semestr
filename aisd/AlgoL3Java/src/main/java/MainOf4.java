import java.io.InputStreamReader;
import java.util.Scanner;
@SuppressWarnings("Duplicates")


public class MainOf4 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(new InputStreamReader(System.in));

        int nodesNumb, edgesNumb, edgesCounter;
        int nodeFrom, nodeTo, edgeWeight;

        System.out.println("Give number of nodes: ");
        nodesNumb = Integer.parseInt(scanner.nextLine());
        System.out.println("Give number of edges: ");
        edgesNumb = Integer.parseInt(scanner.nextLine());

        edgesCounter = 0;
        Edge[] edgeArray = new Edge[edgesNumb];
        Graph graph = new Graph(nodesNumb);


        while (edgesCounter < edgesNumb) {
            System.out.println("Give an edge: ");
            nodeFrom = Integer.parseInt(scanner.next());
            nodeTo = Integer.parseInt(scanner.next());
            //edgeWeight = Integer.parseInt(scanner.next());
            edgeWeight=1;
            if(nodeFrom-1>nodesNumb || nodeTo-1>nodesNumb) {
                System.out.println("You should give positive weight or node which is in the graph");
            }
            else {
                System.out.println("I had got edge: from " + (nodeFrom) + " to " + (nodeTo));
                edgeArray[edgesCounter]=new Edge(nodeFrom-1, nodeTo-1, edgeWeight);
                graph.getEdges().add(edgeArray[edgesCounter]);
                edgesCounter++;
            }
        }

        long start = System.currentTimeMillis();

        Kosaraju g = new Kosaraju(graph);

        graph.calculateNeighboursListForUndirected();

        System.out.println();
        System.out.println("Following are strongly connected components in given graph: ");
        g.KosarajuAlgorithm();

        long stop = System.currentTimeMillis();
        long time = stop - start;

        System.out.println("Time of algorithm working in ms: "+time);


    }
}
