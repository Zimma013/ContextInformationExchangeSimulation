package pl.agh.edu.kis.sp2.sim.generator.graph;

import pl.agh.edu.kis.sp2.sim.generator.dto.LocalizationDto;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private LocalizationDto localization;
    private List<Edge> edges = new ArrayList<>();
    public LocalizationDto getLocalization() {
        return localization;
    }

    public void setLocalization(LocalizationDto localization) {
        this.localization = localization;
    }
    public void addEdge(Edge edge)
    {
        this.edges.add(edge);
        Edge reverseEdge = new Edge();
        reverseEdge.setOrigin(this);
        edge.getDestination().addEdge(reverseEdge);
    }

}