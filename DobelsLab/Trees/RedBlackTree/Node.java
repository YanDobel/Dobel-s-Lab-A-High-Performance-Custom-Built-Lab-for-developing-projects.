package Trees.RedBlackTree;

public class Node<T extends Comparable<T>> {
    private T data;
    private Node<T> left, right, parent;
    private boolean isRed;

    public Node(T data) {
        this.data = data;
        this.isRed = true;
    }

    public Node() {
        this.data = null;
        this.isRed = false;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isRed() {
        return isRed;
    }

    public void setRed(boolean red) {
        isRed = red;
    }
}
