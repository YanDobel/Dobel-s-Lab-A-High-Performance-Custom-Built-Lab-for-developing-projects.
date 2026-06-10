package HashTables.Contracts;
import java.util.List;

public interface MyMap<K, V> {
    V put(K key, V data);
    V get(K key);
    V remove(K key);
    int size();
    boolean isEmpty();
    boolean containsKey(K key);
    void clear();

    V getOrDefault(K key, V defaultValue);
    List<K> keys();
    List<V> values();
}
