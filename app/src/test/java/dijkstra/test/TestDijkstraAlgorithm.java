package dijkstra.test;

import org.junit.Before;
import org.junit.Test;

import java.util.List;


import epam.lab.dijkstra.engine.DijkstraAlgorithm;
import epam.lab.dijkstra.model.Edge;
import epam.lab.dijkstra.model.Graph;
import epam.lab.dijkstra.model.Vertex;


public class TestDijkstraAlgorithm {
    Graph graph;

    @Before
    public void create() {
        this.graph = new Graph();
        for (int i = 0; i < 11; i++)
            graph.addVertex(i);

        graph.addEdge(0, 1, 85).
                addEdge(0, 2, 217).
                addEdge(0, 4, 173).
                addEdge(2, 6, 186).
                addEdge(2, 7, 103).
                addEdge(3, 7, 183).
                addEdge(5, 8, 250).
                addEdge(8, 9, 84).
                addEdge(7, 9, 167).
                addEdge(4, 9, 502).
                addEdge(9, 10, 40).
                addEdge(1, 10, 600);

        graph.addEdge(2, 10, 30);

    }

    @Test
    public void testExecute() {
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(0);
        List<Vertex> path = dijkstra.getPath(10);

        int total = 0;
        for (int i = 0; i < path.size(); i++) {
            Edge cur = graph.getEdgeByParams(path.get(i).getId(), i + 1 != path.size() ? path.get(i + 1).getId() : Integer.MAX_VALUE);
            if (cur != null)
                total += cur.getWeight();
            System.out.println("vertex " + path.get(i) + "|  chosen edge: " + cur + " -> ");
            graph.getEdgesListBySource(path.get(i).getId()).forEach(edge -> System.out.println("     " + edge));
            System.out.println("------------------------");
        }
        System.out.println("Total weight comprises " + total);


    }
}
