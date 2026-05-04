package Lists.Deque;

import java.util.*;
import java.util.function.*;

public class Deque<T> implements Iterable<T> {
    private T[] queue;
    private int head;
    private int tail;
    private int size;
    private int capacity;
    private transient int modCount;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public Deque(int capacity) {
        this.capacity = capacity;
        this.queue = (T[]) new Object[capacity];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }
    @SuppressWarnings("unchecked")
    public Deque() {
        this.capacity = DEFAULT_CAPACITY;
        this.queue = (T[]) new Object[DEFAULT_CAPACITY];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T val) {
        if (size == capacity) grow();
        head = (head - 1 + capacity) % capacity;
        queue[head] = val;
        size++;
        modCount++;
    }

    public void addLast(T val) {
        if (size == capacity) grow();
        queue[tail] = val;
        tail = (tail + 1) % capacity;
        modCount++;
        size++;
    }

    public T removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("(!) Empty queue (!)");
        T val = queue[head];
        queue[head] = null;
        head = (head + 1) % capacity;
        modCount++;
        size--;
        if (size > 0 && size <= capacity / 4 && capacity > DEFAULT_CAPACITY) shrink();
        return val;
    }

    public T removeLast() {
        if (isEmpty()) throw new NoSuchElementException("(!) Empty Deque (!)");

        tail = (tail - 1 + capacity) % capacity;
        T val = queue[tail];
        queue[tail] = null;
        size--;
        modCount++;
        if (size > 0 && size <= capacity / 4 && capacity > DEFAULT_CAPACITY) shrink();
        return val;
    }

    public T getFirst() {
        if (isEmpty()) throw new NoSuchElementException("(!) Empty Deque (!)");
        return queue[head];
    }

    public T getLast() {
        if (isEmpty()) throw new NoSuchElementException("(!) Empty Deque (!)");
        return queue[(tail - 1 + capacity) % capacity];
    }

    public int indexOf(T val) {
        if (isEmpty()) return -1;
        for (int i = 0; i < size; i++) {
            int currentIndex = (head + i) % capacity;
            if (queue[currentIndex] != null && queue[currentIndex].equals(val)) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            int physicalIndex = (head + i) % capacity;
            queue[physicalIndex] = null;
        }
        size = 0;
        head = 0;
        tail = 0;
        modCount++;
    }


    @SuppressWarnings("unchecked")
    private void grow() {
        int newCapacity = capacity * 2;
        T[] newQueue = (T[]) new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[(head + i) % capacity];
        }
        this.queue = newQueue;
        this.capacity = newCapacity;
        this.head = 0;
        this.tail = size;
        modCount++;
    }

    @SuppressWarnings("unchecked")
    private void shrink() {
        int newCapacity = Math.max(DEFAULT_CAPACITY, capacity / 2);
        T[] newQueue = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[(head + i) % capacity];
        }
        this.queue = newQueue;
        this.capacity = newCapacity;
        this.head = 0;
        this.tail = size;
        modCount++;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T value : this) action.accept(value);
    }

    public void forEachInRange(int from, int to, Consumer<? super T> action) {
        Objects.requireNonNull(action);
        if (from < 0 || to > size || from > to) throw new IndexOutOfBoundsException();
        for (int i = from; i < to; i++) {
            int physicalIndex = (head + i) % capacity;
            action.accept(queue[physicalIndex]);
        }
    }

    public <R> Deque<R> map(Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper);
        Deque<R> newDeque = new Deque<>(this.capacity);
        for (T val : this) {
            newDeque.addLast(mapper.apply(val));
        }
        return newDeque;
    }

    public <R> List<R> mapToList(Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper);
        List<R> list = new ArrayList<>(this.capacity);
        for (T val : this) {
            list.add(mapper.apply(val));
        }
        return list;
    }

    public Deque<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        Deque<T> filteredDeque = new Deque<>(this.capacity);
        for (T val : this) {
            if (predicate.test(val)) {
                filteredDeque.addLast(val);
            }
        }
        return filteredDeque;
    }

    public Optional<T> find(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        for (T val : this) {
            if (predicate.test(val)) return Optional.of(val);
        }
        return Optional.empty();
    }

    public List<T> toList() {
        if (isEmpty()) throw new RuntimeException("(!) Empty Queue (!)");
        List<T> list = new ArrayList<>(this.size);
        for (T val : this) {
            list.add(val);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        if (isEmpty()) throw new RuntimeException("(!) Empty Queue (!)");
        T[] array = (T[]) new Object[this.size];
        for (int i = 0; i < size; i++) {
            int physicalIndex = (head + i) % capacity;
            array[i] = queue[physicalIndex];
        }
        return array;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int currentPos = 0;
            private final int expectedModCount = modCount;

            private void checkComodification() {
                if (modCount != expectedModCount) throw new ConcurrentModificationException();
            }
            @Override
            public boolean hasNext() {
                checkComodification();
                return currentPos < size;
            }
            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                int physicalIndex = (head + currentPos) % capacity;
                T val = queue[physicalIndex];
                currentPos++;
                return val;
            }
        };
    }
}