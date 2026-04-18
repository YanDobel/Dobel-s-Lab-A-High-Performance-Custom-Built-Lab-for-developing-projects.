package Algorithms.sort;

import java.util.Comparator;

public class QuickSort<T> {
    public void sort(Object[] array, int low,int high, Comparator<? super T> c) {
        if (low < high) {
            int pivot = partition(array, low, high, c);
            sort(array, low, pivot, c);
            sort(array, pivot + 1, high, c);
        }
    }

    @SuppressWarnings("unchecked")
    public int partition(Object[] array, int low, int high, Comparator<? super T> c) {
        T pivot = (T) array[low + (high - low) / 2];
        int i = low - 1;
        int j = high + 1;

        while (true) {
            do { i++;} while(c.compare((T) array[i], pivot) < 0);
            do { j--; } while (c.compare((T) array[j], pivot) > 0);
            if (i >= j) return j;
            swap(array, i, j);
        }
    }
    private void swap(Object[] array, int i, int j) {
        Object temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
