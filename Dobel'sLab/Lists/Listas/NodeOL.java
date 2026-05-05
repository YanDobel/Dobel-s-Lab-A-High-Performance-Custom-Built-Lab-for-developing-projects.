package Lists.Listas;

class NodeOL<T extends Comparable<T>> {
    private T data;
    private NodeOL<T> next;
    private NodeOL<T> prev;

    public NodeOL(T data) {
        this.next = null;
        this.prev = null;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public NodeOL<T> getNext() {
        return next;
    }

    public void setNext(NodeOL<T> next) {
        this.next = next;
    }

    public NodeOL<T> getPrev() {
        return prev;
    }

    public void setPrev(NodeOL<T> prev) {
        this.prev = prev;
    }
}
