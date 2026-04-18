package HashTables;

import Lists.LinkedList.LinkedList;
import Trees.AVL.AVL_SucEsq.AVL;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class HashTable<K, D> implements Iterable<HashNode<K,D>>{
    private Bucket<K, D>[] buckets;
    private int count;
    private static final int TREEIFY_THRESHOLD = 8;
    private static final int UNTREEIFY_THRESHOLD = 6;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public HashTable() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        this.buckets = (Bucket<K, D>[]) new Bucket[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new ListBucket();
        }
    }

    public D put(K key, D data) {
        int index = hash(key);
        Bucket<K, D> b = buckets[index];
        D res = b.put(key, data);

        if (res == null) {
            this.count++;
            if (b instanceof HashTable.ListBucket && b.size() >= TREEIFY_THRESHOLD) {
                treeify(this.buckets, index);
            }
            checkLoad();
        }
        return res;
    }

    private void treeify(Bucket<K, D>[] target, int index) {
        Bucket<K, D> oldBucket = target[index];
        TreeBucket newBucket = new TreeBucket();
        for (HashNode<K, D> node : oldBucket.nodes()) {
            newBucket.put(node.getKey(), node.getData());
        }
        target[index] = newBucket;
    }

    private void untreeify(int index) {
        Bucket<K, D> oldBucket = buckets[index];
        ListBucket newBucket = new ListBucket();
        for (HashNode<K, D> node : oldBucket.nodes()) {
            newBucket.put(node.getKey(), node.getData());
        }
        buckets[index] = newBucket;
    }

    private int hash(K key) {
        if (key == null) return 0;

        int h = key.hashCode();
        h = h ^ (h >>> 16);

        return (h & 0x7fffffff) % buckets.length;
    }

    private int hash(K key, int capacity) {
        if (key == null) return 0;
        int h = key.hashCode();
        h = h ^ (h >>> 16);
        return (h & 0x7fffffff) % capacity;
    }

    private void checkLoad() {
        double loadFactor = (double) count / buckets.length;
        if (loadFactor >= 0.75) {
            rehash();
        }
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        int newCapacity = buckets.length * 2;
        Bucket<K, D>[] newBuckets = (Bucket<K, D>[]) new Bucket[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = new ListBucket();
        }
        for (HashNode<K, D> node : this) {
            internalPut(newBuckets, node.getKey(), node.getData());
        }
        this.buckets = newBuckets;
    }

    private void internalPut(Bucket<K, D>[] target, K key, D data) {
        int h = (key == null) ? 0 : key.hashCode();
        h = h ^ (h >>> 16);
        int index = (h & 0x7fffffff) % target.length;

        target[index].put(key, data);
        if (target[index] instanceof ListBucket && target[index].size() >= TREEIFY_THRESHOLD) {
            treeify(target, index);
        }
    }

    public D get(K key) {
        int index = hash(key);
        return buckets[index].get(key);
    }

    public int size() {
        return this.count;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        this.buckets = (Bucket<K, D>[]) new Bucket[DEFAULT_INITIAL_CAPACITY];
        for (int i = 0; i < DEFAULT_INITIAL_CAPACITY; i++) {
            buckets[i] = new ListBucket();
        }
        this.count = 0;
    }

    public D remove(K key) {
        int index = hash(key);
        Bucket<K, D> b = buckets[index];
        D removed = b.remove(key);

        if (removed != null) {
            this.count--;

            if (b instanceof TreeBucket && b.size() <= UNTREEIFY_THRESHOLD) {
                untreeify(index);
            }
        }
        return removed;
    }

    private interface Bucket<K, D> {
        D put(K key, D data);
        D get(K key);
        D remove(K key);
        int size();
        Iterable<HashNode<K, D>> nodes();
    }

    private class ListBucket implements Bucket<K, D> {
        private LinkedList<HashNode<K, D>> list = new LinkedList<HashNode<K , D>>();

        @Override
        public D put(K key, D data) {
            for (HashNode<K, D> node : list) {
                if (node.getKey().equals(key)) {
                    D oldData = node.getData();
                    node.setData(data);
                    return oldData;
                }
            }
            list.add(new HashNode<>(key, data));
            return null;
        }

        @Override
        public D get(K key) {
            for (HashNode<K, D> node : list) {
                if (node.getKey().equals(key)) return node.getData();
            }
            return null;
        }

        @Override
        public D remove(K key) {
            try {
                HashNode<K, D> dummy = new HashNode<>(key, null);
                HashNode<K, D> removed = list.remove(dummy);
                return removed.getData();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public int size() {
            return list.size();
        }

        @Override
        public Iterable<HashNode<K, D>> nodes() {
            return list;
        }
    }

    private class TreeBucket implements Bucket<K, D> {
        private AVL<HashNode<K, D>> tree = new AVL<>();

        @Override
        public D put(K key, D data) {
            HashNode<K, D> newNode = new HashNode<>(key, data);
            HashNode<K, D> oldNode = tree.put(newNode);

            if (oldNode != null) return oldNode.getData();
            return null;
        }

        @Override
        public D get(K key) {
            HashNode<K, D> dummy = new HashNode<>(key, null);
            if (tree.contains(dummy)) {
                return tree.get(dummy).getData();
            }
            return null;
        }

        @Override
        public D remove(K key) {
            try {
                HashNode<K, D> dummy = new HashNode<>(key, null);
                return tree.remove(dummy).getData();
            } catch (NoSuchElementException e) {
                return null;
            }
        }

        @Override
        public int size() {
            return tree.size();
        }

        @Override
        public Iterable<HashNode<K, D>> nodes() {
            return tree;
        }
    }

    public Stream<HashNode<K, D>> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    @Override
    public Spliterator<HashNode<K, D>> spliterator() {
        List<HashNode<K, D>> allNodes = new ArrayList<>(count);

        for (Bucket<K, D> bucket : buckets) {
            for (HashNode<K, D> node : bucket.nodes()) {
                allNodes.add(node);
            }
        }
        return allNodes.spliterator();
    }

    public void forEach(BiConsumer<? super K, ? super D> action) {
        Objects.requireNonNull(action);
        for (Bucket<K, D> bucket : buckets) {
            for (HashNode<K, D> node : bucket.nodes()) {
                action.accept(node.getKey(), node.getData());
            }
        }
    }

    public D computeIfAbsent(K key, Function<? super K, ? extends D> mapper) {
        Objects.requireNonNull(mapper);
        D v = get(key);
        if (v == null) {
            D newValue = mapper.apply(key);
            if (newValue != null) {
                put(key, newValue);
                return newValue;
            }
        }
        return v;
    }

    public D computeIfPresent(K key, BiFunction<? super K, ? super D, ? extends D> remapper) {
        Objects.requireNonNull(remapper);
        D oldValue = get(key);
        if (oldValue != null) {
            D newValue = remapper.apply(key, oldValue);
            if (newValue != null) {
                put(key, newValue);
                return newValue;
            } else {
                remove(key);
                return null;
            }
        }
        return null;
    }

    public D putIfAbsent(K key, D value) {
        D v = get(key);
        if (v == null) v = put(key, value);
        return v;
    }

    public HashTable<K, D> filter(Predicate<HashNode<K, D>> predicate) {
        HashTable<K, D> newTable = new HashTable<>(this.buckets.length);
        this.forEach((k, d) -> {
            if (predicate.test(new HashNode<>(k, d))) {
                newTable.put(k, d);
            }
        });
        return newTable;
    }

    @Override
    public Iterator<HashNode<K, D>> iterator() {
        return new Iterator<>() {
            private int bucketIndex = 0;
            private Iterator<HashNode<K, D>> currentBucketIterator = null;

            @Override
            public boolean hasNext() {
                while ((currentBucketIterator == null || !currentBucketIterator.hasNext())
                        && bucketIndex < buckets.length) {
                    currentBucketIterator = buckets[bucketIndex++].nodes().iterator();
                }
                return currentBucketIterator != null && currentBucketIterator.hasNext();
            }
            @Override
            public HashNode<K, D> next() {
                if (!hasNext()) throw new NoSuchElementException("(!) Empty (!)");
                return currentBucketIterator.next();
            }
        };
    }

    @Override
    public String toString() {
        if (isEmpty()) return "{}";
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        Iterator<HashNode<K, D>> it = iterator();
        while (it.hasNext()) {
            HashNode<K, D> node = it.next();
            K key = node.getKey();
            D data = node.getData();
            sb.append(key == this ? "(this Map)" : key);
            sb.append("=");
            sb.append(data == this ? "(this Map)" : data);
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.append("}").toString();
    }
}