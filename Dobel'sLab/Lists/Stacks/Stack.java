package Lists.Stacks;

import java.util.*;
import java.util.function.*;

public class Stack<T> implements Iterable<T> {
    private T[] stack;
    private int size;
    private int modCount;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public Stack(int capacity) {
        this.stack = (T[]) new Object[capacity];
        this.capacity = capacity;
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    public Stack() {
        this.stack = (T[]) new Object[DEFAULT_CAPACITY];
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
    }

    public void push(T val) {
        if (size == capacity) grow();
        stack[size] = val;
        size++;
        modCount++;
    }

    public T pop() {
        if (isEmpty()) throw new NoSuchElementException("(!) Empty Stack (!)");
        size--;
        T val = stack[size];
        stack[size] = null;
        modCount++;
        if (size > 0 && size <= capacity / 4 && capacity > DEFAULT_CAPACITY) shrink();
        return val;
    }

    public T peek() {
        if (isEmpty()) return null;
        return stack[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int indexOf(T val) {
        if (isEmpty()) throw new NoSuchElementException("(!) Empty Stack (!)");
        for (int i = 0; i < size; i++) {
            if (Objects.equals(stack[i], val)) return i;
        }
        return -1;
    }

    public void clear() {
        for (int i = 0; i < size; i++) stack[i] = null;
        size = 0;
        modCount++;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        if (isEmpty()) throw new NoSuchElementException("(!) Empty Stack (!)");
        T[] array = (T[]) new Object[this.size];
        for (int i = 0; i < size; i++) array[i] = stack[i];
        return array;
    }

    public List<T> toList() {
        List<T> list = new ArrayList<>(this.size);
        for (T val : this) list.add(val);
        return list;
    }

    @SuppressWarnings("unchecked")
    private void grow() {
        int newCapacity = this.capacity * 2;
        T[] newStack = (T[]) new Object[newCapacity];
        System.arraycopy(stack, 0, newStack, 0, size);

        this.stack = newStack;
        this.capacity = newCapacity;
        modCount++;
    }

    @SuppressWarnings("unchecked")
    private void shrink() {
        int newCapacity = Math.max(DEFAULT_CAPACITY, capacity / 2);
        T[] newStack = (T[]) new Object[newCapacity];
        System.arraycopy(stack, 0, newStack, 0, size);
        this.stack = newStack;
        this.capacity = newCapacity;
        modCount++;
    }

    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T val : this) action.accept(val);
    }

    public <R> Stack<R> map(Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper);
        Stack<R> newStack = new Stack<>(this.size);
        for (int i = 0; i < size; i++) {
            newStack.push(mapper.apply(stack[i]));
        }
        return newStack;
    }

    public Stack<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        Stack<T> newStack = new Stack<>(this.size);
        for (int i = 0; i < size; i++) {
            if (predicate.test(stack[i])) newStack.push(stack[i]);
        }
        return newStack;
    }

    public Optional<T> find(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        for (int i = 0; i < size; i++) {
            if (predicate.test(stack[i])) return Optional.of(stack[i]);
        }
        return Optional.empty();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int current = size - 1;
            private final int expectedModCount = modCount;

            private void checkComodification() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }

            @Override
            public boolean hasNext() {
                checkComodification();
                return current >= 0;
            }

            @Override
            public T next() {
                checkComodification();
                if (!hasNext()) throw new NoSuchElementException();
                T val = stack[current];
                current--;
                return val;
            }
        };
    }
}