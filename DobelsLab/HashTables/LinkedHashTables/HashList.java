package HashTables.LinkedHashTables;
import Lists.LinkedList.LinkedList;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

public class HashList<K, D> implements Iterable<HashNode<K, D>> {
    private final LinkedList<HashNode<K, D>> hashList;

    public HashList() {
        this.hashList = new LinkedList<HashNode<K, D>>();
    }

    public void add(HashNode<K, D> node) {
        hashList.insertLast(node);
    }
    public void add(K key, D data) {
        hashList.insertLast(new HashNode<>(key, data));
    }

    public D remove(K key) {
        Optional<HashNode<K, D>> nodeFound = hashList
                .find(node -> Objects.equals(node.getKey(), key));

        if (nodeFound.isPresent()) {
            HashNode<K, D> targetNode = nodeFound.get();
            hashList.remove(targetNode);
            return targetNode.getData();
        }
        throw new NoSuchElementException("(!) No data matches the provided key (!)");
    }

    public D get(K key) {
        return hashList.find(node -> Objects.equals(node.getKey(), key))
                .get().getData();
    }

    public boolean isEmpty() {
        return hashList.isEmpty();
    }

    public boolean contains(K key) {
        return hashList.find(node -> Objects.equals(node.getKey(), key)).isPresent();
    }

    public LinkedList<HashNode<K, D>> showAll() {
        return hashList;
    }

    public void clear() {
        hashList.clear();
    }

    @Override
    public void forEach(Consumer<? super HashNode<K, D>> action) {
        hashList.forEach(action);
    }

    public LinkedList<HashNode<K, D>> filter(Predicate<HashNode<K, D>> test) {
        return hashList.filter(test);
    }

    public <R> LinkedList<R> map(Function<? super HashNode<K, D>, ? extends R> mapper) {
        return hashList.map(mapper);
    }

    public Optional<HashNode<K, D>> find(Predicate<HashNode<K, D>> test) {
        return hashList.find(test);
    }

    @Override
    public Spliterator<HashNode<K, D>> spliterator() {
        return hashList.spliterator();
    }

    public Stream<HashNode<K, D>> stream() {
        return hashList.stream();
    }

    @Override
    public Iterator<HashNode<K, D>> iterator() {
        return hashList.iterator();
    }
}
