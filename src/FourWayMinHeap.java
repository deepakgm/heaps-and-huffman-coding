

public class FourWayMinHeap<E extends Comparable<E>> implements MinHeap<E> {
    private Object[] arr;
    private int size;
    private static final int d = 4;

    public FourWayMinHeap(int size) {
        this.arr = new Object[size];
    }

    private boolean isValidIndex(int index) {
        return (index < this.size);
    }

    @SuppressWarnings("unchecked")
    private E get(int index) {
        if (isValidIndex(index)) return (E) this.arr[index];
        else return null;
    }

    private E getRoot() {
        return get(0);
    }

    private void insertAt(int index, E elem) {
        this.arr[index] = elem;
    }

    private void insertInTheEnd(E elem) {
        insertAt(size, elem);
    }

    private int getParentIndex(int index) {
        return ((index + d - 1) / d) - 1;
    }

    private E getParent(int index) {
        int parentIndex = getParentIndex(index);
        if (parentIndex > -1) return get(parentIndex);
        else return null;
    }

    private int getLeftChildIndex(int index) {
        return d * index + 1;
    }

    private void bottomUpHeapify(int index) {
        E child = get(index);
        E parent = getParent(index);
        if (parent != null && child.compareTo(parent) < 0) {
            int parentIndex = getParentIndex(index);
            insertAt(parentIndex, child);
            insertAt(index, parent);
            bottomUpHeapify(parentIndex);
        }
    }

    @SuppressWarnings("unchecked")
    private void topDownHeapify(int index) {
        E parent = get(index);
        int leftChildIndex=getLeftChildIndex(index);
        if (isValidIndex(leftChildIndex)) {
            int minChildIndex = leftChildIndex;
            E minChild= (E) this.arr[minChildIndex];
            for(int i=1;i<d;i++){
                int childIndex=leftChildIndex+i;
                if(isValidIndex(childIndex)){
                    E child= (E) this.arr[childIndex];
                    if(child.compareTo(minChild) < 0 ){
                        minChild=child;
                        minChildIndex=childIndex;
                    }
                }else break;
            }
            if (parent.compareTo(minChild) > 0) {
                insertAt(minChildIndex, parent);
                insertAt(index, minChild);
                topDownHeapify(minChildIndex);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size < 1;
    }

    @Override
    public E getMin() {
        if (isEmpty())
            return null;
        E min = getRoot();
        insertAt(0, get(this.size - 1));
        insertAt(this.size - 1, null);
        this.size--;
        topDownHeapify(0);
        return min;
    }

    @Override
    public void insert(E elem) {
        insertInTheEnd(elem);
        this.size++;
        bottomUpHeapify(this.size - 1);
    }

    @Override
    public int getSize() {
        return this.size;
    }
}
