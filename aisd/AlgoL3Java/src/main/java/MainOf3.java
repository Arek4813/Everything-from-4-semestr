import java.io.InputStreamReader;
import java.util.Scanner;
@SuppressWarnings("Duplicates")


public class MainOf3 {

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
            edgeWeight = Integer.parseInt(scanner.next());
            if(nodeFrom-1>nodesNumb || nodeTo-1>nodesNumb) {
                System.out.println("You should give positive weight or node which is in the graph");
            }
            else {
                System.out.println("I had got edge: from " + (nodeFrom) + " to " + (nodeTo) + " with weight " + edgeWeight);
                edgeArray[edgesCounter]=new Edge(nodeFrom-1, nodeTo-1, edgeWeight);
                graph.getEdges().add(edgeArray[edgesCounter]);
                graph.getEdges().add(new Edge(nodeTo-1, nodeFrom-1, edgeWeight));
                edgesCounter++;
            }
        }

        graph.calculateNeighboursListForDirected();

        System.out.println();
        System.out.println();

        if(args[0].equals("-k")) {
            System.out.println("You have chosen Kruskal algorithm");
            System.out.println();
            Kruskal(graph);
        }
        else if(args[0].equals("-p")) {
            System.out.println("You have chosen Prim algorithm");
            System.out.println();
            Prim(graph,0);  //my random node is the first one
        }
        else {
            System.out.println("You should as line command give -k or -p parameter");
        }

    }

    public static void Kruskal(Graph graph){

        Kruskal kruskal = new Kruskal(graph);
        kruskal.find();

        for (Edge e:kruskal.getT()) {
            System.out.print(e + " ");
            System.out.println();
        }

        System.out.println("Amount of edges used in MST = " + kruskal.getT().size());
        System.out.println("Weight of MST = " + kruskal.getMinWeight());
    }


    public static void Prim(Graph graph, int source){

        Prim prim = new Prim();
        prim.calculate(graph,source);

        System.out.println();
        for (Edge e: prim.getT()) {
            System.out.print(e + " ");
            System.out.println();
        }
        System.out.println("Amount of edges used in MST = " + prim.getT().size());
        System.out.println("Weight of MST = " + prim.getMinWeight(graph));
    }
}
