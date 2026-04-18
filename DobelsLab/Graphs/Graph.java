package Graphs;

import HashTables.*;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class Graph<T extends Comparable<T>> {
    private final HashMap<T, Vertex<T>> vertices;
    private final boolean directed;

    public Graph(boolean directed) {
        this.vertices = new HashMap<>();
        this.directed = directed;
    }

    public void addEdge(T src, T dest, double weight) {
        if (!vertices.containsKey(src)) vertices.put(src, new Vertex<>(src));
        if (!vertices.containsKey(dest)) vertices.put(dest, new Vertex<>(dest));

        Vertex<T> vSrc = vertices.get(src);
        Vertex<T> vDest = vertices.get(dest);

        vSrc.addEdge(vDest, weight);
        if (!directed) vDest.addEdge(vSrc, weight);
    }

    public Vertex<T> removeVertex(T val) {
        Vertex<T> target = vertices.get(val);
        if (target == null) throw new NoSuchElementException("(!) Vertex not found (!)");

        for (Vertex<T> v : vertices.values()) {
            if (v == target) continue;
            Edge<T> dummy = new Edge<>(v, target, 0);
            v.getAdjacencies().remove(dummy);
        }
        target.getAdjacencies().clear();
        return vertices.remove(val);
    }

    public Vertex<T> getVertex(T val) {
        return vertices.get(val);
    }

    public void forEachVertex(Consumer<Vertex<T>> action) {
        vertices.values().forEach(action);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex<T> v : vertices.values()) {
            sb.append(v.getValue()).append("->").append(v.getAdjacencies()).append("\n");
        }
        return sb.toString();
    }
}