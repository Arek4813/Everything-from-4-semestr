public class Element implements PriorityAble<Element> {

    int value;
    int priority;

    public Element(int value, int priority) {
        this.value = value;
        this.priority = priority;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Element o) {
        return this.priority - o.getPriority();
    }
}
