

public class PairingHeapNode <E> {
    private E data;
    private  PairingHeapNode<E> child;
    private  PairingHeapNode<E> left;
    private  PairingHeapNode<E> right;

    public PairingHeapNode(E data) {
        this.data=data;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public PairingHeapNode<E> getChild() {
        return child;
    }

    public void setChild(PairingHeapNode<E> child) {
        this.child = child;
    }

    public PairingHeapNode<E> getLeft() {
        return left;
    }

    public void setLeft(PairingHeapNode<E> left) {
        this.left = left;
    }

    public PairingHeapNode<E> getRight() {
        return right;
    }

    public void setRight(PairingHeapNode<E> right) {
        this.right = right;
    }
}
