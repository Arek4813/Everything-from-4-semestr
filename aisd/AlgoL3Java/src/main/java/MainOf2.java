import java.io.InputStreamReader;
import java.util.Scanner;

@SuppressWarnings("Duplicates")

public class MainOf2 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(new InputStreamReader(System.in));

        int nodesNumb, edgesNumb, startingNode, edgesCounter;
        int nodeFrom, nodeTo, edgeWeight;

        System.out.println("Give number of nodes: ");
        nodesNumb = Integer.parseInt(scanner.nextLine());
        System.out.println("Give number of edges: ");
        edgesNumb = Integer.parseInt(scanner.nextLine());

        edgesCounter = 0;
        Edge[] edgeArray = new Edge[edgesNumb];
        Graph graph = new Graph(nodesNumb);
        int[] lengthArray = new int[nodesNumb];
        int[] previousArray = new int[nodesNumb];
        MyPrioQueue myPrioQueue = new MyPrioQueue(nodesNumb);

        while (edgesCounter < edgesNumb) {
            System.out.println("Give an edge: ");
            nodeFrom = Integer.parseInt(scanner.next());
            nodeTo = Integer.parseInt(scanner.next());
            edgeWeight = Integer.parseInt(scanner.next());
            if(edgeWeight<0 || nodeFrom-1>nodesNumb || nodeTo-1>nodesNumb) {
                System.out.println("You should give positive weight or node which is in the graph");
            }
            else {
                System.out.println("I had got edge: from " + (nodeFrom) + " to " + (nodeTo) + " with weight " + edgeWeight);
                edgeArray[edgesCounter]=new Edge(nodeFrom-1, nodeTo-1, edgeWeight);
                graph.getEdges().add(edgeArray[edgesCounter]);
                edgesCounter++;
            }
        }

        graph.calculateNeighboursListForDirected();

        System.out.println("Give starting node: ");
        startingNode = Integer.parseInt(scanner.next());

        if(startingNode<=0 || startingNode>nodesNumb) {
            System.out.println("Give real starting node");
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < nodesNumb; i++) {
            lengthArray[i] = Integer.MAX_VALUE;
            previousArray[i] = -1;
            myPrioQueue.insert(i, lengthArray[i]); //adding to priority queue
        }

        lengthArray[startingNode-1]=0;
        previousArray[startingNode-1]=0;

        Element e;

        myPrioQueue.priority(startingNode-1, lengthArray[startingNode-1]);

        while(!myPrioQueue.empty()) {
            e = myPrioQueue.pop();
            if(!myPrioQueue.empty()) {
                for (Edge edge : graph.getNeighbourLists().get(e.getValue())) {
                    if (lengthArray[edge.getNext()] > lengthArray[edge.getPrevious()] + edge.getWeight()) {
                        lengthArray[edge.getNext()] = lengthArray[edge.getPrevious()] + edge.getWeight();
                        previousArray[edge.getNext()] = edge.getPrevious()+1;
                    }
                    myPrioQueue.priority(edge.getNext(), lengthArray[edge.getNext()]);
                }
            }
        }

        long stop = System.currentTimeMillis();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Time in ms: " + (stop - start));

        print(lengthArray, previousArray);

    }

    private static void print(int[] d, int[] p) {

        System.out.println("Node, min length to this node from source, and previous node for this node:");

        for(int i=0; i<d.length; i++) {
            int tmp=i+1;
            System.out.println("N:"+tmp+" , ML:"+d[i]+" , PN:"+p[i]);
        }

    }

}
