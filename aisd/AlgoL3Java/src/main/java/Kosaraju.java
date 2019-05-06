import java.util.LinkedList;
import java.util.Stack;

public class Kosaraju {

    private Graph graph;

    public Kosaraju(Graph graph){this.graph=graph;}

    void SecondDFS(int v, boolean visited[], Graph graph)
    {

        visited[v] = true;
        int tmp=v+1;
        System.out.print(tmp + " ");


        for (Edge e:graph.getNeighbourLists().get(v)
        ) {
            if(!visited[e.getNext()])
                SecondDFS(e.getNext(),visited,graph);
        }
    }

    Graph getTranspose()
    {
        Graph g = new Graph(graph.getVerticles());
        for (int i = 0; i < graph.getVerticles(); i++)
            g.getNeighbourLists().add(new LinkedList<Edge>());
        for (Edge e:graph.getEdges()
             ) {
            g.getNeighbourLists().get(e.getNext()).add(new Edge(e.getNext(),e.getPrevious(),e.getWeight()));

        }
        g.calculateNeighboursListForUndirected();
        return g;
    }

    void FirstDFS(int v, boolean visited[], Stack stack, Graph graph)
    {
        visited[v] = true;

        for (Edge e:graph.getNeighbourLists().get(v)
             ) {
            if(!visited[e.getNext()])
                FirstDFS(e.getNext(), visited, stack,graph);
        }
        stack.push(new Integer(v));  //pushing at stack
    }


    public void KosarajuAlgorithm()
    {
        Stack stack = new Stack();

        boolean visited[] = new boolean[graph.getVerticles()];
        for(int i = 0; i < graph.getVerticles(); i++)
            visited[i] = false;


        for (int i = 0; i < graph.getVerticles(); i++)
            if (!visited[i])
                FirstDFS(i, visited, stack, graph);   //1st DFS


        Graph graph  = getTranspose();  //graph transposition


        for (int i = 0; i < graph.getVerticles(); i++)
            visited[i] = false;


        while (!stack.empty())
        {

            int v = (int)stack.pop(); //popping form stack


            if (!visited[v])
            {
                SecondDFS(v, visited,graph);  // 2nd DFS
                System.out.println();
            }
        }
    }
}
