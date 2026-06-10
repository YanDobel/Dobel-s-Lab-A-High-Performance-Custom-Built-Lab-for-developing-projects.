package HashTables.LinkedHashTables;
import java.util.Iterator;
import java.util.function.Consumer;

public class LinkedHashSet<E> implements Iterable<E> {
    private final LinkedHashMap<E, Object> map;
    private static final Object PRESENT = new Object();

    public LinkedHashSet() {
        this.map = new LinkedHashMap<>();
    }

    public LinkedHashSet(int initialCapacity) {
        this.map = new LinkedHashMap<>(initialCapacity);
    }

    public boolean add(E element) {
        return map.put(element, PRESENT) == null;
    }

    public boolean remove(E element) {
        return map.remove(element) == PRESENT;
    }

    public boolean contains(E element) {
        return map.containsKey(element);
    }

    public int size() {
        return map.size();
    }

    public void clear() {
        map.clear();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return map.keys().iterator();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        map.keys().forEach(action);
    }

    @Override
    public String toString() {
        return map.keys().toString()
                .replace("[", "{").replace("]", "}");
    }
}
