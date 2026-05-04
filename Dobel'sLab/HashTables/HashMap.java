package HashTables;

import java.util.*;
import java.util.HashSet;
import java.util.function.*;
import java.util.stream.*;

public class HashMap<K, D> {
    private final HashTable<K, D> table;

    public HashMap() {
        this.table = new HashTable<>();
    }

    public HashMap(int initialCapacity) {
        this.table = new HashTable<>(initialCapacity);
    }

    public D put(K key, D value) {
        return table.put(key, value);
    }

    public D get(K key) {
        return table.get(key);
    }

    public D remove(K key) {
        return table.remove(key);
    }

    public boolean containsKey(K key) {
        return table.get(key) != null;
    }

    public int size() {
        return table.size();
    }

    public void clear() {
        table.clear();
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }

    public D putIfAbsent(K key, D value) {
        return table.putIfAbsent(key, value);
    }

    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        table.forEach((k, v) -> keys.add(k));
        return keys;
    }

    public Collection<D> values() {
        List<D> values = new ArrayList<>();
        table.forEach((k, v) -> values.add(v));
        return values;
    }

    public void forEach(BiConsumer<? super K, ? super D> action) {
        table.forEach(action);
    }

    public Stream<HashNode<K, D>> stream() {
        return table.stream();
    }

    public Spliterator<HashNode<K, D>> spliterator() {
        return table.spliterator();
    }

    public D computeIfAbsent(K key, Function<? super K, ? extends D> mapper) {
        return table.computeIfAbsent(key, mapper);
    }

    public D computeIfPresent(K key, BiFunction<? super K, ? super D, ? extends D> remapper) {
        return table.computeIfPresent(key, remapper);
    }

    public HashMap<K, D> filter(Predicate<HashNode<K, D>> predicate) {
        Objects.requireNonNull(predicate);
        HashMap<K, D> newMap = new HashMap<>();
        this.table.forEach((k, d) -> {
            if (predicate.test(new HashNode<>(k, d))) {
                newMap.put(k, d);
            }
        });
        return newMap;
    }

    @Override
    public String toString() {
        return table.toString();
    }
}