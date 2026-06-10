package Algorithms.graph.Dijkstra;

public class PathNode<T extends Comparable<T>> implements Comparable<PathNode<T>> {
    private final T value;
    private final double distance;

    public PathNode(T value, double distance) {
        this.value = value;
        this.distance = distance;
    }

    @Override
    public int compareTo(PathNode<T> o) {
        return Double.compare(this.distance, o.distance);
    }

    public T getValue() {
        return value;
    }

    public double getDistance() {
        return distance;
    }
}