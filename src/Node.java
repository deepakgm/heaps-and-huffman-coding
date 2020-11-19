

public class Node implements Comparable<Node> {
    private String value;
    private int frequency;
    private Node left;
    private Node right;
    private Node parent;

    public Node(String value, int frequency) {
        this.value = value;
        this.frequency = frequency;
    }

    public Node(String value, int frequency, Node left, Node right) {
        this.value = value;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
        this.left.parent=this;
        this.right.parent=this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public int compareTo(Node node) {
        return this.frequency-node.frequency;
    }
}
