package Algorithms.graph.Dijkstra;
import HashTables.*;
import Graphs.*;
import Trees.Heap.MinHeap;

import java.util.ArrayList;
import java.util.List;

public class ShortestPath<T extends Comparable<T>> {

    public HashMap<T, Double> findShortestPaths(Graph<T> graph, T sourceVal) {
        HashMap<T, Double> distances = new HashMap<>();
        HashMap<T, Boolean> settled = new HashMap<>();
        HashMap<T, T> predecessors = new HashMap<>();

        graph.forEachVertex(v -> {
            distances.put(v.getValue(), Double.MAX_VALUE);
            settled.put(v.getValue(), false);
        });

        if (!distances.containsKey(sourceVal)) return distances;
        distances.put(sourceVal, 0.0);

        MinHeap<PathNode<T>> pq = new MinHeap<>();
        pq.add(new PathNode<>(sourceVal, 0.0));

        while (!pq.isEmpty()) {
            PathNode<T> current = pq.poll();
            T u = current.getValue();

            if (settled.get(u)) continue;
            settled.put(u, true);

            Vertex<T> vertexU = graph.getVertex(u);
            if (vertexU == null) continue;

            for (Edge<T> edge : vertexU.getAdjacencies()) {
                T v = edge.getDestination().getValue();

                if (!settled.get(v)) {
                    double weight = edge.getWeight();
                    double currentDisThroughU = distances.get(u) + weight;

                    if (currentDisThroughU < distances.get(v)) {
                        distances.put(v, currentDisThroughU);
                        predecessors.put(v, u);
                        pq.add(new PathNode<>(v, currentDisThroughU));
                    }
                }
            }
        }
        return distances;
    }

    public List<T> getPath(T target, HashMap<T, T> predecessors) {
        ArrayList<T> path = new ArrayList<>();
        for (T at = target; at != null; at = predecessors.get(at)) {
            path.addFirst(at);
        }
        return path;
    }
}