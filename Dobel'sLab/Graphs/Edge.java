package Graphs;

public class Edge<T extends Comparable<T>> implements Comparable<Edge<T>> {
    private final Vertex<T> source;
    private final Vertex<T> destination;
    private final double weight;

    public Edge(Vertex<T> source, Vertex<T> destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Vertex<T> getSource() {
        return source;
    }

    public Vertex<T> getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Edge<T> o) {
        return this.destination.getValue().compareTo(o.destination.getValue());
    }
}
