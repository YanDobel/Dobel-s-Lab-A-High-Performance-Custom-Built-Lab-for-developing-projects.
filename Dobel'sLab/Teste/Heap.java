package Teste;


public class Heap<T extends Comparable<T>> {
    private T[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public Heap(int capacity) {
        heap = (T[]) new Comparable[capacity];
        this.size = 0;
    }
    @SuppressWarnings("unchecked")
    public Heap() {
        heap = (T[]) new Comparable[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public void add(T data) {
        heap[size] = data;
        size++;

    }

    private void siftUp(int index) {
        T aux;
        int parentIndex;
        if (index != 0) {
            parentIndex = (index - 1) / 2;

            if (heap[parentIndex].compareTo(heap[index]) > 0) {
                aux = heap[parentIndex];
                heap[parentIndex] = heap[index];
                heap[index] = aux;
                siftUp(parentIndex);
            }
        }
    }

    private void siftUp2(int index) {
        T data = heap[index];
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            T parent = heap[parentIndex];
            if (data.compareTo(parent) >= 0) break;
            heap[index] = parent;
            index = parentIndex;
        }
        heap[index] = data;
    }

    private void siftDown(int index) {
        int half = size / 2;
        T top = heap[index];
        while (index < half) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = left;
            if (right < half && heap[right].compareTo(heap[left]) < 0) {
                smallest = right;
            }
            if (top.compareTo(heap[smallest]) <= 0) break;

            heap[index] = heap[smallest];
            index = smallest;
        }
        heap[index] = top;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}