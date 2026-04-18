package HashTables.LinkedHashTables;

import java.util.*;
import java.util.function.*;
import Lists.LinkedList.LinkedList;

public class LinkedHashTable<K, D> implements Iterable<HashNode<K, D>>{
    private int size;
    private int count;
    private HashList<K, D>[] buckets;
    private final LinkedList<HashNode<K, D>> ordered;

    @SuppressWarnings("unchecked")
    public LinkedHashTable(int size) {
        ordered = new LinkedList<HashNode<K, D>>();
        this.size = size;
        this.count = 0;
        this.buckets = (HashList<K, D>[]) new HashList[size];
        for (int i = 0; i < size; i++) buckets[i] = new HashList<>();
    }

    @SuppressWarnings("unchecked")
    public LinkedHashTable() {
        this(16);
    }

    private int hash(K key) {
        return ((key.hashCode() & 0x7fffffff) % size);
    }

    public D put(K key, D data) {
        int index = hash(key);
        HashList<K, D> bucket = buckets[index];
        Optional<HashNode<K, D>> nodeFound = bucket
                .find(node -> Objects.equals(node.getKey(), key));

        if (nodeFound.isPresent()) {
            HashNode<K, D> node = nodeFound.get();
            D oldData = node.getData();
            node.setData(data);
            return oldData;
        } else {
            HashNode<K, D> newNode = new HashNode<>(key, data);
            bucket.add(newNode);
            ordered.add(newNode);

            this.count++;
            checkLoad();
            return null;
        }
    }
    public D get(K key) {
        int index = hash(key);
        return buckets[index]
                .find(node -> Objects.equals(node.getKey(), key))
                .map(HashNode::getData)
                .orElse(null);
    }

    public D remove(K key) {
        int index = hash(key);
        D removedData = buckets[index].remove(key);
        if (removedData != null) {
            ordered.removeIf(node -> Objects.equals(node.getKey(), key));
            this.count--;
        }
        return removedData;
    }

    public int size() {
        return this.count;
    }

    public boolean containsKey(K key) {
        int index = hash(key);
        return buckets[index].contains(key);
    }

    public void clear() {
        for (HashList<K, D> h : buckets) h.clear();
        ordered.clear();
        this.count = 0;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        int newCapacity = buckets.length * 2;
        HashList<K, D>[] newBuckets = (HashList<K, D>[]) new HashList[newCapacity];

        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = new HashList<>();
        }

        for (HashList<K, D> old : buckets) {
            for (HashNode<K, D> node : old) {
                int newIndex = ((node.getKey().hashCode() & 0x7fffffff) % newCapacity);
                newBuckets[newIndex].add(node);
            }
        }
        this.buckets = newBuckets;
        this.size = newCapacity;
    }

    private void checkLoad() {
        if ((double) count / buckets.length >= 0.75) {
            rehash();
        }
    }

    public double getLoadFactor() {
        return ((double) size() / buckets.length);
    }

    public int capacity() {
        return buckets.length;
    }

    public List<K> keys() {
        List<K> list = new ArrayList<>(this.count);
        for (HashNode<K, D> node : ordered) list.add(node.getKey());
        return list;
    }

    public List<D> values() {
        List<D> list = new ArrayList<>(this.count);
        for (HashNode<K, D> node : ordered) list.add(node.getData());
        return list;
    }

    public List<HashNode<K, D>> entrySet() {
        List<HashNode<K ,D>> list = new ArrayList<>(this.count);
        for (HashNode<K, D> node : ordered) list.add(node);
        return list;
    }

    public void forEach(BiConsumer<? super K, ? super D> action) {
        Objects.requireNonNull(action);
        for (HashNode<K, D> node : ordered) {
            action.accept(node.getKey(), node.getData());
        }
    }

    public D getOrDefault(K key, D defaultVal) {
        int index = hash(key);
        Optional<HashNode<K, D>> node = buckets[index]
                .find(n -> Objects.equals(n.getKey(), key));
        return node.isPresent() ? node.get().getData() : defaultVal;
    }

    public D computeIfAbsent(K key, Function<? super K, ? extends D> mapper) {
        Objects.requireNonNull(mapper);
        D val = get(key);

        if (val == null) {
            D newVal = mapper.apply(key);
            if (newVal != null) {
                put(key, newVal);
                return newVal;
            }
        }
        return val;
    }

    @Override
    public Iterator<HashNode<K, D>> iterator() {
        return ordered.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        List<HashNode<K, D>> entries = entrySet();

        for (int i = 0; i < entries.size(); i++) {
            HashNode<K, D> node = entries.get(i);
            sb.append(node.getKey()).append("=").append(node.getData());
            if (i < entries.size() - 1) sb.append(", ");
        }
        return sb.append("}").toString();
    }
}