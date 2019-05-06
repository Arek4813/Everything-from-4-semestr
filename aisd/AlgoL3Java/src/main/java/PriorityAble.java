public interface PriorityAble <T> extends Comparable<T>{
        int getPriority();
        int getValue();
        void setPriority(int x);

        @Override
        int compareTo(T o);
}
