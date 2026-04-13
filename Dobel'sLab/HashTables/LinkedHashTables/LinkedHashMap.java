package HashTables.LinkedHashTables;
import HashTables.Contracts.MyMap;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class LinkedHashMap<K, V> implements MyMap<K, V>, Iterable<HashNode<K, V>> {
    private final LinkedHashTable<K, V> table;

    public LinkedHashMap(int initialCapacity) {
        this.table = new LinkedHashTable<>(initialCapacity);
    }

    public LinkedHashMap() {
        this.table = new LinkedHashTable<>();
    }

    @Override
    public V put(K key, V value) {
        return table.put(key, value);
    }

    @Override
    public V get(K key) {
        return table.get(key);
    }

    @Override
    public V remove(K key) {
        return table.remove(key);
    }

    @Override
    public int size() {
        return table.size();
    }

    @Override
    public boolean isEmpty() {
        return table.isEmpty();
    }

    @Override
    public boolean containsKey(K key) {
        return table.containsKey(key);
    }

    @Override
    public void clear() {
        table.clear();
    }

    @Override
    public V getOrDefault(K key, V defaultValue) {
        return table.getOrDefault(key, defaultValue);
    }

    @Override
    public List<K> keys() {
        return table.keys();
    }

    @Override
    public List<V> values() {
        return table.values();
    }

    public List<HashNode<K, V>> entrySet() {
        return table.entrySet();
    }

    public void forEach(BiConsumer<? super K, ? super V> action) {
        table.forEach(action);
    }

    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return table.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public Iterator<HashNode<K, V>> iterator() {
        return table.entrySet().iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        List<HashNode<K, V>> entries = entrySet();

        for (int i = 0; i < entries.size(); i++) {
            HashNode<K, V> node = entries.get(i);
            sb.append(node.getKey()).append("=").append(node.getData());
            if (i < entries.size() - 1) sb.append(", ");
        }
        return sb.append("}").toString();
    }
}
