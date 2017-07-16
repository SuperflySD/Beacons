package epam.lab.dijkstra.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {
    private List<Vertex> vertexesList = new ArrayList<>();
    private List<Edge> edgesList = new ArrayList<>();

    public Graph addVertex(Vertex vertex) {
        if (vertexesList.contains(vertex))
            throw new RuntimeException("Vertex with such ID is already in existence");
        this.vertexesList.add(vertex);
        return this;
    }

    public Graph addVertex(int vertexId) {
        Vertex temp = new Vertex(vertexId);
        if (vertexesList.contains(temp))
            throw new RuntimeException("Vertex with such ID is already in existence");
        this.vertexesList.add(temp);
        return this;
    }

    public Graph removeVertex(Vertex vertex) {
        this.vertexesList.remove(vertex);
        return this;
    }

    public Graph removeVertex(int vertexId) {
        this.vertexesList.remove(new Vertex(vertexId));
        return this;
    }

    public Vertex getVertexById(int vertexId) {
        return this.vertexesList.get(vertexesList.indexOf(new Vertex(vertexId)));
    }

    public Graph addEdge(Edge edge) {
        if (!(vertexesList.stream().anyMatch(vertex -> vertex.getId() == edge.getSource().getId()) &&
                vertexesList.stream().anyMatch(vertex -> vertex.getId() == edge.getDestination().getId())))
            throw new RuntimeException("There is no such couple of source-destination for this edge");
        if (edgesList.contains(edge))
            throw new RuntimeException("Edge with such source-destination couple is already in existence");
        this.edgesList.add(edge);
        return this;
    }

    public Graph addEdge(Vertex source, Vertex destination, int weight) {
        Edge temp = new Edge(source, destination, weight);
        if (!(vertexesList.stream().anyMatch(vertex -> vertex.getId() == temp.getSource().getId()) &&
                vertexesList.stream().anyMatch(vertex -> vertex.getId() == temp.getDestination().getId())))
            throw new RuntimeException("There is no such couple of source-destination for this edge");
        if (edgesList.contains(temp))
            throw new RuntimeException("Edge with such source-destination couple is already in existence");

        this.edgesList.add(temp);
        return this;
    }

    public Graph addEdge(int sourceId, int destinationId, int weight) {
        Edge temp = new Edge(new Vertex(sourceId), new Vertex(destinationId), weight);
        if (!(vertexesList.stream().anyMatch(vertex -> vertex.getId() == temp.getSource().getId()) &&
                vertexesList.stream().anyMatch(vertex -> vertex.getId() == temp.getDestination().getId())))
            throw new RuntimeException("There is no such couple of source-destination for this edge");
        if (edgesList.contains(temp))
            throw new RuntimeException("Edge with such source-destination couple is already in existence");
        this.edgesList.add(temp);
        return this;
    }

    public Graph removeEdge(Edge edge) {
        this.edgesList.remove(edge);
        return this;
    }

    public Graph removeEdge(int sourceVertexId, int destinationVertexId) {
        Edge temp = new Edge(new Vertex(sourceVertexId), new Vertex(destinationVertexId), Integer.MAX_VALUE);
        this.edgesList.remove(temp);
        return this;
    }

    public Edge getEdgeByParams(int sourceVertexId, int destinationVertexId) {
        return this.edgesList.stream().
                filter(edge -> edge.getSource().getId() == sourceVertexId && edge.getDestination().getId() == destinationVertexId).
                findFirst().orElse(null);
    }

    public List<Vertex> getVertexesList() {
        return Collections.unmodifiableList(vertexesList);
    }

    public List<Integer> getIdVertexesList() {
        return vertexesList.stream().map(Vertex::getId).collect(Collectors.toList());
    }

    public List<Edge> getEdgesListBySource() {
        return edgesList.stream().sorted(Comparator.comparingInt(e -> e.getSource().getId())).collect(Collectors.toList());
    }

    public List<Edge> getEdgesListBySource(Integer... sources) {
        return edgesList.stream().filter(edge -> Arrays.asList(sources).contains(edge.getSource().getId())).
                sorted(Comparator.comparingInt(e -> e.getSource().getId())).collect(Collectors.toList());
    }

    public List<Edge> getEdgesListByDestination() {
        ArrayList<Edge> temp = new ArrayList<>(edgesList);
        temp.sort(Comparator.comparingInt(e -> e.getDestination().getId()));
        return temp;
    }

    public List<Edge> getEdgesListByDestination(Integer... destinations) {
        return edgesList.stream().filter(edge -> Arrays.asList(destinations).contains(edge.getDestination().getId())).
                sorted(Comparator.comparingInt(e -> e.getDestination().getId())).collect(Collectors.toList());
    }

}
