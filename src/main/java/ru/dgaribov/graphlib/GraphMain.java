package ru.dgaribov.graphlib;

/**
 * Simple factory to control graph creation by clients from outside of the framework package.
 */
public class GraphMain {
    public static Graph newGraph(boolean isDirected) {
        return new Graph(isDirected);
    }
}
