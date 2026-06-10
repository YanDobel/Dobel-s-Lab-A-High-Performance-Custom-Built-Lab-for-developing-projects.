package HashTables;

import Lists.LinkedList.*;

public class LinkedTabelaHash<K extends Comparable<K>, V> {

    private static class HashNode<K extends Comparable<K>, V> implements Comparable<HashNode<K, V>> {
        private K key;
        private V value;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public int compareTo(HashNode<K, V> o) {
            if (this.key == null || o.key == null) {
                if (this.key == o.key) return 0;
                return (this.key == null) ? -1 : 1;
            }
            return this.key.compareTo(o.key);
        }
    }

    private interface Bucket<K extends Comparable<K>, V> {
        V put(K key, V data);
        V get(K key);
        V remove(K key);
        int size();
        HashNode<K, V>[] toNodeArray();
        HashNode<K, V> pop();
    }

    private Bucket<K, V>[] buckets;
    private int count;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    public LinkedTabelaHash() {
        this.buckets = (Bucket<K, V>[]) new Bucket[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
    }

    @SuppressWarnings("unchecked")
    public LinkedTabelaHash(int capacity) {
        this.buckets = (Bucket<K, V>[]) new Bucket[capacity];
        this.capacity = capacity;
    }

    private int hash(K key) {
        if (key == null) return 0;
        int h = key.hashCode();
        return (h & Integer.MAX_VALUE) % capacity;
    }

    public V get(K key) {
        int index = hash(key);
        Bucket<K, V> b = buckets[index];
        if (b == null) return null;
        return b.get(key);
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public int size() {
        return this.count;
    }

    public V put(K key, V value) {
        int index = hash(key);
        if (buckets[index] == null) {
            buckets[index] = new ListBucket();
        }
        Bucket<K, V> b = buckets[index];
        V res = b.put(key, value);
        this.count++;
        return res;
    }

    public V putIfAbsent(K key, V value) {
        V v = get(key);
        if (v == null) v = put(key, value);
        return v;
    }

    public V remove(K key) {
        int index = hash(key);
        Bucket<K, V> b = buckets[index];
        if (b == null) return null;
        V removed = b.remove(key);
        if (removed != null) {
            this.count--;
            if (b.size() == 0) {
                buckets[index] = null;
            }
        }
        return removed;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        this.buckets = (Bucket<K, V>[]) new Bucket[this.capacity];
        this.count = 0;
    }
    @SuppressWarnings("unchecked")
    public void clear(int capacity) {
        this.buckets = (Bucket<K, V>[]) new Bucket[capacity];
        this.capacity = capacity;
        this.count = 0;
    }

    // ============== BUCKET CLASSES ==============

    private class ListBucket implements Bucket<K, V> {
        private LinkedList<HashNode<K, V>> list = new LinkedList<>();

        @Override
        public V put(K key, V value) {
            list.add(new HashNode<>(key, value));
            return null;
        }

        @Override
        public V get(K key) {
            for (HashNode<K, V> node : list) {
                if (node.getKey().equals(key)) return node.getValue();
            }
            return null;
        }

        @Override
        public V remove(K key) {
            try {
                HashNode<K, V> dummy = new HashNode<>(key, null);
                HashNode<K, V> removed = list.remove(dummy);
                return removed.getValue();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public int size() {
            return list.size();
        }

        @SuppressWarnings("unchecked")
        @Override
        public HashNode<K, V>[] toNodeArray() {
            HashNode<K, V>[] array = (HashNode<K, V>[]) new HashNode[size()];
            int i = 0;
            for (HashNode<K, V> node : list) {
                array[i++] = node;
            }
            return array;
        }

        @Override
        public HashNode<K, V> pop() {
            if (list.isEmpty()) return null;
            return list.removeFirst();
        }
    }
}