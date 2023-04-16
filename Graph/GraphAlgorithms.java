import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Renjie Yao
 * @userid ryao36
 * @GTID 903622594
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start)) {
            throw new java.lang.IllegalArgumentException("Argument is illegal");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        Queue<Vertex<T>> queue = new LinkedList<>();
        List<Vertex<T>> res = new ArrayList<>();
        HashSet<Vertex<T>> visited = new HashSet<>();
        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Vertex<T> curr = queue.poll();
            res.add(curr);
            for (VertexDistance<T> vd : adjList.get(curr)) {
                Vertex<T> vertex = vd.getVertex();
                if (!visited.contains(vertex)) {
                    queue.offer(vertex);
                    visited.add(vertex);
                }
            }
        }
        return res;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start)) {
            throw new java.lang.IllegalArgumentException("Argument is Illegal");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        List<Vertex<T>> res = new ArrayList<>();
        HashSet<Vertex<T>> visited = new HashSet<>();
        dfsHelper(start, adjList, res, visited);
        return res;
    }

    /**
     * dfs helper function to actually perform the dfs
     * @param start the vertex to begin the dfs on
     * @param adjList the adjacent list
     * @param res the result containing the dfs result in order
     * @param visited the visited set to keep track of all vertices visited
     * @param <T> the generic typing of the data
     */
    private static <T> void dfsHelper(Vertex<T> start, Map<Vertex<T>, List<VertexDistance<T>>> adjList,
                                      List<Vertex<T>> res, HashSet<Vertex<T>> visited) {
        if (visited.contains(start)) {
            return;
        }
        visited.add(start);
        res.add(start);
        for (VertexDistance<T> vd : adjList.get(start)) {
            Vertex<T> vertex = vd.getVertex();
            dfsHelper(vertex, adjList, res, visited);
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start)) {
            throw new java.lang.IllegalArgumentException("Argument is illegal");
        }

        HashSet<Vertex<T>> visited = new HashSet<>();
        Map<Vertex<T>, Integer> distMap = new HashMap<>();
        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        for (Vertex<T> vertex : graph.getVertices()) {
            distMap.put(vertex, Integer.MAX_VALUE);
        }
        pq.offer(new VertexDistance<>(start, 0));
        while (!pq.isEmpty() && visited.size() < graph.getVertices().size()) {
            VertexDistance<T> vt = pq.poll();
            Vertex<T> vertex = vt.getVertex();
            int dist = vt.getDistance();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                distMap.put(vertex, dist);
                for (VertexDistance<T> vd : adjList.get(vertex)) {
                    if (!visited.contains(vd.getVertex())) {
                        pq.offer(new VertexDistance<>(vd.getVertex(), dist + vd.getDistance()));
                    }
                }
            }
        }
        return distMap;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that 
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new java.lang.IllegalArgumentException("Argument is illegal");
        }
        Set<Vertex<T>> visited = new HashSet<Vertex<T>>();
        Set<Edge<T>> output = new HashSet<Edge<T>>();
        PriorityQueue<Edge<T>> q = new PriorityQueue<Edge<T>>();
        for (VertexDistance<T> vertexDistance : graph.getAdjList().get(start)) {
            q.add(new Edge<T>(start, vertexDistance.getVertex(), vertexDistance.getDistance()));
        }
        visited.add(start);
        while (!(q.isEmpty()) && visited.size() != graph.getVertices().size()) {
            Edge<T> curr = q.remove();
            if (!visited.contains(curr.getV())) {
                visited.add(curr.getV());
                output.add(new Edge<T>(curr.getU(), curr.getV(), curr.getWeight()));
                output.add(new Edge<T>(curr.getV(), curr.getU(), curr.getWeight()));
                for (VertexDistance<T> vertexDistance : graph.getAdjList().get(curr.getV())) {
                    if (!visited.contains(vertexDistance.getVertex())) {
                        q.add(new Edge<T>(curr.getV(), vertexDistance.getVertex(), vertexDistance.getDistance()));
                    }
                }
            }
        }
        if (output.size() < graph.getVertices().size() - 1) {
            return null;
        }
        return output;
    }
}