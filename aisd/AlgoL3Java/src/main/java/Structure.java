import java.lang.reflect.Array;
import java.util.Arrays;

// kolejka dalej zbuggowana...

@SuppressWarnings("Duplicates")
public class Structure<T extends Comparable<T>> {

    private PriorityAble<T>[] array;
    private int heapSize;

    public Structure(Class<T> type) {
        array = (PriorityAble<T>[]) Array.newInstance(type, 1);
        heapSize = 0;
    }

    private int left(int i) {
        return 2 * i + 1;
    }

    private int right(int i) {
        return 2 * i + 2;
    }

    private int parent(int i) {
        return (int) Math.floor((i - 1) / 2);
    }

    void clear() {
        this.array = Arrays.copyOf(array, 1);
        array[0] = null;
        heapSize = 0;
    }


    private void swap(int e, int k) {
        T item;
        item = (T) array[e];
        array[e] = array[k];
        array[k] = (PriorityAble<T>) item;
    }


    public void insert(PriorityAble<T> e) {
        if (heapSize < array.length) {
            array[heapSize] = e;
            int i = heapSize;
            heapSize++;

            while (i != 0 && array[parent(i)].getPriority() > array[i].getPriority()) {
                swap(parent(i), i);
                i = parent(i);
            }
        } else {
            this.array = Arrays.copyOf(array, 2 * size());
            insert(e);
        }
    }

    public void decreaseKey(int i, int newVal) {
        if (array[i].getPriority() > newVal) {
            array[i].setPriority(newVal);
            while (i != 0 && array[parent(i)].getPriority() > array[i].getPriority()) {
                swap(parent(i), i);
                i = parent(i);
            }
        }
    }


    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int size() {
        return heapSize;
    }

    public PriorityAble<T> top() {
        if (!isEmpty())
            return array[0];
        return null;
    }


    public void print() {
        int k = 0;
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " " + i + " ");
            if (i % Math.pow(2, k) == 0) {
                System.out.println();
                k++;
            }
        }
        System.out.println();
        System.out.println();
    }

    public PriorityAble<T> pop() {
        if (isEmpty())
            return null;
        if (heapSize == 1) {
            T item = (T) array[0];
            clear();
            return (PriorityAble<T>) item;
        }

        T element = (T) array[0];
        heapSize--;
        array[0] = array[heapSize];
        heapifyDown(0);
        return (PriorityAble<T>) element;
    }

    private void heapifyDown(int i) {
        int l = left(i);
        int r = right(i);
        int smallest = i;
        if (l < heapSize && array[l].getPriority() < array[i].getPriority())
            smallest = l;
        if (r < heapSize && array[r].getPriority() < array[smallest].getPriority())
            smallest = r;
        if (smallest != i) {
            swap(i, smallest);
            heapifyDown(smallest);
        }

    }


    public void changePriority(int x, int p) {
        if (!isEmpty()) {
           for (int i = 0 ; i <heapSize;i++)
               if (array[i].getValue()==x && array[i].getPriority()>p)
                   array[i].setPriority(p);
        }
    }



    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < array.length; i++)
            ret.append(array[i] + " ");
        return String.valueOf(ret);
    }
}

