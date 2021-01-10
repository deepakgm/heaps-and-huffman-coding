import java.util.Stack;

public class PairingMinHeap<E extends Comparable<E>> implements MinHeap<E>  {

    private PairingHeapNode<E> root;
    private int size=0;

    private PairingHeapNode<E> meld(PairingHeapNode<E> node1, PairingHeapNode<E> node2) {
        if(node1==null) return node2;
        if(node2==null) return node1;

        if(node1.getData().compareTo(node2.getData()) < 0){
            if(node1.getChild()!=null)
                node1.getChild().setLeft(node2);
            node2.setLeft(node1);
            node2.setRight(node1.getChild());
            node1.setChild(node2);
            node1.setLeft(null);
            node1.setRight(null);
            return node1;
        }else{
            if(node2.getChild()!=null)
                node2.getChild().setLeft(node1);
            node1.setLeft(node2);
            node1.setRight(node2.getChild());
            node2.setChild(node1);
            node2.setLeft(null);
            node2.setRight(null);
            return node2;
        }
    }

    private PairingHeapNode<E> twoPassMerge(PairingHeapNode<E> child) {
        if (child==null || child.getRight()==null) return child;
        Stack<PairingHeapNode<E>> stack=new Stack<>();
        PairingHeapNode<E> next=null;
        while (child!=null){
            if(child.getRight()==null){
                stack.push(child);
                break;
            }else {
                next=child.getRight().getRight();
                child=meld(child,child.getRight());
                stack.push(child);
                child=next;
            }
        }
        child=stack.pop();
        while (!stack.isEmpty()){
            child=meld(child,stack.pop());
        }
        return child;
    }



    @Override
    public boolean isEmpty() {
        return root==null;
    }

    @Override
    public E removeMin() {
        if(isEmpty()) return null;

        E min = root.getData();
        this.root=twoPassMerge(root.getChild());
        this.size--;
        return min;
    }

    @Override
    public void insert(E elem) {
        PairingHeapNode<E> newNode=new PairingHeapNode<>(elem);
        root=meld(root,newNode);
        this.size++;
    }

    @Override
    public int getSize() {
        return this.size;
    }
}
