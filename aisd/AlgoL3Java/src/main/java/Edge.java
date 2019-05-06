public class Edge  implements PriorityAble<Edge> {
    private final int previous;
    private final int next;
    private  int weight;

    public Edge(int previous, int next, int weight) {
        this.previous = previous;
        this.next = next;
        this.weight = weight;

    }

    public int getPrevious() {
        return previous;
    }

    public int getNext() {
        return next;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "u= " + previous + " v =" + next + " w= " + weight;
    }


    @Override
    public int compareTo(Edge o) {
        return this.weight - o.weight;
    }

    @Override
    public int getPriority() {
        return this.weight;
    }

    @Override
    public int getValue() {
        return next;
    }

    @Override
    public void setPriority(int x) {
    this.weight = x;
    }
}