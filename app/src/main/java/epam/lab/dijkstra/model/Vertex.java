package epam.lab.dijkstra.model;

public class Vertex {
    private int id;
    private String name;
    private double longt;

    public Vertex(int id,  double longt) {
        this.id = id;
        this.longt = longt;
    }
    public Vertex(int id) {
        this.id = id;
    }

    public Vertex(int id, String name, double longt) {
        this.id = id;
        this.name = name;
        this.longt = longt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name != null ? name : "noName";
    }

    @Override
    public String toString() {
        return "id="+ id +"(" + getName()+")";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;

        Vertex vertex = (Vertex) o;

        return id == vertex.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}