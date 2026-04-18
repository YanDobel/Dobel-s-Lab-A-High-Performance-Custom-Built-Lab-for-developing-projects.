package Algorithms.sort;
import java.util.Comparator;

public class InsertionSort<T> {

    @SuppressWarnings("unchecked")
    public void sort(Object[] array, int low, int high, Comparator<? super T> c) {
        for (int i = low + 1; i <= high; i++){
            T key = (T) array[i];
            int j = i - 1;

            while (j >= low && c.compare((T) array[j], key) > 0) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }
}
