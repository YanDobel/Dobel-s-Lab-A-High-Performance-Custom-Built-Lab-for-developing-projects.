package HashTables;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class HashSet<K> implements Iterable<K> {
    private final HashTable<K, Object> table;
    private static final Object PRESENT = new Object();

    public HashSet() {
        this.table = new HashTable<>();
    }

    public HashSet(int initialCapacity) {
        this.table = new HashTable<>(initialCapacity);
    }

    public boolean add(K key) {
        return table.put(key, PRESENT) == null;
    }

    public boolean remove(K key) {
        return table.remove(key) != null;
    }

    public boolean contains(K key) {
        return table.get(key) != null;
    }

    public int size() {
        return table.size();
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }

    public void clear() {
        table.clear();
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private final Iterator<HashNode<K, Object>> it = table.iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public K next() {
                return it.next().getKey();
            }
        };
    }
    public void forEach(Consumer<? super K> action) {
        Objects.requireNonNull(action);
        for (K key : this) action.accept(key);
    }

    public Stream<K> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public Spliterator<K> spliterator() {
        List<K> keys = new ArrayList<>(size());
        for (K key : this) keys.add(key);
        return keys.spliterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        Iterator<K> it = iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) sb.append(", ");
        }
        return sb.append("}").toString();
    }
}