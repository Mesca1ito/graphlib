package ru.dgaribov;


import org.junit.Assert;
import org.junit.Test;
import ru.dgaribov.graphlib.Graph;
import ru.dgaribov.graphlib.GraphMain;
import ru.dgaribov.graphlib.Vertex;

import java.util.List;

public class GraphTest {


    @Test
    public void vertexAddTest() {
        Graph graph = GraphMain.newGraph(false);
        Assert.assertEquals(0, graph.getNumberOfVertexes());
        graph.addVertex(new SimpleLabelVertex("Vertex 1"));
        graph.addVertex(new SimpleLabelVertex("Vertex 2"));
        graph.addVertex(new SimpleLabelVertex("Vertex 3"));
        Assert.assertEquals(3, graph.getNumberOfVertexes());

    }


    @Test
    public void edgeAddAndGetPathTest() {
        Graph graph = GraphMain.newGraph(false);
        Assert.assertEquals(0, graph.getNumberOfVertexes());
        graph.addVertex(new SimpleLabelVertex("Vertex 1"));
        graph.addVertex(new SimpleLabelVertex("Vertex 2"));
        graph.addVertex(new SimpleLabelVertex("Vertex 3"));
        graph.addEdge(new SimpleLabelVertex("Vertex 1"), new SimpleLabelVertex("Vertex 2"));
        graph.addEdge(new SimpleLabelVertex("Vertex 2"), new SimpleLabelVertex("Vertex 3"));
        List<Vertex> path = graph.getPath(new SimpleLabelVertex("Vertex 1"), new SimpleLabelVertex("Vertex 3"));
        Assert.assertEquals(3, graph.getNumberOfVertexes());
        Assert.assertEquals("Vertex 1", path.get(0).getValue());
        Assert.assertEquals("Vertex 2", path.get(1).getValue());
        Assert.assertEquals("Vertex 3", path.get(2).getValue());
    }


    @Test
    public void traverseTest() {

        final StringBuilder traversePath = new StringBuilder();
        Graph graph = GraphMain.newGraph(false);
        Assert.assertEquals(0, graph.getNumberOfVertexes());
        graph.addVertex(new SimpleLabelVertex("Vertex 1"));
        graph.addVertex(new SimpleLabelVertex("Vertex 2"));
        graph.addVertex(new SimpleLabelVertex("Vertex 3"));
        graph.addEdge(new SimpleLabelVertex("Vertex 1"), new SimpleLabelVertex("Vertex 2"));
        graph.addEdge(new SimpleLabelVertex("Vertex 2"), new SimpleLabelVertex("Vertex 3"));
        graph.traverseWholeGraphWithFunction((v) -> {
            traversePath.append(v.getValue());
        });

        Assert.assertEquals("Vertex 1Vertex 2Vertex 3", traversePath.toString());
    }

    @Test
    public void reversePathDoesExistForUndirectedGraphTest() {
        Graph graph = GraphMain.newGraph(false);
        Assert.assertEquals(0, graph.getNumberOfVertexes());
        graph.addVertex(new SimpleLabelVertex("Vertex 1"));
        graph.addVertex(new SimpleLabelVertex("Vertex 2"));
        graph.addVertex(new SimpleLabelVertex("Vertex 3"));
        graph.addEdge(new SimpleLabelVertex("Vertex 1"), new SimpleLabelVertex("Vertex 2"));
        graph.addEdge(new SimpleLabelVertex("Vertex 2"), new SimpleLabelVertex("Vertex 3"));
        List<Vertex> path = graph.getPath(new SimpleLabelVertex("Vertex 3"), new SimpleLabelVertex("Vertex 1"));
        Assert.assertEquals(3, graph.getNumberOfVertexes());
        Assert.assertEquals(3, path.size());
    }

    @Test
    public void reversePathDoesNotExistForDirectedGraphTest() {
        Graph graph = GraphMain.newGraph(true);
        Assert.assertEquals(0, graph.getNumberOfVertexes());
        graph.addVertex(new SimpleLabelVertex("Vertex 1"));
        graph.addVertex(new SimpleLabelVertex("Vertex 2"));
        graph.addVertex(new SimpleLabelVertex("Vertex 3"));
        graph.addEdge(new SimpleLabelVertex("Vertex 1"), new SimpleLabelVertex("Vertex 2"));
        graph.addEdge(new SimpleLabelVertex("Vertex 2"), new SimpleLabelVertex("Vertex 3"));
        List<Vertex> path = graph.getPath(new SimpleLabelVertex("Vertex 3"), new SimpleLabelVertex("Vertex 1"));
        Assert.assertEquals(3, graph.getNumberOfVertexes());
        Assert.assertEquals(0, path.size());
    }
}
