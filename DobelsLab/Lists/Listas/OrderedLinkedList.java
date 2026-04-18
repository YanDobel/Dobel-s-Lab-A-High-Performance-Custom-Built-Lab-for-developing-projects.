package Lists.Listas;

import java.security.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class OrderedLinkedList<T extends Comparable<T>> implements Iterable<T>{
    private NodeOL<T> first;
    private NodeOL<T> last;
    private int size;

    public OrderedLinkedList() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public void add(T data) {
       if (this.first == null || data.compareTo(this.first.getData()) <= 0) {
           insertFirst(data);
           return;
       }
       if (data.compareTo(this.last.getData()) >= 0) {
           insertLast(data);
           return;
       }

       NodeOL<T> aux = this.first;
       while (aux != null && data.compareTo(aux.getData()) > 0) {
           aux = aux.getNext();
       }

       NodeOL<T> newNode = new NodeOL<T>(data);
       NodeOL<T> prev = aux.getPrev();

       prev.setNext(newNode);
       newNode.setPrev(prev);
       newNode.setNext(aux);
       aux.setPrev(newNode);

       size++;
    }

    private void insertFirst(T data) {
        NodeOL<T> newNode = new NodeOL<>(data);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            newNode.setNext(first);
            first.setPrev(newNode);
            first = newNode;
        }
        size++;
    }

    private void insertLast(T data) {
        NodeOL<T> newNode = new NodeOL<>(data);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            last.setNext(newNode);
            newNode.setPrev(last);
            last = newNode;
        }
        size++;
    }

    public T remove(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("(!) Index: " + index + "Size :" + this.size + "(!)");
        }

        if (index == 0) return removeFirst();
        if (index == size - 1) return removeLast();

        NodeOL<T> target;
        if (index < size / 2) {
            target = first;

            for (int i = 0; i < index; i++) {
                target = target.getNext();
            }
        } else {
            target = last;

            for (int j = size - 1; j > index; j--) {
                target = target.getPrev();
            }

        }
        return unlink(target);
    }

    public T remove(T data) {

        NodeOL<T> aux = first;

        while (aux != null) {
            if (Objects.equals(aux.getData(), data)) {
                return unlink(aux);
            }
            aux = aux.getNext();
        }
        throw new NoSuchElementException("(!) Element not found (!)");
    }

    public T removeFirst() {
        return unlink(first);
    }

    public T removeLast() {
        return unlink(last);
    }
    private T unlink(NodeOL<T> node) {

        if (node == null) {
            throw new InvalidParameterException("(!) Null Node (!)");
        }
        T data = node.getData();
        NodeOL<T> next = node.getNext();
        NodeOL<T> prev = node.getPrev();

        if (prev == null) {
            first = next;
        } else {
            prev.setNext(next);
        }

        if (next == null) {
            last = prev;
        } else {
            next.setPrev(prev);
        }
        node.setNext(null);
        node.setPrev(null);

        size--;
        return data;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return this.size;
    }

    // ===== MÉTODOS ITERABLE =====

    @Override
    public void forEach(Consumer<? super T> action) {
        NodeOL<T> aux = first;
        while (aux != null) {
            action.accept(aux.getData());
            aux = aux.getNext();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private NodeOL<T> aux = first;

            @Override
            public boolean hasNext() {
                return aux != null;
            }
            @Override
            public T next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                T data = aux.getData();
                aux = aux.getNext();
                return data;
            }
        };
    }

    // ===== METODOS FUNCIONAIS =====

    public LinkedList<T> filter(Predicate<T> test) {
        LinkedList<T> listAux = new LinkedList<T>();
        NodeOL<T> nodeAux = first;
        while (nodeAux != null) {
            if (test.test(nodeAux.getData())) {
                listAux.add(nodeAux.getData());
            }
            nodeAux = nodeAux.getNext();
        }
        return listAux;
    }

    public <R extends Comparable<R>> LinkedList<R> map(Function<T, R> transformacao) {
        LinkedList<R> newList = new LinkedList<R>();

        NodeOL<T> aux = first;
        while (aux != null) {
            newList.add(transformacao.apply(aux.getData()));
            aux = aux.getNext();
        }
        return newList;
    }


    public Optional<T> find (Predicate<T> test) {
        return this.stream().filter(test).findFirst();
    }

    public Stream<T> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    @Override
    public String toString() {
        if (size == 0) return "[]";

        StringBuilder sb = new StringBuilder("[");
        NodeOL<T> aux = this.first;

        while (aux != null) {
            sb.append(aux.getData());

            aux = aux.getNext();

            if (aux != null) sb.append(", ");
        }
        return sb.append("]").toString();
    }

}