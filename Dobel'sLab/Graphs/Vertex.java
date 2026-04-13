package Graphs;

import Trees.AVL.AVL_SucEsq.AVL;

public class Vertex<T extends Comparable<T>> {
    private final T value;
    private final AVL<Edge<T>> adjecencies;

    public Vertex(T value) {
        this.value = value;
        this.adjecencies = new AVL<>();
    }

    public void addEdge(Vertex<T> destination, double weight) {
        adjecencies.add(new Edge<>(this, destination, weight));
    }

    public T getValue() {
        return value;
    }

    public AVL<Edge<T>> getAdjecencies() {
        return this.adjecencies;
    }
}
