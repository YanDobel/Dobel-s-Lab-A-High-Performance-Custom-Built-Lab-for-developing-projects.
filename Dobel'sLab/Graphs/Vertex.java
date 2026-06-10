package Graphs;

import Trees.AVL.AVL_SucEsq.AVL;

public class Vertex<T extends Comparable<T>> {
    private final T value;
    private final AVL<Edge<T>> adjacencies;

    public Vertex(T value) {
        this.value = value;
        this.adjacencies = new AVL<>();
    }

    public enum ADJACENCIES {
        RBT, AVL, HASH;
    }

    public void addEdge(Vertex<T> destination, double weight) {
        adjacencies.add(new Edge<>(this, destination, weight));
    }

    public T getValue() {
        return value;
    }

    public AVL<Edge<T>> getAdjacencies() {
        return this.adjacencies;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}