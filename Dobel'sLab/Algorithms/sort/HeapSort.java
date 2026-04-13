package Algorithms.sort;

import java.util.Comparator;

public class HeapSort<T> {
    public void sort(Object[] array, int n, Comparator<? super T> c) {
        sort(array, 0, n - 1, c);
    }

    public void sort(Object[] array, int low, int high, Comparator<? super T> c) {
        int n = high - low + 1;
        if (n <= 1) return;

        for (int i = (n / 2) - 1; i >= 0; i--) {
            heapify(array, n, i, low, c);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(array, low, low + i);
            heapify(array, i, 0, low, c);
        }
    }

    @SuppressWarnings("unchecked")
    private void heapify(Object[] array, int heapSize, int i, int low, Comparator<? super T> c) {
        int max = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < heapSize && c.compare((T) array[low + left], (T) array[low + max]) > 0) {
            max = left;
        }
        if (right < heapSize && c.compare((T) array[low + right], (T) array[low + max]) > 0) {
            max = right;
        }
        if (max != i) {
            swap(array, low + i, low + max);
            heapify(array, heapSize, max, low, c);
        }
    }

    @SuppressWarnings("unchecked")
    private void heapify(Object[] array, int heapSize, int i, Comparator<? super T> c) {
        int max = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < heapSize && c.compare((T) array[left], (T) array[max]) > 0) {
            max = left;
        }
        if (right < heapSize && c.compare((T) array[right], (T) array[max]) > 0) {
            max = right;
        }
        if (max != i) {
            swap(array, i, max);
            heapify(array, heapSize, max, c);
        }
    }

    private void swap(Object[] array, int i, int j) {
        Object aux = array[i];
        array[i] = array[j];
        array[j] = aux;
    }
}