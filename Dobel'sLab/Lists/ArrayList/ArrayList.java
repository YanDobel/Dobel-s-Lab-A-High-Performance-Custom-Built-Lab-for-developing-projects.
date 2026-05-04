package Lists.ArrayList;

import Algorithms.sort.IntroSort;
import java.util.*;
import java.util.function.*;

public class ArrayList<T> implements Iterable<T> {
    private Object[] array;
    private int tamanho;
    private int modCount = 0;
    private final int capacidadePadrao = 10;

    public ArrayList() {
        this.array = new Object[capacidadePadrao];
        this.tamanho = 0;
    }
    public ArrayList(int initialCapacity) {
        if (initialCapacity < 0) throw new IllegalArgumentException("(!) Invalid initial capacity (!)");
        this.array = new Object[initialCapacity];
    }

    public void ensureCapacity(int minCapacity) {
        if (minCapacity > array.length) {
            modCount++;
            int newCapacity = Math.max(array.length + (array.length >> 1), minCapacity);
            this.array = Arrays.copyOf(this.array, newCapacity);
        }
    }

    public boolean add(T valor) {
        ensureCapacity(tamanho + 1);
        array[tamanho++] = valor;
        return true;
    }

    public boolean add(int index, T val) {
        if (index < 0 || index > this.tamanho) {
            throw new IndexOutOfBoundsException("(!) Index out of limit: " + index + " (!)");
        }
        ensureCapacity(tamanho + 1);
        int numToShift = tamanho - index;

        if (numToShift > 0) {
            System.arraycopy(array, index, array, index + 1, numToShift);
        }
        array[index] = val;
        tamanho++;
        modCount++;
        return true;
    }

    public T remove(int indice) {
        if (indice < 0 || indice >= this.tamanho) {
            throw new IndexOutOfBoundsException("(!) Index out of limit (!)");
        }
        modCount++;
        T removed = get(indice);
        int numElementsToShift = tamanho - indice - 1;
        if (numElementsToShift > 0) {
            System.arraycopy(array, indice + 1, array, indice, numElementsToShift);
        }
        array[--tamanho] = null;
        encolher();
        return removed;
    }


    public T remove(T o) {
        int index = indexOf(o);
        if (index >= 0) {
            return remove(index);
        }
        return null;
    }

    public T set(int index, T val) {
        if (index < 0 || index >= this.tamanho) {
            throw new IndexOutOfBoundsException("(!) Index out of limit (!)");
        }
        T oldVal = get(index);
        array[index] = val;
        return oldVal;
    }

    private void encolher() {
        if (this.array.length > capacidadePadrao && this.tamanho <= this.array.length / 4) {
            int novaCapacidade = Math.max(capacidadePadrao, this.array.length / 2);
            this.array = Arrays.copyOf(this.array, novaCapacidade);
        }
    }
    public boolean contains(T valor) {
        return indexOf(valor) >= 0;
    }

    public int indexOf(T val) {
        for (int i = 0; i < tamanho; i++) {
            if (Objects.equals(array[i], val)) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return this.tamanho == 0;
    }

    public void clear() {
        modCount++;
        for (int i = 0; i < tamanho; i++) {
            array[i] = null;
        }
        tamanho = 0;
        encolher();
    }

    @SuppressWarnings("unchecked")
    public T get(int indice) {
        if (indice < 0 || indice >= this.tamanho) {
            throw new IndexOutOfBoundsException("(!) Indice fora dos limites (!)");
        }
        return (T) this.array[indice];
    }

    public T get(T val) {
        int index = indexOf(val);
        if (index == -1) throw new NoSuchElementException("(!) Element not found (!)");
        return get(index);
    }

    public int size() {
        return this.tamanho;
    }

    public void trimToSize() {
        modCount++;
        if (tamanho < array.length) {
            array = (tamanho == 0) ? new Object[0] : Arrays.copyOf(array, tamanho);
        }
    }

    public void sort(Comparator<? super T> c) {
        new IntroSort<T>().sort(this.array, this.tamanho, c);
        modCount++;
    }

    public void reverse() {
        int left = 0;
        int right = tamanho - 1;
        while (left < right) {
            Object aux = array[left];
            array[left] = array[right];
            array[right] = aux;
            left++;
            right--;
        }
        modCount++;
    }

    public Object[] toArray() {
        return Arrays.copyOf(array, tamanho);
    }

    @Override
    public String toString() {
        if (tamanho == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < tamanho; i++) {
            sb.append(array[i]);
            if (i < tamanho - 1) {
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new ALIterator();
    }

    public ArrayList<T> filter(Predicate<? super T> filter) {
        Objects.requireNonNull(filter);
        ArrayList<T> newList = new ArrayList<T>();
        for (int i = 0; i < tamanho; i++) {
            T e = get(i);
            if (filter.test(e)) newList.add(e);
        }
        return newList;
    }

    public <R> ArrayList<R> map(Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper);
        ArrayList<R> newList = new ArrayList<R>();
        for (int i = 0; i < tamanho; i++) {
            newList.add(mapper.apply(get(i)));
        }
        return newList;
    }

    public boolean removeIf(Predicate<? super T> filter) {
        Objects.requireNonNull(filter);
        int removed = 0;
        int j = 0;
        for (int i = 0; i < tamanho; i++) {
            if (filter.test(get(i))) {
                removed++;
            } else {
                array[j++] = array[i];
            }
        }
        if (removed > 0) {
            for (int i = j; i < tamanho; i++) {
                array[i] = null;
            }
            tamanho = j;
            modCount++;
            encolher();
            return true;
        }
        return false;
    }

    public T reduce(T identity, BinaryOperator<T> accumulator) {
        Objects.requireNonNull(accumulator);
        T res = identity;
        for (int i = 0 ; i < tamanho; i++) {
            res = accumulator.apply(res, get(i));
        }
        return res;
    }

    public Optional<T> findFirst(Predicate<? super T> filter) {
        Objects.requireNonNull(filter);
        for (int i = 0; i < tamanho; i++) {
            T e = get(i);
            if (filter.test(e)) return Optional.of(e);
        }
        return Optional.empty();
    }

    public void replaceAll(UnaryOperator<T> op) {
        Objects.requireNonNull(op);
        for (int i = 0; i< tamanho; i++) {
            array[i] = op.apply(get(i));
        }
        modCount++;
    }

    public boolean addAll(ArrayList<? extends T> other) {
        if (other == null || other.isEmpty()) return false;
        ensureCapacity(tamanho + other.size());
        System.arraycopy(other.array, 0, this.array, this.tamanho, other.size());
        tamanho += other.size();
        modCount++;
        return true;
    }

    public void forEachInRange(int from, int to, Consumer<? super T> action) {
        Objects.requireNonNull(action);
        if (from < 0 || to > tamanho || from > to) {
            throw new IndexOutOfBoundsException("(!) Invalid range: " + from + " to " + to + " (!)");
        }
        for (int i = from; i < to; i++) {
            action.accept(get(i));
        }
    }

    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T e : this) action.accept(e);
    }

    private class ALIterator implements Iterator<T> {
        private int indiceAtual = 0;
        private final int expectedModCount= modCount;

        @Override
        public boolean hasNext() {
            checkForComodification();
            return indiceAtual < tamanho;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            checkForComodification();
            if (!hasNext()) throw new NoSuchElementException("(!) Empty List (!)");
            return (T) array[indiceAtual++];
        }

        final void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("(!) List modified during iteration (!)");
            }
        }
    }
}