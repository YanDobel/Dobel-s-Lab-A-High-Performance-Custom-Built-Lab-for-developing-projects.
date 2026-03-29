package HashTables.LinkedHashTables;

import java.util.Objects;

public class HashNode<K, D> {
    private K key;
    private D data;

    public HashNode (K key, D data) {
        this.key = key;
        this.data = data;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HashNode<?, ?> hashNode = (HashNode<?, ?>) o;
        return Objects.equals(key, hashNode.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
