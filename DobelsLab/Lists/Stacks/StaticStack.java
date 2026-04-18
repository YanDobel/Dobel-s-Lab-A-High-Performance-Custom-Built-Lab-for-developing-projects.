package Lists.Stacks;

public class StaticStack<T> {
    private final Object[] stack;
    private int size = 16;
    private int cont;

    public StaticStack(int size) {
        stack = new Object[size];
        this.size = size;
        cont = 0;
    }

    public StaticStack() {
        stack = new Object[size];
        cont = 0;
    }

    public void push(T val) {
        stack[cont] = val;
        cont++;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        cont--;
        return (T) stack[size];
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        return (T) stack[size];
    }

    public boolean isEmpty() {
        return cont == 0;
    }

    public int size() {
        return this.cont;
    }

    public void clear() {
        for (int i = 0; i < size; i++) stack[i] = null;
    }
}
