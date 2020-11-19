
public interface MinHeap<E extends Comparable<E>> {
    public boolean isEmpty();
    public E getMin();
    public int getSize();
    public void insert(E elem);
}
