package Graphs;

import HashTables.*;

import java.util.function.Consumer;

public class Graph<T extends Comparable<T>> {
    private final HashMap<T, Vertex<T>> vertices;
    private final boolean directed;

    public Graph(boolean directed) {
        this.vertices = new HashMap<>();
        this.directed = directed;
    }

    public void addEdge(T src, T dest, double weight) {
        vertices.putIfAbsent(src, new Vertex<>(src));
        vertices.putIfAbsent(dest, new Vertex<>(dest));

        Vertex<T> vSrc = vertices.get(src);
        Vertex<T> vDest = vertices.get(dest);

        vSrc.addEdge(vDest, weight);
        if (!directed) vDest.addEdge(vSrc, weight);
    }

    public void forEachVertex(Consumer<Vertex<T>> action) {
        vertices.values().forEach(action);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex<T> v : vertices.values()) {
            sb.append(v.getValue()).append("->").append(v.getAdjecencies()).append("\n");
        }
        return sb.toString();
    }
}
