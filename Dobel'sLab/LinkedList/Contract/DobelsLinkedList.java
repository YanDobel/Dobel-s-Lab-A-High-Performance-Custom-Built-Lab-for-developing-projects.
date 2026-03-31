package LinkedList.Contract;
import LinkedList.Node;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

public interface DobelsLinkedList<T> {
    T getLast();
    T getFirst();
    T get(T data);
    T get(int index);
    void add(T data);
    void add(int idx, T data);
    void insertFirst(T data);
    void insertLast(T data);
    T remove(int index);
    T remove(T data);
    T removeFirst();
    T removeLast();
    T unlink(Node<T> node);
    boolean contains(T data);
    boolean isEmpty();
    int size();
    int indexOf(T data);
    void clear();
    LinkedList<T> filter(Predicate<T> test);
    Iterator<T> iterator();
    public Optional<T> find(Predicate<T> test);
    public Stream<T> stream();
    public Spliterator<T> spliterator();
    public boolean removeIf(Predicate<? super T> filter);
    public <R> LinkedList<R> map(Function<? super T, ? extends R> mapper);
}
