package ru.dgaribov.graphlib;

import lombok.extern.java.Log;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;


/**
 * Contents all graph behaviour.
 *
 * Supports operations:
 * add vertex
 * remove vertex
 * add edge
 * find path between two vertexes
 * traverse vertexes with given function
 *
 * Constructor is package private to have control over graph creation for clients via one factory
 */
@Log
public class Graph {

    private boolean isDirected;

    Graph(boolean isDirected) {
        this.isDirected = isDirected;
    }

    private Vertex root;
    private Map<Vertex, List<Vertex>> adjVertices = new ConcurrentHashMap<>();


    /**
     *
     * @param traverseFunction function to be applied on every vertex in the graph
     */
    public void traverseWholeGraphWithFunction(Consumer<Vertex> traverseFunction) throws TraverseException {
        breadthFirstTraversal(root, null, traverseFunction);
    }

    /**
     *
     * @param vertex1
     * @param vertex2
     * @return the path from one vertex to another if one exists
     */
    public List<Vertex> getPath(Vertex vertex1, Vertex vertex2) throws TraverseException {
        return breadthFirstTraversal(vertex1, vertex2, null);
    }

    public void addVertex(Vertex vertex) {
        if (vertex == null) return;
        root = root == null ? vertex : root;
        adjVertices.putIfAbsent(vertex, new ArrayList<>());
    }

    public void removeVertex(Vertex v) {
        adjVertices.values().forEach(e -> e.remove(v));
        adjVertices.remove(v);
        if (adjVertices.isEmpty()) root = null;
    }

    /**
     * Connects two given vertexes with an edge
     *
     * @param v1
     * @param v2
     */
    public void addEdge(Vertex v1, Vertex v2) {
        if (v1 == null || v2 == null) return;
        adjVertices.get(v1).add(v2);
        if (!isDirected) adjVertices.get(v2).add(v1);
    }

    /**
     * Removes an edge from the graph
     *
     * @param v1 first vertex of given edge
     * @param v2 second vertex of given edge
     */
    public void removeEdge(Vertex v1, Vertex v2) {
        List<Vertex> eV1 = adjVertices.get(v1);
        List<Vertex> eV2 = adjVertices.get(v2);
        if (eV1 != null)
            eV1.remove(v2);
        if (eV2 != null)
            if (!isDirected) eV2.remove(v1);
    }

    /**
     * Traverses over the graph with breadth-first-search algorithm.
     *
     * @param root of our walk through the graph. For traversal method it would be root of the graph.
     *             For getPath method it would be vertexFrom.
     * @param destination passed if we want to build a path from vertex1 to vertex2. null otherwise.
     * @param traverseFunction passed if we want to apply user function over graph vertexes while them being traversed.
     *                         null otherwise.
     * @return list of vertexes if we need a path. empty list for traversal.
     */
    private List<Vertex> breadthFirstTraversal(Vertex root, Vertex destination, Consumer<Vertex> traverseFunction) throws TraverseException{
        Set<Vertex> visited = new HashSet<>();
        Map<Vertex, Vertex> vertexParentMap = new HashMap<>();
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Vertex vertex = queue.poll();
            visited.add(vertex);
            try {
                if (traverseFunction != null) traverseFunction.accept(vertex);
                if (vertex.equals(destination)) {
                    return buildEdgePathFromParentMap(vertexParentMap, destination);
                }
            } catch (Exception ex) {
                log.throwing(this.getClass().getName(), "breadthFirstTraversal", ex);
                throw new TraverseException();
            }

            for (Vertex v : getAdjVertices(vertex)) {
                if (visited.contains(v)) continue;
                queue.add(v);
                if (destination != null) vertexParentMap.put(v, vertex);
            }
        }
        return Collections.emptyList();
    }


    public int getNumberOfVertexes() {
        return adjVertices.size();
    }

    private List<Vertex> getAdjVertices(Vertex vertex) {
        return adjVertices.get(vertex);
    }

    private List<Vertex> buildEdgePathFromParentMap(Map<Vertex, Vertex> parentMap, Vertex destination) {
        LinkedList<Vertex> path = new LinkedList<>();
        path.add(destination);
        Vertex current = destination;
        while (parentMap.containsKey(current)) {
            current = parentMap.get(current);
            path.addFirst(current);
        }

        return path;
    }
}
