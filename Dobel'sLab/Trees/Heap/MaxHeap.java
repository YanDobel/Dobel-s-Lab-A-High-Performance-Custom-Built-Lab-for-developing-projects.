package Trees.Heap;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class MaxHeap<T extends Comparable<T>> {
    private T[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public MaxHeap() {
        this.heap = (T[]) new Comparable[DEFAULT_CAPACITY];
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    public MaxHeap(int capacity) {
        this.heap = (T[]) new Comparable[capacity];
        this.size = 0;
    }

    public MaxHeap(T[] array) {
        this.heap = Arrays.copyOf(array, Math.max(DEFAULT_CAPACITY, array.length));
        this.size = array.length;
        buildHeap();
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
        heap[size - 1] = null;
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
            int largest = left;

            if (right < size && heap[right].compareTo(heap[left]) > 0) {
                largest = right;
            }
            if (element.compareTo(heap[largest]) >= 0) break;

            heap[index] = heap[largest];
            index = largest;
        }
        heap[index] = element;
    }

    private void siftUp(int index) {
        T element = heap[index];
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            T parent = heap[parentIndex];
            if (element.compareTo(parent) <= 0) break;

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

    private void buildHeap() {
        for (int i = (size / 2) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }
}
