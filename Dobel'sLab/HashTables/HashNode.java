package HashTables;
import java.util.Objects;

public class HashNode<K, D> implements Comparable<HashNode<K, D>> {
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
    public int compareTo(HashNode<K, D> other) {
        if (this == other || Objects.equals(this.key, other.getKey())) {
            return 0;
        }
        int h1 = (this.key == null) ? 0 : this.key.hashCode();
        int h2 = (other.key == null) ? 0 : other.key.hashCode();
        if (h1 != h2) return Integer.compare(h1, h2);

        String class1 = this.key.getClass().getName();
        String class2 = other.key.getClass().getName();
        int classCmp = class1.compareTo(class2);
        if (classCmp != 0) return classCmp;

        int id1 = System.identityHashCode(this.key);
        int id2 = System.identityHashCode(other.key);

        return Integer.compare(id1, id2);
    }
}
