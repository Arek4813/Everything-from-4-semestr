import java.lang.reflect.Array;
import java.util.Arrays;

public class Stack<T> {

    private T[] array;
    private int size;

    public Stack(Class<T> type) {
        array = (T[]) Array.newInstance(type, 1);
        size = 0;
    }

    public void push(T element) {
        if (size < array.length) {
            array[size] = element;
            size++;
        } else {
            this.array = Arrays.copyOf(array, 2 * size);
            push(element);
        }
    }

    public boolean isEmpty() {
        if (array[0] == null) return true;
        return false;
    }


    public T pop() {
        if (size >0) {
            T element = array[size -1];
            size--;
            array[size]=null;
            return element;
        } else
            return null;

    }


}
