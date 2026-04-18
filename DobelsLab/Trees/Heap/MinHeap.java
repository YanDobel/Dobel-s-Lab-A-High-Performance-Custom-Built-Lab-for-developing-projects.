package Trees.Heap;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class MinHeap<T extends Comparable<T>> {
    private T[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public MinHeap() {
        this.heap = (T[]) new Comparable[DEFAULT_CAPACITY];
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    public MinHeap(int capacity) {
        this.heap = (T[]) new Comparable[capacity];
        this.size = 0;
    }

    public void add(T element) {
        ensureCapacity();
        heap[size] = element;
        siftUp(size);
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T poll() {
        if (size == 0) throw new NoSuchElementException("(!) Heap is Empty (!)");
        T root = heap[0];
        heap[0] = heap[size - 1];
        size--;
        siftDown(0);
        return root;
    }

    private void siftDown(int index) {
        int half = size / 2;
        T element = heap[index];

        while(index < half) {
            int left = 2 * index + 1;
            int right = left + 1;
            int smallest = left;

            if (right < size && heap[right].compareTo(heap[left]) < 0) {
                smallest = right;
            }
            if (element.compareTo(heap[smallest]) <= 0) break;

            heap[index] = heap[smallest];
            index = smallest;
        }
        heap[index] = element;
    }

    private void siftUp(int index) {
        T element = heap[index];
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            T parent = heap[parentIndex];
            if (element.compareTo(parent) >= 0) break;

            heap[index] = parent;
            index = parentIndex;
        }
        heap[index] = element;
    }

    private void ensureCapacity() {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
    }
}