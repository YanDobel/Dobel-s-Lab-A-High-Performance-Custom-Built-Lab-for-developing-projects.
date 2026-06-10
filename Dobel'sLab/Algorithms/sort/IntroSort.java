package Algorithms.sort;

import java.util.Arrays;
import java.util.Comparator;

public class IntroSort<T> {
    private final QuickSort<T> quickSort = new QuickSort<>();
    private final HeapSort<T> heapSort = new HeapSort<>();
    private final InsertionSort<T> insertionSort = new InsertionSort<>();

    public void sort(Object[] array, int n, Comparator<? super T> c) {
        sort(array, n, c, true);
    }

    public void sort(Object[] array, int n, Comparator<? super T> c, boolean scan) {
        if (n <= 1) return;
        if (scan && isAlreadySorted(array, 0, n - 1, c)) return;

        int depthLimit = (Integer.SIZE - Integer.numberOfLeadingZeros(n)) << 1;
        introSort(array, 0, n - 1, depthLimit, c);
    }

    public T[] sortToList(T[] array, Comparator<? super T> c) {
        T[] copy = Arrays.copyOf(array, array.length);
        sort(copy, copy.length, c);
        return copy;
    }

    private void introSort(Object[] array, int low, int high, int depthLimit, Comparator<? super T> c) {
        int size = high - low + 1;
        if (size < 16) {
            insertionSort.sort(array, low, high, c);
            return;
        }
        if (depthLimit == 0) {
            heapSort.sort(array, low, high, c);
            return;
        }

        int p = quickSort.partition(array, low, high, c);
        introSort(array, low, p, depthLimit - 1, c);
        introSort(array, p + 1, high, depthLimit - 1, c);
    }

    @SuppressWarnings("unchecked")
    private boolean isAlreadySorted(Object[] array, int low, int high, Comparator<? super T> c) {
        boolean ascending = true;
        boolean descending = true;

        for (int i = low; i < high; i++) {
            int cmp = c.compare((T) array[i], (T) array[i + 1]);
            if (cmp > 0) ascending = false;
            if (cmp < 0) descending = false;

            if (!ascending && !descending) return false;
        }
        if (ascending) return true;
        if (descending) {
            invert(array, low, high);
            return true;
        }
        return false;
    }

    private void invert(Object[] array, int low, int high) {
        while (low < high) {
            Object aux = array[low];
            array[low] = array[high];
            array[high] = aux;
            low++;
            high--;
        }
    }
}
