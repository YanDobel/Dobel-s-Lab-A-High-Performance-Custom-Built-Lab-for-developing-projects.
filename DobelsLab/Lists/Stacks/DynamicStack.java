package Lists.Stacks;

public class DynamicStack<T> {
    private Node<T> last;
    private int cont;

    public DynamicStack() {
        this.last = null;
        this.cont = 0;
    }

    public void push(T val) {
        Node<T> node = new Node<>(val);
        if (last == null) {
            last = node;
            cont++;
            return;
        }
        node.setNext(last);
        last = node;
        cont++;
    }

    public T pop() {
        T aux = last.getVal();
        last = last.getNext();
        cont--;
        return aux;
    }

    public T peek() {
        return last.getVal();
    }

    public boolean isEmpty() {
        return cont == 0;
    }

    public void clear() {
        this.last = null;
        cont = 0;
    }
}