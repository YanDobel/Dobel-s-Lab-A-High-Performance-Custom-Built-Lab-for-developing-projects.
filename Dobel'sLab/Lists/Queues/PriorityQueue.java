package Lists.Queues;

import java.util.*;
import java.util.function.*;

public class PriorityQueue<V, P extends Comparable<P>> implements Iterable<V> {

    private static class Entry<V, P extends Comparable<P>> implements Comparable<Entry<V, P>> {
        private final V value;
        private final P priority;
        private final long sequenceNumber;

        public Entry(V value, P priority, long sequenceNumber) {
            this.value = value;
            this.priority = priority;
            this.sequenceNumber = sequenceNumber;
        }
        @Override
        public int compareTo(Entry<V, P> o) {
            int cmp = this.priority.compareTo(o.priority);
            if (cmp != 0) return cmp;
            return Long.compare(this.sequenceNumber, o.sequenceNumber);
        }

    }

    private Entry<V, P>[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;
    private long timer = 0;
    private int modCount = 0;

    @SuppressWarnings("unchecked")
    public PriorityQueue() {
        this.heap = (Entry<V, P>[]) new Entry[DEFAULT_CAPACITY];
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    public PriorityQueue(int capacity) {
        this.heap = (Entry<V, P>[]) new Entry[capacity];
        this.size = 0;
    }

    public void enqueue(V value, P priority) {
        ensureCapacity();
        heap[size] = new Entry<>(value, priority, timer++);
        siftUp(size);
        size++;
        modCount++;
    }

    public V dequeue() {
        if (isEmpty()) throw new NoSuchElementException("(!) Queue is empty (!)");
        V res = heap[0].value;
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        modCount++;
        if (size > 0) siftDown(0);
        return res;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int capacity() {
        return heap.length;
    }

    private void ensureCapacity() {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
            modCount++;
        }
    }

    public V peek() {
        if (isEmpty()) return null;
        return heap[0].value;
    }

    public void clear() {
        Arrays.fill(heap, 0, size, null);
        this.size = 0;
        modCount++;
    }

    private void siftDown(int index) {
        int half = size / 2;
        Entry<V, P> top = heap[index];
        while (index < half) {
            int left = 2 * index + 1;
            int right = left + 1;
            int largest = left;
            if (right < size && heap[right].compareTo(heap[left]) > 0) {
                largest = right;
            }
            if (top.compareTo(heap[largest]) >= 0) break;
            heap[index] = heap[largest];
            index = largest;
        }
        heap[index] = top;
    }

    private void siftUp(int index) {
        Entry<V, P> bottom = heap[index];
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (bottom.compareTo(heap[parent]) <= 0) break;
            heap[index] = heap[parent];
            index = parent;
        }
        heap[index] = bottom;
    }

    @SuppressWarnings("unchecked")
    public V[] toArray() {
        if (isEmpty()) throw new RuntimeException("(!) Empty List (!)");
        V[] array = (V[]) new Object[this.size];
        for (int i = 0; i < size; i++) {
            array[i] = heap[i].value;
        }
        return array;
    }

    // ============== FUNCTIONAL METHODS ===============

    public <R> List<R> mapToList(Function<? super V, ? extends R> mapper) {
        List<R> list = new ArrayList<>(heap.length);
        for (int i = 0; i < size; i++) {
            list.add(mapper.apply(heap[i].value));
        }
        return list;
    }

    public <R> PriorityQueue<R, P> map(Function<? super V, ? extends R> mapper) {
        PriorityQueue<R, P> pq = new PriorityQueue<>(heap.length);
        for (int i = 0; i < size; i++) {
            pq.enqueue(mapper.apply(heap[i].value), heap[i].priority);
        }
        return pq;
    }

    public List<V> filterToList(Predicate<V> test) {
        Objects.requireNonNull(test);
        List<V> list = new ArrayList<>(heap.length);
        for (V val : this) {
            if (test.test(val)) list.add(val);
        }
        return list;
    }

    public PriorityQueue<V, P> filter(Predicate<V> test) {
        Objects.requireNonNull(test);
        PriorityQueue<V, P> pq = new PriorityQueue<>(heap.length);
        for (int i = 0; i < size; i++) {
            if (test.test(heap[i].value)) {
                pq.enqueue(heap[i].value, heap[i].priority);
            }
        }
        return pq;
    }

    public void forEach(Consumer<? super V> action) {
        Objects.requireNonNull(action);
        for (V value : this) action.accept(value);
    }

    @Override
    public Iterator<V> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<V> {
        private int cursor = 0;
        private final int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            checkModification();
            return cursor < size;
        }
        @Override
        public V next() {
            checkModification();
            if (!hasNext()) throw new NoSuchElementException();
            return heap[cursor++].value;
        }

        public void checkModification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("(!) Queue modified during iteration (!)");
            }
        }
    }
}