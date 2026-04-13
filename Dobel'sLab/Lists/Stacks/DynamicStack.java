package Lists.Stacks;

public class DynamicStack<T> {
    private Node last;

    public void push(Node<T> val) {
        add(val);
    }
    private void add(Node<T> val) {
        val.setNext(last);
        last = val;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        Node aux = last;
        last = last.getNext();
        return (T) aux;
    }
}
