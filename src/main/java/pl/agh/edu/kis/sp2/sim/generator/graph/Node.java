package pl.agh.edu.kis.sp2.sim.generator.graph;

import pl.agh.edu.kis.sp2.sim.generator.wftr.Localization;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Localization localization;
    private List<Edge> edges = new ArrayList<>();

    public Node() {
    }

    private Node(Builder builder) {
        setLocalization(builder.localization);
        edges = builder.edges;
    }

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }
    public void addEdge(Edge edge)
    {
        this.edges.add(edge);
        /*Edge reverseEdge = new Edge();
        reverseEdge.setOrigin(this);
        edge.getDestination().addEdge(reverseEdge);*/
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public static final class Builder {
        private Localization localization;
        private List<Edge> edges;

        public Builder() {
        }

        public Builder localization(Localization val) {
            localization = val;
            return this;
        }

        public Builder edges(List<Edge> val) {
            edges = val;
            return this;
        }

        public Node build() {
            return new Node(this);
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "localization=" + localization +
                ", edges=" + edges +
                '}';
    }
}