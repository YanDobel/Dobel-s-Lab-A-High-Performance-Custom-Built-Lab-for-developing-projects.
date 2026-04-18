package Lists.Stacks;

import Lists.ArrayList.ArrayList;

import java.util.List;

public class Stack<T> {
    ArrayList<T> stack;

    public Stack() {
        stack = new ArrayList<>();
    }

    public Stack(int size) {
        stack = new ArrayList<>(size);
    }

    public void push(T val) {
        stack.add(val);
    }

    public T pop() {
        return stack.remove(stack.size());
    }

    public T peek() {
        return stack.get(stack.size());
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    public void clear() {
        stack.clear();
    }

    public Object[] toArray() {
        return stack.toArray();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
