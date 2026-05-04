package Lists.Queues;

public class Node<V, P extends Comparable<P>> {
    private V value;
    private P priority;
    private Node<V, P> next;
    private Node<V, P> prev;

    public Node(V value, P priority) {
        this.next = null;
        this.prev = null;
        this.value = value;
        this.priority = priority;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public P getPriority() {
        return priority;
    }

    public void setPriority(P priority) {
        this.priority = priority;
    }

    public Node<V, P> getNext() {
        return next;
    }

    public void setNext(Node<V, P> next) {
        this.next = next;
    }

    public Node<V, P> getPrev() {
        return prev;
    }

    public void setPrev(Node<V, P> prev) {
        this.prev = prev;
    }
}
