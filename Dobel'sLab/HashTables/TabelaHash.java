package HashTables;

import Lists.LinkedList.*;
import Trees.AVL.AVL_SucEsq.*;

import java.util.List;
import java.util.NoSuchElementException;

public class TabelaHash<K extends Comparable<K>, V> {

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
    private static final int TREEIFY_THRESHOLD = 8;
    private static final int UNTREEIFY_THRESHOLD = 6;
    private static final int DEFAULT_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    public TabelaHash() {
        this.buckets = (Bucket<K, V>[]) new Bucket[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
    }

    @SuppressWarnings("unchecked")
    public TabelaHash(int capacity) {
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

    private void treeify(Bucket<K, V>[] target, int index) {
        Bucket<K, V> oldBucket = target[index];
        TreeBucket newBucket = new TreeBucket();

        while (oldBucket.size() > 0) {
            HashNode<K, V> node = oldBucket.pop();
            if (node != null) {
                newBucket.put(node.getKey(), node.getValue());
            }
        }
        target[index] = newBucket;
    }

    private void untreeify(int index) {
        Bucket<K, V> oldBucket = buckets[index];
        ListBucket newBucket = new ListBucket();
        while(oldBucket.size() > 0) {
            HashNode<K, V> node = oldBucket.pop();
            if (node != null) {
                newBucket.put(node.getKey(), node.getValue());
            }
        }
        buckets[index] = newBucket;
    }


    public V put(K key, V value) {
        int index = hash(key);
        if (buckets[index] == null) {
            buckets[index] = new ListBucket();
        }
        Bucket<K, V> b = buckets[index];
        V res = b.put(key, value);
        if (res == null) {
            this.count++;
            if (b instanceof ListBucket && b.size() >= TREEIFY_THRESHOLD) {
                treeify(this.buckets, index);
            }
        }
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
            else if (b instanceof TreeBucket && b.size() <= UNTREEIFY_THRESHOLD) {
                untreeify(index);
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < capacity; i++) {
            sb.append(i + " -> ");
            if (buckets[i] != null) {
                HashNode<K, V>[] nodes = buckets[i].toNodeArray();
                int n = nodes.length;
                for (int j = 0; j < n; j++) {
                    sb.append(nodes[j].getKey() + " -> ");
                }
            }
            sb.append("\\");
            if (i < capacity - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
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

    private class TreeBucket implements Bucket<K, V> {
        private AVL<HashNode<K, V>> avl = new AVL<>();

        @Override
        public V put(K key, V value) {
            HashNode<K, V> newNode = new HashNode<>(key, value);
            HashNode<K, V> oldNode = avl.addOrModify(newNode);

            if (oldNode != null) return oldNode.getValue();
            return null;
        }

        @Override
        public V get(K key) {
            HashNode<K, V> dummy = new HashNode<>(key, null);
            if (avl.contains(dummy)) {
                return avl.get(dummy).getValue();
            }
            return null;
        }

        @Override
        public V remove(K key) {
            try {
                HashNode<K, V> dummy = new HashNode<>(key, null);
                return avl.remove(dummy).getValue();
            } catch (NoSuchElementException e) {
                return null;
            }
        }

        @Override
        public int size() {
            return avl.size();
        }

        @SuppressWarnings("unchecked")
        @Override
        public HashNode<K, V>[] toNodeArray() {
            List<HashNode<K, V>> list = avl.inOrder();
            HashNode<K, V>[] array = (HashNode<K, V>[]) new HashNode[list.size()];

            for (int i = 0; i < list.size(); i++) {
                array[i] = list.get(i);
            }
            return array;
        }

        @Override
        public HashNode<K, V> pop() {
            if (avl.isEmpty()) return null;
            return avl.removeFirst();
        }
    }
}