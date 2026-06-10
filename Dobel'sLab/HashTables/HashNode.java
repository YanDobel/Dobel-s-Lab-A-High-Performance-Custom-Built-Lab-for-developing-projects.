package HashTables;
import java.util.Objects;

public class HashNode<K extends Comparable<K>, D> implements Comparable<HashNode<K, D>> {
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

    @Override
    public int compareTo(HashNode<K, D> o) {
        if (this.key == null || o.key == null) {
            if (this.key == o.key) return 0;
            return (this.key == null) ? -1 : 1;
        }
        return this.key.compareTo(o.key);
    }
}