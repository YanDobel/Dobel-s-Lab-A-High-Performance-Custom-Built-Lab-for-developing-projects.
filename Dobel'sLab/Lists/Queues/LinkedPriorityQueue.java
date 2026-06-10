package Lists.Queues;

import java.util.*;
import java.util.function.*;

public class LinkedPriorityQueue<V, P extends Comparable<P>> implements Iterable<V> {
    private Node<V, P> first;
    private Node<V, P> last;
    private int size;
    private transient int modCount;

    public LinkedPriorityQueue() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(V value, P priority) {
        Node<V, P> node = new Node<>(value, priority);
        if (first == null) first = last = node;
        else if (priority.compareTo(first.getPriority()) > 0) {
            node.setNext(first);
            first.setPrev(node);
            first = node;
        } else {
            Node<V, P> current = first;
            while (current.getNext() != null
                    && current.getNext().getPriority().compareTo(priority) >= 0) {
                current = current.getNext();
            }
            node.setNext(current.getNext());
            node.setPrev(current);

            if (current.getNext() == null) {
                last = node;
            } else {
                current.getNext().setPrev(node);
            }
            current.setNext(node);
        }
        modCount++;
        size++;
    }

    public V dequeue() {
        if (first == null) throw new NoSuchElementException("(!) Empty Queue (!)");

        V value = first.getValue();
        first = first.getNext();

        if (first == null) {
            this.last = null;
        } else {
            first.setPrev(null);
        }
        modCount++;
        this.size--;
        return value;
    }

    public int indexOf(V value) {
        Node<V, P> aux = first;
        int index = 0;

        while (aux != null) {
            if (Objects.equals(aux.getValue(), value)) {
                return index;
            }
            aux = aux.getNext();
            index++;
        }
        return -1;
    }

    public void clear() {
        this.first = null;
        this.last = null;
        this.size = 0;
        modCount++;
    }

    public List<Node<V, P>> toList() {
        if (isEmpty()) throw new RuntimeException("(!) Empty Queue (!)");
        List<Node<V, P>> list = new ArrayList<>();
        Node<V, P> aux = first;
        while (aux != null) {
            list.add(aux);
            aux = aux.getNext();
        }
        return list;
    }

    @Override
    public void forEach(Consumer<? super V> action) {
        Node<V, P> aux = first;
        while (aux != null) {
            action.accept(aux.getValue());
            aux = aux.getNext();
        }
    }

    public void forEachInRange(int start, int end, Consumer<? super V> action) {
        if (start < 0 || end >= size || start > end)  {
            throw new IndexOutOfBoundsException("(!) Index out of limit (!)");
        }
        Node<V, P> aux = first;
        for (int i = 0; i < start; i++) aux = aux.getNext();

        for (int i = start; i <= end; i++) {
            action.accept(aux.getValue());
            aux = aux.getNext();
        }
    }

    public LinkedPriorityQueue<V, P> filter(Predicate<? super V> test) {
        Objects.requireNonNull(test);
        LinkedPriorityQueue<V, P> newQueue = new LinkedPriorityQueue<>();
        Node<V, P> aux = first;
        while (aux != null) {
            if (test.test(aux.getValue())) {
                newQueue.enqueue(aux.getValue(), aux.getPriority());
            }
            aux = aux.getNext();
        }
        return newQueue;
    }

    public <R> LinkedPriorityQueue<R, P> map(Function<? super V, ? extends R> mapper) {
        LinkedPriorityQueue<R, P> newQueue = new LinkedPriorityQueue<>();
        Node<V, P> aux = first;
        while (aux != null) {
            newQueue.enqueue(mapper.apply(aux.getValue()), aux.getPriority());
            aux = aux.getNext();
        }
        return newQueue;
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<>() {
            private Node<V, P> current = first;
            private final int expectedModCount = modCount;

            private void checkForComodification() {
                if (modCount != expectedModCount) throw new ConcurrentModificationException();
            }

            @Override
            public boolean hasNext() {
                checkForComodification();
                return current != null;
            }

            @Override
            public V next() {
                checkForComodification();
                if (!hasNext()) throw new NoSuchElementException("(!) Empty Queue (!)");
                V value = current.getValue();
                current = current.getNext();
                return value;
            }
        };
    }
}