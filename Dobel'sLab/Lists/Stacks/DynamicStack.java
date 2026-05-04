package Lists.Stacks;

import java.util.*;

public class DynamicStack<T> implements Iterable<T> {
    private Node<T> last;
    private int cont;
    private transient int modCount;

    public DynamicStack() {
        this.last = null;
        this.cont = 0;
    }

    public void push(T val) {
        Node<T> node = new Node<>(val);
        node.setNext(last);
        last = node;
        cont++;
        modCount++;
    }

    public T pop() {
        if (isEmpty()) throw new NoSuchElementException("(!) Empty Stack (!)");
        T aux = last.getVal();
        last = last.getNext();
        cont--;
        modCount++;
        return aux;
    }

    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("(!) Empty Stack (!)");
        return last.getVal();
    }

    public boolean isEmpty() {
        return cont == 0;
    }

    public void clear() {
        this.last = null;
        cont = 0;
        modCount++;
    }

    public int indexOf(T val) {
        if (isEmpty()) throw new NoSuchElementException("(!) Empty Stack (!)");
        int index = 0;
        Node<T> aux = last;
        while (aux != null) {
            if (Objects.equals(aux.getVal(), val)) {
                return index;
            }
            aux = aux.getNext();
            index++;
        }
        return -1;
    }

    public List<T> toList() {
        if (isEmpty()) throw new NoSuchElementException("(!) Empty Stack (!)");
        List<T> list = new ArrayList<>(cont);
        for (T val : this) list.add(val);
        return list;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        if (isEmpty()) throw new NoSuchElementException("(!) Empty Stack (!)");
        T[] array = (T[]) new Object[cont];
        Node<T> aux = last;
        for (int i = 0; i < cont; i++) {
            array[i] = aux.getVal();
            aux = aux.getNext();
        }
        return array;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = last;
            private final int expectedModCount = modCount;

            public void checkComodification() {
                if (modCount != expectedModCount) throw new ConcurrentModificationException();
            }

            @Override
            public boolean hasNext() {
                checkComodification();
                return current != null;
            }

            @Override
            public T next() {
                checkComodification();
                if (!hasNext()) throw new NoSuchElementException();
                T data = current.getVal();
                current = current.getNext();
                return data;
            }
        };
    }
}