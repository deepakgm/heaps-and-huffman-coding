
public interface MinHeap<E extends Comparable<E>> {
    boolean isEmpty();
    E removeMin();
    int getSize();
    void insert(E elem);
}
